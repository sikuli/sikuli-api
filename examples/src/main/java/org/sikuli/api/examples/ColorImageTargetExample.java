package org.sikuli.api.examples;
import java.awt.Rectangle;
import java.util.List;

import org.sikuli.api.ColorImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.visual.ScreenPainter;

public class ColorImageTargetExample {

	static Mouse mouse = new Mouse();
	static Keyboard keyboard = new Keyboard();
	static ScreenPainter painter = new ScreenPainter();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.ColorStars);
			wait(5000);
			showImage(Images.ColorBullets);
			wait(5000);
			close();
		}
	};
	
	public static void main(String[] args) {

		simulator.start();
				
		Rectangle b = simulator.getBounds();
		
		final ScreenRegion s = new ScreenRegion(b.x, b.y, b.width, b.height);
		painter.box(s, 10000);

		// Finding color stars

		Target blueTarget = new ColorImageTarget(Images.BlueStar);
		Target redTarget = new ColorImageTarget(Images.RedStar);
		Target greenTarget = new ColorImageTarget(Images.GreenStar);

		List<ScreenRegion> blueTargetRegions = s.findAll(blueTarget);
		List<ScreenRegion> greenTargetRegions = s.findAll(greenTarget);
		List<ScreenRegion> redTargetRegions = s.findAll(redTarget);
		
		for (ScreenRegion r : blueTargetRegions){
			painter.box(r, 3000);
			painter.label(r, "blue", 3000);
		}

		for (ScreenRegion r : redTargetRegions){
			painter.box(r, 3000);
			painter.label(r, "red", 3000);
		}

		for (ScreenRegion r : greenTargetRegions){
			painter.box(r, 3000);
			painter.label(r, "green", 3000);
		}


		// Finding color bullets
		ScreenRegion greenBullet = s.wait(new ColorImageTarget(Images.GreenBullet),10000);
		ScreenRegion redBullet = s.find(new ColorImageTarget(Images.RedBullet));
		ScreenRegion silverBullet = s.find(new ColorImageTarget(Images.SilverBullet));
		
		painter.box(greenBullet, 3000);
		painter.label(greenBullet, "g", 3000);
		
		painter.box(redBullet, 3000);
		painter.label(redBullet, "r", 3000);
		
		painter.box(silverBullet, 3000);
		painter.label(silverBullet, "s", 3000);

	}
}