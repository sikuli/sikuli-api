/*
Copyright 2007-2011 Selenium committers

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.sikuli.api.remote.client;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.logging.NeedsLocalLogs;
import org.openqa.selenium.logging.LocalLogs;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.profiler.HttpProfilerLogEntry;
import org.openqa.selenium.net.Urls;
import org.openqa.selenium.remote.BeanToJsonConverter;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.ErrorCodes;
import org.openqa.selenium.remote.JsonToBeanConverter;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.internal.HttpClientFactory;
import org.sikuli.api.Screen;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Mouse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import static org.apache.http.protocol.ExecutionContext.HTTP_TARGET_HOST;
import static org.openqa.selenium.remote.DriverCommand.*;

public class SikuliHttpCommandExecutor implements CommandExecutor, NeedsLocalLogs {

  private static final int MAX_REDIRECTS = 10;

  private final HttpHost targetHost;
  private final URL remoteServer;
  private final Map<String, CommandInfo> nameToUrl;
  private final HttpClient client;

  private static HttpClientFactory httpClientFactory;

  private LocalLogs logs = LocalLogs.getNullLogger();

  private enum HttpVerb {
    GET() {
      @Override
      public HttpUriRequest createMethod(String url) {
        return new HttpGet(url);
      }
    },
    POST() {
      @Override
      public HttpUriRequest createMethod(String url) {
        return new HttpPost(url);
      }
    },
    DELETE() {
      @Override
      public HttpUriRequest createMethod(String url) {
        return new HttpDelete(url);
      }
    };

    public abstract HttpUriRequest createMethod(String url);
  }

  public SikuliHttpCommandExecutor(URL addressOfRemoteServer) {
    try {
      remoteServer = addressOfRemoteServer == null ?
                     new URL(System.getProperty("webdriver.remote.server", "http://localhost:4444/wd/hub")) :
                     addressOfRemoteServer;
    } catch (MalformedURLException e) {
      throw new WebDriverException(e);
    }

    HttpParams params = new BasicHttpParams();
    // Use the JRE default for the socket linger timeout.
    params.setParameter(CoreConnectionPNames.SO_LINGER, -1);
    HttpClientParams.setRedirecting(params, false);

    synchronized (SikuliHttpCommandExecutor.class) {
      if (httpClientFactory == null) {
        httpClientFactory = new HttpClientFactory();
      }
    }
    client = httpClientFactory.getHttpClient();
    if (addressOfRemoteServer != null && addressOfRemoteServer.getUserInfo() != null) {
      // Use HTTP Basic auth
      UsernamePasswordCredentials credentials = new
          UsernamePasswordCredentials(addressOfRemoteServer.getUserInfo());
      ((DefaultHttpClient) client).getCredentialsProvider().
          setCredentials(AuthScope.ANY, credentials);
    }

    // Some machines claim "localhost.localdomain" is the same as "localhost".
    // This assumption is not always true.

    String host = remoteServer.getHost().replace(".localdomain", "");

    targetHost = new HttpHost(
        host, remoteServer.getPort(), remoteServer.getProtocol());

    nameToUrl = ImmutableMap.<String, CommandInfo>builder()
        .put(ScreenRegion.FIND, post("/screen/find"))
        .put(Mouse.CLICK, post("/mouse/click"))
        .put(Screen.GET_SIZE, get("/screen/size"))
        .build();
  }

  public void setLocalLogs(LocalLogs logs) {
    this.logs = logs;
  }

  private void log(String logType, LogEntry entry) {
    logs.addEntry(logType, entry);
  }

  public URL getAddressOfRemoteServer() {
    return remoteServer;
  }

  public Response execute(Command command) throws IOException {
    HttpContext context = new BasicHttpContext();

    CommandInfo info = nameToUrl.get(command.getName());
    try {
      HttpUriRequest httpMethod = info.getMethod(remoteServer, command);

      setAcceptHeader(httpMethod);

      if (httpMethod instanceof HttpPost) {
        String payload = new BeanToJsonConverter().convert(command.getParameters());
        ((HttpPost) httpMethod).setEntity(new StringEntity(payload, "utf-8"));
        httpMethod.addHeader("Content-Type", "application/json; charset=utf-8");
      }

      // Do not allow web proxy caches to cache responses to "get" commands
      if (httpMethod instanceof HttpGet)  {
        httpMethod.addHeader("Cache-Control", "no-cache");
      }

      log(LogType.PROFILER, new HttpProfilerLogEntry(command.getName(), true));
      HttpResponse response = fallBackExecute(context, httpMethod);
      log(LogType.PROFILER, new HttpProfilerLogEntry(command.getName(), false));

      response = followRedirects(client, context, response, /* redirect count */0);

      final EntityWithEncoding entityWithEncoding = new EntityWithEncoding(response.getEntity());

      return createResponse(response, context, entityWithEncoding);
    } catch (UnsupportedCommandException e) {
      if (e.getMessage() == null || "".equals(e.getMessage())) {
        throw new UnsupportedOperationException(
            "No information from server. Command name was: " + command.getName(),
            e.getCause());
      }
      throw e;
    } catch (NullPointerException e) {
      // swallow an NPE on quit. It indicates that the sessionID is null
      // which is what we expect to be the case.
      if (QUIT.equals(command.getName())) {
        return new Response();
      } else {
        throw e;
      }
    }
  }

  private HttpResponse fallBackExecute(HttpContext context, HttpUriRequest httpMethod)
      throws IOException {
    try {
      return client.execute(targetHost, httpMethod, context);
    } catch (BindException e) {
      // If we get this, there's a chance we've used all the local ephemeral sockets
      // Sleep for a bit to let the OS reclaim them, then try the request again.
      try {
        Thread.sleep(2000);
      } catch (InterruptedException ie) {
        throw Throwables.propagate(ie);
      }
    } catch (NoHttpResponseException e) {
      // If we get this, there's a chance we've used all the remote ephemeral sockets
      // Sleep for a bit to let the OS reclaim them, then try the request again.
      try {
        Thread.sleep(2000);
      } catch (InterruptedException ie) {
        throw Throwables.propagate(ie);
      }
    }
    return client.execute(targetHost, httpMethod, context);
  }

  private void setAcceptHeader(HttpUriRequest httpMethod) {
    httpMethod.addHeader("Accept", "application/json, image/png");
  }

  private HttpResponse followRedirects(
      HttpClient client, HttpContext context, HttpResponse response, int redirectCount) {
    if (!isRedirect(response)) {
      return response;
    }

    try {
      // Make sure that the previous connection is freed.
      HttpEntity httpEntity = response.getEntity();
      if (httpEntity != null) {
        EntityUtils.consume(httpEntity);
      }
    } catch (IOException e) {
      throw new WebDriverException(e);
    }

    if (redirectCount > MAX_REDIRECTS) {
      throw new WebDriverException("Maximum number of redirects exceeded. Aborting");
    }

    String location = response.getFirstHeader("location").getValue();
    URI uri;
    try {
      uri = buildUri(context, location);

      HttpGet get = new HttpGet(uri);
      setAcceptHeader(get);
      HttpResponse newResponse = client.execute(targetHost, get, context);
      return followRedirects(client, context, newResponse, redirectCount + 1);
    } catch (URISyntaxException e) {
      throw new WebDriverException(e);
    } catch (ClientProtocolException e) {
      throw new WebDriverException(e);
    } catch (IOException e) {
      throw new WebDriverException(e);
    }
  }

  private URI buildUri(HttpContext context, String location) throws URISyntaxException {
    URI uri;
    uri = new URI(location);
    if (!uri.isAbsolute()) {
      HttpHost host = (HttpHost) context.getAttribute(HTTP_TARGET_HOST);
      uri = new URI(host.toURI() + location);
    }
    return uri;
  }

  private boolean isRedirect(HttpResponse response) {
    int code = response.getStatusLine().getStatusCode();

    return (code == 301 || code == 302 || code == 303 || code == 307)
           && response.containsHeader("location");
  }

  class EntityWithEncoding {

    private final String charSet;
    private final byte[] content;

    EntityWithEncoding(HttpEntity entity) throws IOException {
      try {
        if (entity != null) {
          content = EntityUtils.toByteArray(entity);
          charSet = EntityUtils.getContentCharSet(entity);
        } else {
          content = new byte[0];
          charSet = null;
        }
      } finally {
        EntityUtils.consume(entity);
      }
    }

    public String getContentString()
        throws UnsupportedEncodingException {
      return new String(content, charSet != null ? charSet : "utf-8");
    }

    public byte[] getContent() {
      return content;
    }

    public boolean hasEntityContent() {
      return content != null;
    }
  }


  private Response createResponse(HttpResponse httpResponse, HttpContext context,
                                  EntityWithEncoding entityWithEncoding) throws IOException {
    final Response response;

    Header header = httpResponse.getFirstHeader("Content-Type");

    if (header != null && header.getValue().startsWith("application/json")) {
      String responseAsText = entityWithEncoding.getContentString();
      try {
        response = new JsonToBeanConverter().convert(Response.class, responseAsText);
      } catch (ClassCastException e) {
        if (responseAsText != null && "".equals(responseAsText)) {
          // The remote server has died, but has already set some headers.
          // Normally this occurs when the final window of the firefox driver
          // is closed on OS X. Return null, as the return value _should_ be
          // being ignored. This is not an elegant solution.
          return null;
        }
        throw new WebDriverException("Cannot convert text to response: " + responseAsText, e);
      }
    } else {
      response = new Response();

      if (header != null && header.getValue().startsWith("image/png")) {
        response.setValue(entityWithEncoding.getContent());
      } else if (entityWithEncoding.hasEntityContent()) {
        response.setValue(entityWithEncoding.getContentString());
      }

      HttpHost finalHost = (HttpHost) context.getAttribute(HTTP_TARGET_HOST);
      String uri = finalHost.toURI();
      String sessionId = getSessionId(uri);
      if (sessionId != null) {
        response.setSessionId(sessionId);
      }

      int statusCode = httpResponse.getStatusLine().getStatusCode();
      if (!(statusCode > 199 && statusCode < 300)) {
        // 4xx represents an unknown command or a bad request.
        if (statusCode > 399 && statusCode < 500) {
          response.setStatus(ErrorCodes.UNKNOWN_COMMAND);
        } else if (statusCode > 499 && statusCode < 600) {
          // 5xx represents an internal server error. The response status should already be set, but
          // if not, set it to a general error code.
          if (response.getStatus() == ErrorCodes.SUCCESS) {
            response.setStatus(ErrorCodes.UNHANDLED_ERROR);
          }
        } else {
          response.setStatus(ErrorCodes.UNHANDLED_ERROR);
        }
      }

      if (response.getValue() instanceof String) {
        // We normalise to \n because Java will translate this to \r\n
        // if this is suitable on our platform, and if we have \r\n, java will
        // turn this into \r\r\n, which would be Bad!
        response.setValue(((String) response.getValue()).replace("\r\n", "\n"));
      }
    }
    return response;
  }

  public static String getSessionId(String uri) {
    int sessionIndex = uri.indexOf("/session/");
    if (sessionIndex != -1) {
      sessionIndex += "/session/".length();
      int nextSlash = uri.indexOf("/", sessionIndex);
      if (nextSlash != -1) {
        return uri.substring(sessionIndex, nextSlash);
      } else {
        return uri.substring(sessionIndex);
      }

    }
    return null;
  }

  private static CommandInfo get(String url) {
    return new CommandInfo(url, HttpVerb.GET);
  }

  private static CommandInfo post(String url) {
    return new CommandInfo(url, HttpVerb.POST);
  }

  private static CommandInfo delete(String url) {
    return new CommandInfo(url, HttpVerb.DELETE);
  }

  private static class CommandInfo {

    private final String url;
    private final HttpVerb verb;

    private CommandInfo(String url, HttpVerb verb) {
      this.url = url;
      this.verb = verb;
    }

    public HttpUriRequest getMethod(URL base, Command command) {
      StringBuilder urlBuilder = new StringBuilder();

      urlBuilder.append(base.toExternalForm().replaceAll("/$", ""));
      for (String part : url.split("/")) {
        if (part.length() == 0) {
          continue;
        }

        urlBuilder.append("/");
        if (part.startsWith(":")) {
          String value = get(part.substring(1), command);
          if (value != null) {
            urlBuilder.append(get(part.substring(1), command));
          }
        } else {
          urlBuilder.append(part);
        }
      }

      return verb.createMethod(urlBuilder.toString());
    }

    private String get(String propertyName, Command command) {
      if ("sessionId".equals(propertyName)) {
        SessionId id = command.getSessionId();
        if (id == null) {
          throw new WebDriverException("Session ID may not be null");
        }
        return id.toString();
      }

      // Attempt to extract the property name from the parameters
      Object value = command.getParameters().get(propertyName);
      if (value != null) {
        return Urls.urlEncode(String.valueOf(value));
      }
      return null;
    }
  }
}
