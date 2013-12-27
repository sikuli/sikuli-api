package org.sikuli.api.visual.examples;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.sikuli.api.examples.images.Images;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class ImageExample {

	public static void main(String[] args) throws IOException {		
		Canvas canvas = new DesktopCanvas();		

		canvas.add().image(ImageIO.read(Images.Dog)).at(100,100);
				
		canvas.display(3);
	}
}
