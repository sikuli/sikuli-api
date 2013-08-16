/*
 * Copyright 2010-2011, Sikuli.org
 * Released under the MIT License.
 *
 */
package org.sikuli.api;
/**
 * The ScreenLocation interface provides definitions for the objects that represent a location 
 * on any screen coordinate.
 *
 */
public interface ScreenLocation extends Location {
	/**
	 * Returns the screen associated with this screen location.
	 * 
	 * @return  the screen of this screen location.
	 */
	public Screen getScreen();	
	/**
	 * Sets the screen associated with this screen location.
	 * 
	 * @param screen the specified screen of this screen location.
	 */
	public void setScreen(Screen screen);
	/**
	 * Returns a new ScreenLocation relative to the specified offsets.
	 * 
	 * @param xoffset the horizontal offset.
	 * @param yoffset the vertical offset.
	 * @return a new ScreenLocation.
	 */
	public ScreenLocation getRelativeScreenLocation(int xoffset, int yoffset);	
}

