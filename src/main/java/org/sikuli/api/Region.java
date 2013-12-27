package org.sikuli.api;

import java.awt.Rectangle;
/**
 * The Region interface provides definitions for the objects that represent a Region.
 */
public interface Region {
	/**
	 * Returns the bounding Rectangle of this Region.
	 * 
	 * @return a Rectangle object that represents the bounding of this Region.
	 */
	public Rectangle getBounds();
	/**
	 * Sets the bounding Rectangle of this Rectangle.
	 * 
	 * @param newBounds the specified bounding Rectangle.
	 */
	public void setBounds(Rectangle newBounds);
	
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();	
}