package org.sikuli.api.remote.server;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.openqa.selenium.remote.server.DefaultDriverSessions;

public class SikuliRemoteServer {

	private int port = 9000;
	private Server server;

	public SikuliRemoteServer(int port) {
		this.port = port;
	}

	public void servletRegister() {
		server = new Server(port);
		
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		server.setHandler(contexts);
		
		
	    DefaultDriverSessions webdriverSessions = new DefaultDriverSessions();

//		Context rootContext = new Context(contexts,"/",Context.SESSIONS);
//		rootContext.addServlet(new ServletHolder(new ScriptServlet()), "/script.do");
		
		Context sikuliDriverContext = new Context(contexts, "/sikuli", Context.SESSIONS);
		sikuliDriverContext.addServlet(new ServletHolder(SikuliServlet.class), "/*");
		
		sikuliDriverContext.setAttribute(SikuliServlet.SESSIONS_KEY, webdriverSessions);
	}

//	private Context createWebDriverRemoteContext(){
//
//		//		    long sessionTimeout = configuration.getTimeoutInSeconds();
//		//		    if (sessionTimeout == 0) {
//		//		      sessionTimeout = -1;
//		//		    }
//		//		    long browserTimeout = configuration.getBrowserTimeoutInMs();
//		//		    if (browserTimeout == 0) {
//		//		      browserTimeout = -1;
//		//		    } else {
//		//		      browserTimeout /= 1000;
//		//		    }
//		//		    webdriverContext.setInitParameter("webdriver.server.session.timeout", String.valueOf(sessionTimeout));
//		//		    webdriverContext.setInitParameter("webdriver.server.browser.timeout", String.valueOf(browserTimeout));
//		//		    webdriverContext.setAttribute(DriverServlet.SESSIONS_KEY, webDriverSessions);
//		//		    webdriverContext.setContextPath("/wd")
//		;
//			//ServletHandler handler = new ServletHandler();
//			
//		//handler.addServlet("Sikuli remote server", "/sikuli/*", SikuliServlet.class.getName());
//		//webdriverContext.addHandler(handler);
//
//		//return webdriverContext;
//	}

	private void boot() throws Exception {
		server.start();
		//server.join();
	}

	public void startup() throws Exception {
		servletRegister();
		boot();
	}

	public Server getServer() {
		return server;
	}

	public void stop() throws Exception {
		server.stop();
	}

	public static void main(String[] args) throws Exception {
		SikuliRemoteServer server = new SikuliRemoteServer(9000);
		server.startup();
	}

}
