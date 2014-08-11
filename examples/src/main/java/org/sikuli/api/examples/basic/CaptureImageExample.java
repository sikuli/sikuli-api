package org.sikuli.api.examples.basic;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.sikuli.api.API;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class CaptureImageExample {
	
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();

	public static void main(String[] args) throws IOException {
		ScreenRegion r = new DesktopScreenRegion(100,100,300,200);
		canvas.add().box().around(r);
		canvas.add().label("This area will be captured.").inside(r);
		canvas.display(3);
		
		API.pause(2000);
		
		BufferedImage capturedImage = r.capture();
		ImageIO.write(capturedImage, "png", new File("SavedCaptuedImage.png"));
		
		canvas.clear();
		canvas.add().image(capturedImage).at(500,100);
		canvas.add().label("This is the captured image").at(500,100);
		
		canvas.display(3);
		
	}
}