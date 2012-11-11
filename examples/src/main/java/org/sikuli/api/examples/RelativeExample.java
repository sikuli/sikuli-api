package org.sikuli.api.examples;

import java.net.URL;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.visual.ScreenPainter;

public class RelativeExample {
	static Mouse mouse = new Mouse();
	static Keyboard keyboard = new Keyboard();
	static ScreenPainter painter = new ScreenPainter();
	
	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.GoogleSearchPage);
			wait(20000);
			close();
		}
	};

	public static void main(String[] args) {
		simulator.start();		
		ScreenRegion s = new DesktopScreenRegion();			
		URL imageURL = Images.GoogleSearchButton;                
		Target imageTarget = new ImageTarget(imageURL);
		ScreenRegion r = s.find(imageTarget);		
		
		painter.box(Relative.to(r).right(100).getScreenRegion(), 3000);
		painter.label(Relative.to(r).right(100).center().getScreenLocation(), "right", 3000);

		painter.box(Relative.to(r).left(100).getScreenRegion(), 3000);
		painter.label(Relative.to(r).left(100).center().getScreenLocation(), "left", 3000);

		painter.box(Relative.to(r).above(100).getScreenRegion(), 3000);
		painter.label(Relative.to(r).above(100).center().getScreenLocation(), "above", 3000);

		painter.box(Relative.to(r).below(100).getScreenRegion(), 3000);
		painter.label(Relative.to(r).below(100).center().getScreenLocation(), "below", 3000);

		mouse.click(Relative.to(r).center().getScreenLocation());
		mouse.click(Relative.to(r).topLeft().getScreenLocation());
		mouse.click(Relative.to(r).topRight().getScreenLocation());
		mouse.click(Relative.to(r).bottomRight().getScreenLocation());
		mouse.click(Relative.to(r).bottomLeft().getScreenLocation());
	

	}
}
