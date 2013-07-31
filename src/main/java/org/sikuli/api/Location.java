/*
 * Copyright 2010-2011, Sikuli.org
 * Released under the MIT License.
 *
 */
package org.sikuli.api;
/**
 * The Location interface provides definitions for the objects that represent a location 
 * on the screen coordinate.
 *
 */
public interface Location {
	/**
	 * Returns the X location of the screen coordinate.
	 * 
	 * @return the X location of the screen coordinate.
	 */
	public int getX();
	/**
	 * Sets the X location of the screen coordinate.
	 * 
	 * @param x the X location of the screen coordinate.
	 */
	public void setX(int x);
	/**
	 * the Y location of the screen coordinate.
	 * 
	 * @return the Y location of the screen coordinate.
	 */
	public int getY();
	/**
	 * Sets the Y location of the screen coordinate.
	 * 
	 * @param y the Y location of the screen coordinate.
	 */
	public void setY(int y);
}

