package org.sikuli.api;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
/**
 * The Screen interface provides definitions for the objects that represent a Screen.
 *
 */
public interface Screen {
	
	/**
	 * Creates and returns an image containing a screen capture of the specified arguments of the same name.
	 * 
	 * x, y, width, height should be within the bounds.
	 * @param x the X location of the screen coordinate.
	 * @param y the Y location of the screen coordinate.
	 * @param width The width of the region to be captured.
	 * @param height  The height of the region to be captured.
	 * @return The captured image.
	 */
	public BufferedImage getScreenshot(int x, int y, int width, int height);
	
	/**
	 * Gets the size of this Screen, represented by the returned Dimension.
	 * 
	 * @return a Dimension, representing the size of this Screen.
	 */
	public Dimension getSize();
}
