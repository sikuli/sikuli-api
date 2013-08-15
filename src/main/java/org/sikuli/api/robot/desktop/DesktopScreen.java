package org.sikuli.api.robot.desktop;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.sikuli.api.Screen;

import com.google.common.base.Objects;

public class DesktopScreen implements Screen {
	final private int screenId;
	
	public DesktopScreen(int screenId){
		// TODO: throws an exception on invalid screen Id
		this.screenId = screenId;
	}
	
	@Override
	public BufferedImage getScreenshot(int x, int y, int width, int height) {					
		BufferedImage image = AWTDesktop.captureScreenshot(getId(), x, y, width, height);

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
		return AWTDesktop.getScreenBounds(getId()).getSize();
	}

	public int getId() {
		return getScreenId();
	}
	
	public Rectangle getBounds(){
		return AWTDesktop.getScreenBounds(getId());
	}

	
	/**
	 *
	 * @author ente
	 */
	/*
	 * determines the ScreenID related to some coordinates X and Y
	 * returns null if coordinates reside outside of all available screens
	 */
	public static DesktopScreen getScreenAtCoord(int x, int y) {
		DesktopScreen _screen=null;
		for (Integer i=0; i< AWTDesktop.getNumberScreens(); i++) {
			if (AWTDesktop.getScreenBounds(i).contains(x, y)) {
				_screen=new DesktopScreen(i);
			}
		}
		return _screen;
	}
	
	public String toString(){
		return Objects.toStringHelper(this).add("id",getScreenId()).toString();
	}
	
	/**
	 * Get the number of screens available on the desktop
	 * 
	 * @return
	 */
	public static int getNumberScreens() {
        return AWTDesktop.getNumberScreens();
}

	public int getScreenId() {
		return screenId;
	}


}