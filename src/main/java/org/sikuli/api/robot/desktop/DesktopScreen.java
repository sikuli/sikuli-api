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
/**
 * The DesktopScreen class defines methods for retrieving properties of the connected screen(s).
 *
 */
public class DesktopScreen implements Screen {
	final private int screenId;
	/**
	 * Constructs a new DesktopScreen whose screen id is specified by the screenId argument.
	 * The specified screen id represents the order of the connected screen. For example, 
	 * in a dual monitor setup, a screenId of 0 refers to the main display and 1 to the secondary display. 
	 * @param screenId the screen id.  
	 */
	public DesktopScreen(int screenId){
		// TODO: throws an exception on invalid screen Id
		this.screenId = screenId;
	}
	/**
	 * @throws IOException If an input or output exception occurred.
	 */
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
	/**
	 * Returns  screen id of this DesktopScreen.
	 * 
	 * @return the screen id.
	 */
	public int getId() {
		return screenId;
	}
	/**
	 * Returns the bounding Rectangle of this DesktopScreen.
	 * 
	 * @return a Rectangle object that represents the bounding of this DesktopScreen.
	 */
	public Rectangle getBounds(){
		return AWTDesktop.getScreenBounds(getId());
	}

	
	// @author ente
	/**
	 * Determines and returns the DesktopScreen of the specified (x,y) coordinates.
	 * 
	 * @param x the X coordinate. 
	 * @param y the Y coordinate. 
	 * @return returns DesktopScreen  of the specified coordinates, or null if the coordinates 
	 * reside outside of all available screens.
	 * 
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
	/**
	 * Returns a String representation of this DesktopScreen and its values.
	 * 
	 * @return a String representing this DesktopScreen object's screen id.
	 */
	public String toString(){
		return Objects.toStringHelper(getClass().getName()).add("id",screenId).toString();
	}
	
	/**
	 * Gets the number of screens available on the desktop.
	 * 
	 * @return the number of connected screens.
	 */
	public static int getNumberScreens() {
        return AWTDesktop.getNumberScreens();
}


}