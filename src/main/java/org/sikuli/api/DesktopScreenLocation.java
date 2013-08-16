package org.sikuli.api;

import org.sikuli.api.robot.desktop.DesktopScreen;
/**
 * This DesktopScreenLocation class represents a screen location and its corresponding screen.
 *
 */
public class DesktopScreenLocation extends DefaultScreenLocation {

	/**
	 * Constructs a new screen location based on the coordinates. The screen
	 * relating to those coordinates will be determined automatically and
	 * defaults to screen 0 in case those coordinates are outside of all
	 * screens.
	 * @param x the X coordinate of the screen location.
	 * @param y the Y coordinate of the screen location.
	 */
	public DesktopScreenLocation(int x, int y) {
		super(DesktopScreen.getScreenAtCoord(x, y), x, y);
	}
}
