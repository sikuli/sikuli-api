package org.sikuli.api.examples.advanced;
import java.awt.Rectangle;
import java.net.MalformedURLException;
import java.util.List;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.StyledRectangleTarget;
import org.sikuli.api.examples.images.Images;
import org.sikuli.api.examples.utils.ScreenSimulator;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class StyledRectangleExample {
	
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.Calculator);
			wait(10);
			close();
		}
	};

	public static void main(String[] args) throws MalformedURLException {

		simulator.start();
		
		Rectangle b = simulator.getBounds();
		ScreenRegion s = new DesktopScreenRegion(b.x,b.y,b.width,b.height);

		StyledRectangleTarget target = new StyledRectangleTarget(Images.CalculatorButton);
		target.setLimit(20);
		
		List<ScreenRegion> rs = s.findAll(target);
		for (ScreenRegion r : rs){
			canvas.add().box().around(r);
		}
		canvas.display(3);				
	}

}