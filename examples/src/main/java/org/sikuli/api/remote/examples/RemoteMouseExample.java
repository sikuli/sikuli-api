package org.sikuli.api.remote.examples;

import java.net.URL;

import org.sikuli.api.ScreenRegion;
import org.sikuli.api.remote.Remote;
import org.sikuli.api.remote.SikuliRemote;
import org.sikuli.api.robot.Mouse;

public class RemoteMouseExample {
	public static void main(String[] args) throws Exception {
		
		int port = 9000;	
		String host = "localhost";
							
		URL serverUrl = new URL("http://" + host + ":" + port + "/sikuli");
		
		Remote remote = new SikuliRemote(serverUrl);
		
		ScreenRegion s = remote.getScreenRegion();
		Mouse mouse = remote.getMouse();
		mouse.click(s.getRelativeScreenLocation(30,10));
		
	}
}
