package org.sikuli.api;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class API {
	
	static public void pause(int mills){		
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
		}		
	}

	static public void browse(URL url) {
		try {
			Desktop.getDesktop().browse(new URI(url.toString()));
		} catch (URISyntaxException e) {
		} catch (IOException e) {
		}
	}
	
}
