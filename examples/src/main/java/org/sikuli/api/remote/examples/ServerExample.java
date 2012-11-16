package org.sikuli.api.remote.examples;


import org.sikuli.api.remote.server.SikuliRemoteServer;

public class ServerExample {

	public static void main(String[] args) throws Exception {
	
		int port = 5000;		
			
		SikuliRemoteServer server = new SikuliRemoteServer(port);
		server.startup();
	}
}
