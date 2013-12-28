package org.sikuli.api;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.sikuli.api.usage.NoOpUsageTracker;
import org.sikuli.api.usage.UsageTracker;
/**
 * API class that defines a set of methods that perform common operations in the API.
 */
public class API {	

	/**
	 * Causes the currently executing thread to pause (sleep) for the specified number of milliseconds.
	 * 
	 * @param mills the length of time to sleep in milliseconds.
	 */
	static public void pause(int mills){		
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
		}		
	}
	/**
	 * Launches the default browser to open a URL.
	 * 
	 * @param url the URL to be displayed in the user default browser.
	 */
	static public void browse(URL url) {
		try {
			Desktop.getDesktop().browse(new URI(url.toString()));
		} catch (URISyntaxException e) {
		} catch (IOException e) {
		}
	}
	
	static private UsageTracker usageTracker = new NoOpUsageTracker();
	static public UsageTracker getTracker(){
		return usageTracker;
	}
	
	static public void setTracker(UsageTracker tracker){
		usageTracker = tracker;
	}
	
}
