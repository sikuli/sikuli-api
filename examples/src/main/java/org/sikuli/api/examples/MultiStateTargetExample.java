package org.sikuli.api.examples;
import java.awt.Rectangle;
import java.util.List;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.MultiStateTarget;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.ScreenPainter;

public class MultiStateTargetExample {

	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static ScreenPainter painter = new ScreenPainter();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.OSXDockPreferences);
			wait(5000);
			close();
		}
	};

	public static void main(String[] args) {
		simulator.start();
		Rectangle b = simulator.getBounds();
		ScreenRegion s = new DesktopScreenRegion(b.x,b.y,b.width,b.height);
				
		// create an image target based on an image of a checked checkbox
		ImageTarget checked = new ImageTarget(Images.CheckedCheckbox);
		// create an image target based on an image of an unchecked checkbox
		ImageTarget unchecked = new ImageTarget(Images.UncheckedCheckbox);
		
		// create a multi-state target to look for checkboxes
		MultiStateTarget target = new MultiStateTarget();
		// add the "checked" state specified by the image target of a checked checkbox
		target.addState(checked, "checked");
		// add the "unchecked" state specified by the image target of an unchecked checkbox
		target.addState(unchecked, "unchecked");
				
		// find all the checkboxes, both checked and unchecked
		List<ScreenRegion> checkboxes = s.findAll(target);
		for (ScreenRegion c : checkboxes){
			// get the state of each checkbox
			String state = (String) c.getState();
			// display the state next to each checkbox to visualize
			ScreenLocation labelLocation = Relative.to(c).topLeft().left(70).getScreenLocation();
			painter.label(labelLocation, state, 3000);
		}
	}
}