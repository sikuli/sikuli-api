package org.sikuli.api.examples.basic;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.examples.images.Images;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.robot.desktop.DesktopScreen;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.ScreenRegionCanvas;

public class MultiScreenExample {

	public static void main(String[] args) throws IOException  {
		Mouse mouse = new DesktopMouse();
		
		// iterate through each screen, the mouse cursor will traverse along the edges
		// of a rectangle centered on each screen clockwise, and then clicks on the dog image
		int numOfScreens = DesktopScreen.getNumberScreens();
		for (int screenId = 0; screenId < numOfScreens; screenId++){
			
			ScreenRegion screenRegion = new DesktopScreenRegion(screenId);
			ScreenRegion innerRegion = Relative.to(screenRegion).shorter(100).narrower(100).getScreenRegion();

			// create a canvas to draw visualization on the screen
			Canvas c = new ScreenRegionCanvas(screenRegion);
			c.addBox(innerRegion);
			c.addLabel(innerRegion.getCenter(), "Screen " + screenId).withFontSize(30);
			c.addImage(Relative.to(innerRegion).center().above(200).getScreenLocation(), ImageIO.read(Images.Dog));
			c.show();
			
			// hover the mouse cursor to each corner of the inner circle 
			mouse.hover(Relative.to(innerRegion).topLeft().getScreenLocation());
			mouse.hover(Relative.to(innerRegion).topRight().getScreenLocation());
			mouse.hover(Relative.to(innerRegion).bottomRight().getScreenLocation());
			mouse.hover(Relative.to(innerRegion).bottomLeft().getScreenLocation());
			
			// find the dog and click on it
			ScreenRegion dog = innerRegion.find(new ImageTarget(Images.Dog));
			mouse.click(dog.getCenter());
			
			c.hide();
		}
		
	
	}
	
}
