package org.sikuli.api.examples;
import java.net.URL;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.visual.ScreenPainter;

public class FindExample {
	
	static Mouse mouse = new Mouse();
	static Keyboard keyboard = new Keyboard();
	static ScreenPainter painter = new ScreenPainter();
	
	// Create a screen simulator running in the background
	// that will show the image of Google's search page and
	// wait for 5 seconds before closing
	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.GoogleSearchPage);
			wait(5000);
			close();
		}
	};


	public static void main(String[] args) {
		simulator.start();		

		// Obtain the screen region object corresponding to the 
		// default monitor in full screen
		ScreenRegion s = new ScreenRegion();	
		
		// Define an image target on the screen
		URL imageURL = Images.GoogleSearchButton;                
		Target imageTarget = new ImageTarget(imageURL);
		// Issue the find command and get a new screen region
		// 'r' corresponding to the screen region occupied
		// by the found target
		ScreenRegion r = s.find(imageTarget);		
		// Click the center of 'r'		
		mouse.click(r.getCenter());		
		// Display a circle at the center of 'r'
		painter.circle(r.getCenter(), 1000);

		// Find another image target and perform similar operations
		imageURL = Images.GoogleMicrophoneIcon;                
		imageTarget = new ImageTarget(imageURL);    			
		r = s.find(imageTarget);       			
		mouse.rightClick(r.getCenter());
		painter.circle(r.getCenter(), 1000);

		// Find another image target and perform similar operations
		imageURL = Images.GoogleSearchFeelingLuckyButton;                
		imageTarget = new ImageTarget(imageURL);    			
		r = s.find(imageTarget);
		mouse.doubleClick(r.getCenter());
		painter.circle(r.getCenter(), 1000);

	}
}