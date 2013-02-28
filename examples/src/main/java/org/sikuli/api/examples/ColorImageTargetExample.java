package org.sikuli.api.examples;
import java.awt.Rectangle;
import java.util.List;

import org.sikuli.api.ColorImageTarget;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class ColorImageTargetExample {

	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.ColorStars);
			pause();
			showImage(Images.ColorBullets);
			pause();
			close();
		}
	};
	
	public static void main(String[] args) {

		simulator.start();
				
		Rectangle b = simulator.getBounds();
		
		final DesktopScreenRegion s = new DesktopScreenRegion(b.x, b.y, b.width, b.height);


		// Finding color stars

		Target blueTarget = new ColorImageTarget(Images.BlueStar);
		Target redTarget = new ColorImageTarget(Images.RedStar);
		Target greenTarget = new ColorImageTarget(Images.GreenStar);

		List<ScreenRegion> blueTargetRegions = s.findAll(blueTarget);
		List<ScreenRegion> greenTargetRegions = s.findAll(greenTarget);
		List<ScreenRegion> redTargetRegions = s.findAll(redTarget);
		
		for (ScreenRegion r : blueTargetRegions){
			canvas.addBox(r);
			canvas.addLabel(r, "blue");			
		}

		for (ScreenRegion r : redTargetRegions){
			canvas.addBox(r);
			canvas.addLabel(r, "red");			
		}

		for (ScreenRegion r : greenTargetRegions){
			canvas.addBox(r);
			canvas.addLabel(r, "green");			
		}
		canvas.display(3);
		
		simulator.resume();
		
		canvas.clear();
		
		// Finding color bullets
		ScreenRegion greenBullet = s.wait(new ColorImageTarget(Images.GreenBullet),10000);
		ScreenRegion redBullet = s.find(new ColorImageTarget(Images.RedBullet));
		ScreenRegion silverBullet = s.find(new ColorImageTarget(Images.SilverBullet));
		
		canvas.addBox(greenBullet);		
		canvas.addLabel(Relative.to(greenBullet).topLeft().above(20).getScreenLocation(), "green");
		
		canvas.addBox(redBullet);
		canvas.addLabel(Relative.to(redBullet).topLeft().above(20).getScreenLocation(), "red");
		
		canvas.addBox(silverBullet);
		canvas.addLabel(Relative.to(silverBullet).topLeft().above(20).getScreenLocation(), "silver");
		
		canvas.display(3);
		
		simulator.resume();

	}
}