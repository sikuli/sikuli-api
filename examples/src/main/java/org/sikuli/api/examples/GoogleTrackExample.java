package org.sikuli.api.examples;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.sikuli.api.*;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.DesktopCanvas;
import org.sikuli.api.visual.Canvas;

import com.S3.client.bean.SoftwareStatisticsInitParams;
import com.S3.client.bean.SoftwareStatisticsProxySettings;
import com.S3.client.event.SoftwareStatisticsEvent;
import com.S3.client.manager.SoftwareStatisticsManager;
import com.boxysystems.jgoogleanalytics.FocusPoint;
import com.boxysystems.jgoogleanalytics.JGoogleAnalyticsTracker;
import com.boxysystems.jgoogleanalytics.LoggingAdapter;

import static org.sikuli.api.API.*;

public class GoogleTrackExample {

	public static void main(String[] args) throws MalformedURLException {
		
		 //Google analytics tracking code for Library Finder
		  JGoogleAnalyticsTracker tracker = new JGoogleAnalyticsTracker("Sikuli Java API","1.2.0","UA-11634531-6");

		  
		  LoggingAdapter  loggingAdapter = new LoggingAdapter(){

			@Override
			public void logError(String arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0);
			}

			@Override
			public void logMessage(String arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0);
			}
			  
		  };
		  tracker.setLoggingAdapter(loggingAdapter);
		  for (int i=0;i<1000;i++){
			  FocusPoint focusPoint = new FocusPoint("PluginLoad");
			  //focusPoint.set
			  API.pause(100);
			  tracker.trackAsynchronously(focusPoint);
		  }


		  
					
		// Open the main page of Google Code in the default web browser
//		browse(new URL("http://code.google.com"));
//
//		// Create a screen region object that corresponds to the default monitor in full screen 
//		ScreenRegion s = new DesktopScreenRegion();
//				
//		// Specify an image as the target to find on the screen
//		URL imageURL = new URL("http://code.google.com/images/code_logo.gif");                
//		Target imageTarget = new ImageTarget(imageURL);
//				
//		// Wait for the target to become visible on the screen for at most 5 seconds
//		// Once the target is visible, it returns a screen region object corresponding
//		// to the region occupied by this target
//		ScreenRegion r = s.wait(imageTarget,5000);
////		statMgr.addCustomEvent(new SoftwareStatisticsEvent("wait",new Date()));
//				
//		// Display "Hello World" next to the found target for 3 seconds
//		Canvas canvas = new DesktopCanvas();
//		canvas.add().label("Hello World");
//		canvas.display(3);
//		
//		// Click the center of the found target
//		Mouse mouse = new DesktopMouse();
//		mouse.click(r.getCenter());
		
//		statMgr.addCustomEvent(new SoftwareStatisticsEvent("click",new Date()));
//		
//		statMgr.appFinish();	
	}
}