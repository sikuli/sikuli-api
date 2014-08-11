package org.sikuli.api.examples.basic;
import java.awt.Rectangle;
import java.util.List;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.StyledRectangleTarget;
import org.sikuli.api.Target;
import org.sikuli.api.examples.images.Images;
import org.sikuli.api.examples.utils.ScreenSimulator;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class OrderingExample {

	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.OSXSharingPreferences);
			wait(12);
			close();
		}
	};
	
	public static void main(String[] args) {
	
		simulator.setSize(800,800);
		simulator.start();
		
		Rectangle b = simulator.getBounds();
		ScreenRegion s = new DesktopScreenRegion(b.x,b.y,b.width,b.height);	

		Target target;
		List<ScreenRegion> rs;

		// find all unchecked checkboxes ordered from bottom to top	
		
		target = new ImageTarget(Images.UncheckedCheckbox);
		target.setLimit(15);
		target.setOrdering(Target.Ordering.BOTTOM_UP);
		
		rs = s.findAll(target);
		
		canvas.addLabel(Relative.to(s).topLeft().getScreenLocation(), "Unchecked checkboxes found in bottom-up ordering");
		for (int i=0; i < rs.size(); ++i){
			ScreenRegion r = rs.get(i);
			canvas.addBox(r);
			canvas.addLabel(Relative.to(r).topLeft().left(20).getScreenLocation(), ""+(i+1));
		}
		canvas.display(5);
				
		// find all styled rectangles (e.g., buttons) ordered from left to right
	
		target = new StyledRectangleTarget(Images.ButtonOptions);
		target.setOrdering(Target.Ordering.LEFT_RIGHT);
		
		rs = s.findAll(target);
		
		canvas.clear().addLabel(Relative.to(s).topLeft().getScreenLocation(), "Rectangles found in left-right ordering");
		for (int i=0; i < rs.size(); ++i){
			ScreenRegion r = rs.get(i);
			canvas.addBox(r);
			canvas.addLabel(Relative.to(r).topLeft().left(20).getScreenLocation(), ""+(i+1));
		}
		canvas.display(5);

	}
}