/*
 * Copyright 2010-2011, Sikuli.org
 * Released under the MIT License.
 *
 */
package org.sikuli.api;

public interface ScreenLocation extends Location {
	public Screen getScreen();	
	public void setScreen(Screen screen);	
	public ScreenLocation getRelativeScreenLocation(int xoffset, int yoffset);	
}

