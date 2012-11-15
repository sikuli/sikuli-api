/*
Copyright 2012 Selenium committers
Copyright 2012 Software Freedom Conservancy

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

package org.sikuli.api.remote.server;

import static org.openqa.selenium.remote.server.HttpStatusCodes.INTERNAL_SERVER_ERROR;
import static org.openqa.selenium.remote.server.HttpStatusCodes.NOT_FOUND;

import java.util.EnumSet;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.SessionTerminatedException;
import org.openqa.selenium.remote.server.DriverSessions;
import org.openqa.selenium.remote.server.HttpRequest;
import org.openqa.selenium.remote.server.HttpResponse;
import org.openqa.selenium.remote.server.handler.ChangeUrl;
import org.openqa.selenium.remote.server.handler.DescribeElement;
import org.openqa.selenium.remote.server.handler.FindActiveElement;
import org.openqa.selenium.remote.server.handler.FindChildElement;
import org.openqa.selenium.remote.server.handler.FindChildElements;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.remote.server.handler.GetAllSessions;
import org.openqa.selenium.remote.server.handler.NewSession;
import org.openqa.selenium.remote.server.handler.Status;
import org.openqa.selenium.remote.server.renderer.EmptyResult;
import org.openqa.selenium.remote.server.renderer.JsonErrorExceptionResult;
import org.openqa.selenium.remote.server.renderer.JsonResult;
import org.openqa.selenium.remote.server.renderer.RedirectResult;
import org.openqa.selenium.remote.server.rest.RestishHandler;
import org.openqa.selenium.remote.server.rest.Result;
import org.openqa.selenium.remote.server.rest.ResultConfig;
import org.openqa.selenium.remote.server.rest.ResultType;
import org.openqa.selenium.remote.server.rest.UrlMapper;
import org.openqa.selenium.remote.server.xdrpc.CrossDomainRpcRenderer;
import org.sikuli.api.remote.client.RemoteMethodConfig;
import org.sikuli.api.remote.client.RemoteMethod;

interface MimeType {
	static final String EMPTY = "";
	static final String CROSS_DOMAIN_RPC = "application/xdrpc";
}


public class SikuliRemoteConfig {
	private static final String EXCEPTION = ":exception";
	private static final String RESPONSE = ":response";

	private UrlMapper getMapper;
	private UrlMapper postMapper;
	private UrlMapper deleteMapper;
	private final Logger log;

	public SikuliRemoteConfig(DriverSessions sessions, Logger log) {
		this.log = log;
		setUpMappings(sessions, log);
	}

	public void addGlobalHandler(ResultType type, Result result) {
		getMapper.addGlobalHandler(type, result);
		postMapper.addGlobalHandler(type, result);
		deleteMapper.addGlobalHandler(type, result);
	}

	public ResultConfig addNewGetMapping(String path, Class<? extends RestishHandler> implementationClass) {
		return getMapper.bind(path, implementationClass);
	}

	public ResultConfig addNewPostMapping(String path, Class<? extends RestishHandler> implementationClass) {
		return postMapper.bind(path, implementationClass);
	}

	public ResultConfig addNewDeleteMapping(String path,
			Class<? extends RestishHandler> implementationClass) {
		return deleteMapper.bind(path, implementationClass);
	}

	public void handleRequest(HttpRequest request, HttpResponse response,
			HttpServletRequest servletRequest)
			throws WebDriverException {
		try {
			UrlMapper mapper = getUrlMapper(request.getMethod());
			if (mapper == null) {
				response.setStatus(INTERNAL_SERVER_ERROR);
				response.end();
				return;
			}

			ResultConfig config = mapper.getConfig(request.getPath());			
			if (config == null) {
				response.setStatus(NOT_FOUND);
				response.end();
			} else {				
				config.handle(request.getPath(), request, response);
			}
		} catch (SessionTerminatedException e){
			response.setStatus(NOT_FOUND);
			response.end();
		} catch (Exception e) {
			log.warning("Fatal, unhandled exception: " + request.getPath() + ": " + e);
			throw new WebDriverException(e);
		}
	}

	private UrlMapper getUrlMapper(String method) {
		if ("DELETE".equals(method)) {
			return deleteMapper;
		} else if ("GET".equals(method)) {
			return getMapper;
		} else if ("POST".equals(method)) {
			return postMapper;
		} else {
			throw new IllegalArgumentException("Unknown method: " + method);
		}
	}

	
	private void setUpMappings(DriverSessions driverSessions, Logger logger) {
		final EmptyResult emptyResponse = new EmptyResult();
		final JsonResult jsonResponse = new JsonResult(RESPONSE);

		getMapper = new UrlMapper(driverSessions, logger);
		postMapper = new UrlMapper(driverSessions, logger);
		deleteMapper = new UrlMapper(driverSessions, logger);

		Result jsonErrorResult = new Result(MimeType.EMPTY,
				new JsonErrorExceptionResult(EXCEPTION, RESPONSE));
		addGlobalHandler(ResultType.EXCEPTION, jsonErrorResult);
		addGlobalHandler(ResultType.ERROR, jsonErrorResult);

		Result xdrpcResult = new Result(MimeType.CROSS_DOMAIN_RPC,
				new CrossDomainRpcRenderer(RESPONSE, EXCEPTION), true);
		for (ResultType resultType : EnumSet.allOf(ResultType.class)) {
			addGlobalHandler(resultType, xdrpcResult);
		}

		
		
		for (RemoteMethod<?> m : RemoteMethodConfig.getRemoteMethods()){
			postMapper.bind(m.getName(), m.getClass()).on(ResultType.SUCCESS, jsonResponse);
		}
//		
//		//getBind(new RemoteScreen.GetSize()).on(ResultType.SUCCESS, jsonResponse);
//		//postBind(new RemoteScreen.GetScreenshot()).on(ResultType.SUCCESS, jsonResponse);
//		
////		postMapper.bind("/screen/" + Screen.GET_SCREENSHOT, RemoteScreen.GetScreenshot.class)
////		.on(ResultType.SUCCESS, jsonResponse);
//
//		
////		postMapper.bind("/mouse/" + Mouse.CLICK, RemoteMouse.Click.class)
////		.on(ResultType.SUCCESS, emptyResponse);
////
////		postMapper.bind("/mouse/" + Mouse.DOUBLE_CLICK, RemoteMouse.DoubleClick.class)
////		.on(ResultType.SUCCESS, emptyResponse);
////
////		postMapper.bind("/mouse/" + Mouse.RIGHT_CLICK, RemoteMouse.RightClick.class)
////		.on(ResultType.SUCCESS, emptyResponse);
//		
//
//		
//		// When requesting the command root from a JSON-client, just return the server
//		// status. For everyone else, redirect to the web front end.
//		//		getMapper.bind("/", Status.class)
//		//		.on(ResultType.SUCCESS, new RedirectResult("/static/resource/hub.html"))
//		//		.on(ResultType.SUCCESS, jsonResponse, "application/json");
//
//		//		getMapper.bind("/static/resource/:file", StaticResource.class)
//		//		.on(ResultType.SUCCESS, new ResourceCopyResult(RESPONSE))
//		//		// Nope, JSON clients don't get access to static resources.
//		//		.on(ResultType.SUCCESS, emptyResponse, "application/json");
//		//
//		//		postMapper.bind("/config/drivers", AddConfig.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//
//		getMapper.bind("/status", Status.class)
//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		getMapper.bind("/sessions", GetAllSessions.class)
//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//postMapper.bind("/session", NewSession.class)
//		getMapper.bind("/session", NewSession.class)
//		//		.on(ResultType.SUCCESS, new RedirectResult("/session/:sessionId"));
//		.on(ResultType.SUCCESS, new RedirectResult("/sessions"));
//		//		getMapper.bind("/session/:sessionId", GetSessionCapabilities.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		.on(ResultType.SUCCESS, new ForwardResult("/WEB-INF/views/sessionCapabilities.jsp"))
//		//		.on(ResultType.SUCCESS, jsonResponse, "application/json");
//		//
////		deleteMapper.bind("/session/:sessionId", DeleteSession.class)
////		.on(ResultType.SUCCESS, emptyResponse);
////		
//		
//
//		
//		
////		getMapper.bind("/find", NewSession.class)
////		//		.on(ResultType.SUCCESS, new RedirectResult("/session/:sessionId"));
////		.on(ResultType.SUCCESS, new RedirectResult("/sessions"));
//
//		
//		//
//		//		getMapper.bind("/session/:sessionId/window_handle", GetCurrentWindowHandle.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		getMapper.bind("/session/:sessionId/window_handles", GetAllWindowHandles.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//		postMapper.bind("/session/:sessionId/dismiss_alert", DismissAlert.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/accept_alert", AcceptAlert.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		getMapper.bind("/session/:sessionId/alert_text", GetAlertText.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/alert_text", SetAlertText.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//
//				postMapper.bind("/session/:sessionId/url", ChangeUrl.class)
//				.on(ResultType.SUCCESS, emptyResponse);
//		//		getMapper.bind("/session/:sessionId/url", GetCurrentUrl.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//		postMapper.bind("/session/:sessionId/forward", GoForward.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/back", GoBack.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/refresh", RefreshPage.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//
//		//		postMapper.bind("/session/:sessionId/execute", ExecuteScript.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/execute_async", ExecuteAsyncScript.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//		getMapper.bind("/session/:sessionId/source", GetPageSource.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//
//		//		getMapper.bind("/session/:sessionId/title", GetTitle.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//				postMapper.bind("/session/:sessionId/element", FindElement.class)
//				.on(ResultType.SUCCESS, jsonResponse);
//				getMapper.bind("/session/:sessionId/element/:id", DescribeElement.class)
//				.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//		postMapper.bind("/session/:sessionId/elements", FindElements.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//				postMapper.bind("/session/:sessionId/element/active", FindActiveElement.class)
//				.on(ResultType.SUCCESS, jsonResponse);
//		//
//				postMapper.bind("/session/:sessionId/element/:id/element", FindChildElement.class)
//				.on(ResultType.SUCCESS, jsonResponse);
//				postMapper.bind("/session/:sessionId/element/:id/elements", FindChildElements.class)
//				.on(ResultType.SUCCESS, jsonResponse);
//				
//				
//				
//		//
//		//
//		//		postMapper.bind("/session/:sessionId/element/:id/click", ClickElement.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		getMapper.bind("/session/:sessionId/element/:id/text", GetElementText.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/element/:id/submit", SubmitElement.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//
//		//		postMapper.bind("/session/:sessionId/file", UploadFile.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/element/:id/value", SendKeys.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		getMapper.bind("/session/:sessionId/element/:id/value", GetElementValue.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		getMapper.bind("/session/:sessionId/element/:id/name", GetTagName.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//		postMapper.bind("/session/:sessionId/element/:id/clear", ClearElement.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		getMapper.bind("/session/:sessionId/element/:id/selected", GetElementSelected.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		getMapper.bind("/session/:sessionId/element/:id/enabled", GetElementEnabled.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		getMapper.bind("/session/:sessionId/element/:id/displayed", GetElementDisplayed.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		getMapper.bind("/session/:sessionId/element/:id/location", GetElementLocation.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		getMapper.bind("/session/:sessionId/element/:id/location_in_view",
//		//				GetElementLocationInView.class)
//		//				.on(ResultType.SUCCESS, jsonResponse);
//		//		getMapper.bind("/session/:sessionId/element/:id/size", GetElementSize.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		getMapper.bind("/session/:sessionId/element/:id/css/:propertyName", GetCssProperty.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//		getMapper.bind("/session/:sessionId/element/:id/attribute/:name", GetElementAttribute.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		getMapper.bind("/session/:sessionId/element/:id/equals/:other", ElementEquality.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//		getMapper.bind("/session/:sessionId/cookie", GetAllCookies.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/cookie", AddCookie.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		deleteMapper.bind("/session/:sessionId/cookie", DeleteCookie.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		deleteMapper.bind("/session/:sessionId/cookie/:name", DeleteNamedCookie.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//
//		//		postMapper.bind("/session/:sessionId/frame", SwitchToFrame.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/window", SwitchToWindow.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		deleteMapper.bind("/session/:sessionId/window", CloseWindow.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//
//		//		getMapper.bind("/session/:sessionId/window/:windowHandle/size", GetWindowSize.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/window/:windowHandle/size", SetWindowSize.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		getMapper.bind("/session/:sessionId/window/:windowHandle/position", GetWindowPosition.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/window/:windowHandle/position", SetWindowPosition.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/window/:windowHandle/maximize", MaximizeWindow.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//
//		//		postMapper.bind("/session/:sessionId/timeouts", ConfigureTimeout.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/timeouts/implicit_wait", ImplicitlyWait.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/timeouts/async_script", SetScriptTimeout.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//
//		//		postMapper.bind("/session/:sessionId/execute_sql", ExecuteSQL.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//		getMapper.bind("/session/:sessionId/location", GetLocationContext.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/location", SetLocationContext.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//
//		//		getMapper.bind("/session/:sessionId/application_cache/status", GetAppCacheStatus.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/browser_connection", SetBrowserConnection.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		getMapper.bind("/session/:sessionId/browser_connection", IsBrowserOnline.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//		getMapper.bind("/session/:sessionId/local_storage/key/:key", GetLocalStorageItem.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		deleteMapper.bind("/session/:sessionId/local_storage/key/:key", RemoveLocalStorageItem.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		getMapper.bind("/session/:sessionId/local_storage", GetLocalStorageKeys.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/local_storage", SetLocalStorageItem.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		deleteMapper.bind("/session/:sessionId/local_storage", ClearLocalStorage.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		getMapper.bind("/session/:sessionId/local_storage/size", GetLocalStorageSize.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//		getMapper.bind("/session/:sessionId/session_storage/key/:key", GetSessionStorageItem.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		deleteMapper.bind("/session/:sessionId/session_storage/key/:key", RemoveSessionStorageItem.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		getMapper.bind("/session/:sessionId/session_storage", GetSessionStorageKeys.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/session_storage", SetSessionStorageItem.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		deleteMapper.bind("/session/:sessionId/session_storage", ClearSessionStorage.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		getMapper.bind("/session/:sessionId/session_storage/size", GetSessionStorageSize.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//		getMapper.bind("/session/:sessionId/orientation", GetScreenOrientation.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/orientation", Rotate.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//
//		//		postMapper.bind("/session/:sessionId/moveto", MouseMoveToLocation.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/click", ClickInSession.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/doubleclick", DoubleClickInSession.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/buttondown", MouseDown.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/buttonup", MouseUp.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/keys", SendKeyToActiveElement.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//
//		//		getMapper.bind("/session/:sessionId/ime/available_engines", ImeGetAvailableEngines.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		getMapper.bind("/session/:sessionId/ime/active_engine", ImeGetActiveEngine.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		getMapper.bind("/session/:sessionId/ime/activated", ImeIsActivated.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/ime/deactivate", ImeDeactivate.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/ime/activate", ImeActivateEngine.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//
//		//		// Advanced Touch API
//		//		postMapper.bind("/session/:sessionId/touch/click", SingleTapOnElement.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/touch/down", Down.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/touch/up", Up.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/touch/move", Move.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/touch/scroll", Scroll.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/touch/doubleclick", DoubleTapOnElement.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/touch/longclick", LongPressOnElement.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//		postMapper.bind("/session/:sessionId/touch/flick", Flick.class)
//		//		.on(ResultType.SUCCESS, emptyResponse);
//		//
//		//		getMapper.bind("/session/:sessionId/log/types", GetAvailableLogTypesHandler.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/session/:sessionId/log", GetLogHandler.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
//		//		postMapper.bind("/logs", GetSessionLogsHandler.class)
//		//		.on(ResultType.SUCCESS, jsonResponse);
	}
}
