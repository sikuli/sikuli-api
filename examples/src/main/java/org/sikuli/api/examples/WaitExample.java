package org.sikuli.api.examples;
import java.net.URL;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;

public class WaitExample {

	static Mouse mouse = new Mouse();
	static Keyboard keyboard = new Keyboard();
	
	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.OSXSystemPreferences);
			waitForClick();
			showImage(Images.OSXDockPreferences);
			wait(10000);
			close();
		}
	};
	
	public static void main(String[] args) {		
		
		simulator.start();

		ScreenRegion s = new ScreenRegion();

		URL imageURL = Images.OSXDockIcon;                
		Target imageTarget = new ImageTarget(imageURL);

		ScreenRegion r = s.find(imageTarget);
		mouse.click(r.getCenter());		

		imageURL = Images.ThumbIcon;                
		imageTarget = new ImageTarget(imageURL);

		r = s.wait(imageTarget, 5000);
		mouse.click(r.getCenter());

		imageURL = Images.CheckedCheckbox;                
		imageTarget = new ImageTarget(imageURL);    			

		r = s.wait(imageTarget, 5000);
		mouse.click(r.getCenter());
		
		imageURL = Images.OSXDockIcon;
		imageTarget = new ImageTarget(imageURL);
		r = s.wait(imageTarget, 5000);
		

	}
}