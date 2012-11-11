package org.sikuli.api.examples;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.visual.ScreenPainter;

public class CaptureImageExample {
	
	static Mouse mouse = new Mouse();
	static Keyboard keyboard = new Keyboard();
	static ScreenPainter painter = new ScreenPainter();

	public static void main(String[] args) throws IOException {
		DesktopScreenRegion smallRegion = new DesktopScreenRegion(500,500,400,200);
		painter.box(smallRegion, 1000);
		
		BufferedImage capturedImage = smallRegion.capture();
		ImageIO.write(capturedImage, "png", new File("SavedCaptuedImage.png"));
	}
}