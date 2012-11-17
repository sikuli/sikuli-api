package org.sikuli.api.remote.server;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.openqa.selenium.remote.server.DefaultDriverSessions;

public class SikuliServer {

	private int port = 5000;
	private Server server;

	public SikuliServer(int port) {
		this.port = port;
	}

	public void servletRegister() {
		server = new Server(port);
		
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		server.setHandler(contexts);
		
		DefaultDriverSessions webdriverSessions = new DefaultDriverSessions();

		Context sikuliDriverContext = new Context(contexts, "/sikuli", Context.SESSIONS);
		sikuliDriverContext.addServlet(new ServletHolder(SikuliServlet.class), "/*");
		
		sikuliDriverContext.setAttribute(SikuliServlet.SESSIONS_KEY, webdriverSessions);
	}

	private void boot() throws Exception {
		server.start();
//		server.join();
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
		System.out.println("Starting Sikuli Server ... ");
		SikuliServer server = new SikuliServer(5000);
		server.startup();
	}

}
