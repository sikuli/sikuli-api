package org.sikuli.api.examples;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.sikuli.api.DesktopScreenRegion;
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
		DesktopScreenRegion smallRegion = new DesktopScreenRegion(500,500,400,200);
		canvas.addBox(smallRegion).display(1);
		
		BufferedImage capturedImage = smallRegion.capture();
		ImageIO.write(capturedImage, "png", new File("SavedCaptuedImage.png"));
	}
}