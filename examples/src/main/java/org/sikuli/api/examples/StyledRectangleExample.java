package org.sikuli.api.examples;
import java.awt.Rectangle;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.StyledRectangleTarget;
import org.sikuli.api.Target;
import org.sikuli.api.VisualModelFinder;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;
import org.sikuli.core.logging.ImageExplainer;
import org.sikuli.core.logging.ImageExplainer.Level;

public class StyledRectangleExample {
	
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.Calculator);
//			try {
//				//showImage(new URL("file:ButtonGroups.png"));
////				showImage(new URL("file:swing-averager.jpg"));
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			wait(10);
			close();
		}
	};

	public static void main(String[] args) throws MalformedURLException {

		simulator.start();
		ImageExplainer.getExplainer(VisualModelFinder.class).setLevel(Level.STEP);
		
		Rectangle b = simulator.getBounds();
		ScreenRegion s = new DesktopScreenRegion(b.x,b.y,b.width,b.height);

		StyledRectangleTarget target = new StyledRectangleTarget(Images.CalculatorButton);
//		Target target = new StyledRectangleTarget(new URL("file:wally.png"));
//		Target target = new ImageTarget(Images.CalculatorButton);
		target.setLimit(20);
		
		List<ScreenRegion> rs = s.findAll(target);
		
				
		for (ScreenRegion r : rs){
			canvas.addBox(r);
		}
		canvas.display(3);				

	}

}