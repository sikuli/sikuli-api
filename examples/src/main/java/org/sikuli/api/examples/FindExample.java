package org.sikuli.api.examples;
import java.net.URL;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.DesktopCanvas;
import org.sikuli.api.visual.Canvas;

public class FindExample {
	
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();
	
	// Create a screen simulator running in the background
	// that will show the image of Google's search page and
	// wait for 5 seconds before closing
	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.GoogleSearchPage);
			wait(8);
			close();
		}
	};


	public static void main(String[] args) {
		simulator.start();		

		// Obtain the screen region object corresponding to the 
		// default monitor in full screen
		ScreenRegion s = new DesktopScreenRegion();	
		
		// Define an image target on the screen
		URL imageURL = Images.GoogleSearchButton;                
		Target imageTarget = new ImageTarget(imageURL);
		// Issue the find command and get a new screen region
		// 'r' corresponding to the screen region occupied
		// by the found target
		ScreenRegion r = s.find(imageTarget);		
		// Click the center of 'r'		
		mouse.click(r.getCenter());		
		// Display a box around 'r' for 1 second
		canvas.addBox(r).display(1);

		// Find another image target and perform similar operations
		imageURL = Images.GoogleMicrophoneIcon;                
		imageTarget = new ImageTarget(imageURL);    			
		r = s.find(imageTarget);       			
		mouse.rightClick(r.getCenter());
		canvas.clear().addBox(r).display(1);

		// Find another image target and perform similar operations
		imageURL = Images.GoogleSearchFeelingLuckyButton;                
		imageTarget = new ImageTarget(imageURL);    			
		r = s.find(imageTarget);
		mouse.doubleClick(r.getCenter());
		canvas.clear().addBox(r).display(1);

	}
}