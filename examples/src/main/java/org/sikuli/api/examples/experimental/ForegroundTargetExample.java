package org.sikuli.api.examples.experimental;

import java.awt.Rectangle;
import java.util.List;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ForegroundTarget;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.examples.images.Images;
import org.sikuli.api.examples.utils.ScreenSimulator;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class ForegroundTargetExample {
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();
	
	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.SceneDogCat2);			
			wait(8);
			close();
		}
	};
	
	
	public static void main(String[] args) {
		simulator.start();		
		Rectangle b = simulator.getBounds();
		ScreenRegion s = new DesktopScreenRegion(b.x+10,b.y+30,b.width-20,b.height-40);	
		
		canvas.addBox(s);
		canvas.addLabel(Relative.to(s).topLeft().getScreenLocation(), "Observed region");
		canvas.display(2);
		
		Target target = new ForegroundTarget();
		ScreenRegion r = s.find(target);		
		
		canvas.clear();
		canvas.addBox(r);
		canvas.addLabel(Relative.to(s).topLeft().getScreenLocation(), "find");
		canvas.addLabel(Relative.to(r).topLeft().getScreenLocation(), "foreground object");
		canvas.display(2);
		
		List<ScreenRegion> rs = s.findAll(target);
		canvas.clear();
		canvas.addLabel(Relative.to(s).topLeft().getScreenLocation(), "findAll");
		for (ScreenRegion x : rs){			
			canvas.addBox(x);
			canvas.addLabel(Relative.to(x).topLeft().getScreenLocation(), "foreground object");
		}
		canvas.display(2);
	}
	
}
