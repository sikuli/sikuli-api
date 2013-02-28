package org.sikuli.api.examples;

import java.net.URL;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class RelativeExample {
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();
	
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
		
		canvas.addBox(Relative.to(r).right(100).getScreenRegion());
		canvas.addLabel(Relative.to(r).right(100).center().getScreenLocation(), "right");

		canvas.addBox(Relative.to(r).left(100).getScreenRegion());
		canvas.addLabel(Relative.to(r).left(100).center().getScreenLocation(), "left");

		canvas.addBox(Relative.to(r).above(100).getScreenRegion());
		canvas.addLabel(Relative.to(r).above(100).center().getScreenLocation(), "above");

		canvas.addBox(Relative.to(r).below(100).getScreenRegion());
		canvas.addLabel(Relative.to(r).below(100).center().getScreenLocation(), "below");
		
		canvas.display(3);

		mouse.click(Relative.to(r).center().getScreenLocation());
		mouse.click(Relative.to(r).topLeft().getScreenLocation());
		mouse.click(Relative.to(r).topRight().getScreenLocation());
		mouse.click(Relative.to(r).bottomRight().getScreenLocation());
		mouse.click(Relative.to(r).bottomLeft().getScreenLocation());
	

	}
}
