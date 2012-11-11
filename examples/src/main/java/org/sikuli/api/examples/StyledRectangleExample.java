package org.sikuli.api.examples;
import java.awt.Rectangle;
import java.util.List;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.StyledRectangleTarget;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.visual.ScreenPainter;

import static org.sikuli.api.API.*;

public class StyledRectangleExample {
	
	static Mouse mouse = new Mouse();
	static Keyboard keyboard = new Keyboard();
	static ScreenPainter painter = new ScreenPainter();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.OSXNetworkPreferences);
			wait(5000);
			showImage(Images.GoogleSearchPage);
			wait(5000);
			close();
		}
	};

	public static void main(String[] args) {

		simulator.setSize(800,800);
		simulator.start();
		
		Rectangle b = simulator.getBounds();
		ScreenRegion s = new DesktopScreenRegion(b.x,b.y,b.width,b.height);

		StyledRectangleTarget target = new StyledRectangleTarget(Images.ButtonOptions);
		List<ScreenRegion> rs = s.findAll(target);
		for (ScreenRegion r : rs){
			painter.box(r, 3000);
		}

		pause(5000);

		target = new StyledRectangleTarget(Images.GoogleSearchButton);
		rs = s.findAll(target);
		for (ScreenRegion r : rs){
			painter.box(r, 3000);
		}

	}

}