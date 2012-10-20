package org.sikuli.api.robot;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.sikuli.api.Screen;

public class DesktopScreen implements Screen {
	final private int screenId;
	
	public DesktopScreen(int screenId){
		// TODO: throws an exception on invliad screen Id
		this.screenId = screenId;
	}
	
	@Override
	public BufferedImage getScreenshot(int x, int y, int width, int height) {					
		BufferedImage image = Desktop.captureScreenshot(getId(), x, y, width, height);

		// Java Robot returns a buffered image of the type TYPE_INT_RGB
		// JavaCV IplImage has trouble handling TYPE_INT_RGB 
		// The workaround is to write it to a byte array and read it back to get
		// a buffered image of the type TYPE_3BYTE_RGB
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, "png", os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			return ImageIO.read(is);
		} catch (IOException e) {
		}	
		return null;
	}

	@Override
	public Dimension getSize() {
		return Desktop.getScreenBounds(getId()).getSize();
	}

	public int getId() {
		return screenId;
	}
	
	public Rectangle getBounds(){
		return Desktop.getScreenBounds(getId());
	}

}