package org.sikuli.api.visual;

import java.io.IOException;
import javax.imageio.ImageIO;

import org.sikuli.api.examples.Images;

public class ImageExample {

	public static void main(String[] args) throws IOException {		
		Canvas canvas = new DesktopCanvas();		

		canvas.add().image(ImageIO.read(Images.Dog)).at(100,100);
				
		canvas.display(3);
	}
}
