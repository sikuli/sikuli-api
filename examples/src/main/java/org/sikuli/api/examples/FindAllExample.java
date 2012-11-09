package org.sikuli.api.examples;
import java.util.List;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.visual.ScreenPainter;

public class FindAllExample {
	
	static Mouse mouse = new Mouse();
	static Keyboard keyboard = new Keyboard();
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
		
		ScreenRegion s = new ScreenRegion();
		Target imageTarget = new ImageTarget(Images.ThumbIcon);
	
		List<ScreenRegion> rs = s.findAll(imageTarget);
		int no = 1;
		for (ScreenRegion r : rs){
			painter.box(r, 3000);
			String labelText = String.format("(%d):%1.3f", no, r.getScore());
			painter.label(r, labelText, 3000);
			no += 1;
		}
	}
}