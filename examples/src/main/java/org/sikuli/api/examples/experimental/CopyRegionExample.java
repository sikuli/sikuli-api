package org.sikuli.api.examples.experimental;

import static org.sikuli.api.API.browse;
import java.net.MalformedURLException;
import java.net.URL;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class CopyRegionExample {
	public static void main(String[] args) throws MalformedURLException{
		// Open the main page of Google Code in the default web browser
		browse(new URL("http://code.google.com"));
		
		// Create a screen region object that corresponds to the default monitor in full screen
		ScreenRegion s = new DesktopScreenRegion();
		
		// Specify an image as the target to find on the screen
		URL imageURL = new URL("http://code.google.com/images/code_logo.gif");
		Target imageTarget = new ImageTarget(imageURL);
		
		// Wait for the target to become visible on the screen for at most 5 seconds
		ScreenRegion r = s.wait(imageTarget,5000);
		
		// Specify the region to be copied
		ScreenRegion copyRegion = Relative.to(r).right(310).below(20).above(10).getScreenRegion();
		
		// Display canvas on the region to be copied for 3 seconds
		Canvas canvas = new DesktopCanvas();
		canvas.addBox(copyRegion);
		canvas.addLabel(copyRegion, "region to be copied.");
		canvas.display(3);
		
		// copy the content of the region
		Keyboard keyboard = new DesktopKeyboard();
		keyboard.copyRegion(copyRegion);
	}
}
