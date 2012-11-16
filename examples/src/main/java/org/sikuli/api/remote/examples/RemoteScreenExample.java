package org.sikuli.api.remote.examples;

import java.awt.image.BufferedImage;
import java.net.URL;

import org.sikuli.api.Screen;

import org.sikuli.api.examples.ScreenSimulator;
import org.sikuli.api.remote.Remote;
import org.sikuli.api.remote.SikuliRemote;

public class RemoteScreenExample {
	public static void main(String[] args) throws Exception {
		
		int port = 5000;	
		//String host = "172.16.121.128";
		String host = "localhost";
							
		URL serverUrl = new URL("http://" + host + ":" + port + "/sikuli");
		
		//URL	imageUrl = new URL("http://code.google.com/images/code_logo.gif");
		

		Remote remote = new SikuliRemote(serverUrl);
		Screen screen = remote.getScreen();		
		screen.getSize();
				
		System.out.println(screen);
		
		final BufferedImage image = screen.getScreenshot(0, 0, 500, 500);
//		
		ScreenSimulator simulator = new ScreenSimulator(){
			public void run(){
				showImage(image);
				wait(5000);
				close();
			}
		};
		simulator.start();

		
		
	}
}
