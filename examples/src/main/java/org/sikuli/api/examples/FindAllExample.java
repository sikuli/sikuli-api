package org.sikuli.api.examples;
import java.util.List;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.DesktopCanvas;
import org.sikuli.api.visual.Canvas;

public class FindAllExample {
	
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.OSXDockPreferences);
			wait(5);
			close();
		}
	};
	
	public static void main(String[] args) {
		simulator.start();
		
		ScreenRegion s = new DesktopScreenRegion();
		Target imageTarget = new ImageTarget(Images.ThumbIcon);
	
		List<ScreenRegion> rs = s.findAll(imageTarget);
		int no = 1;
		for (ScreenRegion r : rs){
			canvas.addBox(r);
			String labelText = String.format("(%d):%1.3f", no, r.getScore());
			canvas.addLabel(r, labelText);
			no += 1;
		}
		canvas.display(3);
	}
}