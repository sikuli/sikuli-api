package org.sikuli.api.examples.basic;
import java.net.URL;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.examples.images.Images;
import org.sikuli.api.examples.utils.ScreenSimulator;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;

public class WaitExample {

	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	
	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.OSXSystemPreferences);
			waitForClick();
			showImage(Images.OSXDockPreferences);
			wait(10);
			close();
		}
	};
	
	public static void main(String[] args) {		
		
		simulator.start();

		ScreenRegion s = new DesktopScreenRegion();

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