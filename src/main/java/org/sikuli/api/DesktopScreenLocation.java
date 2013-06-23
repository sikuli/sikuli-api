package org.sikuli.api;

import org.sikuli.api.robot.desktop.DesktopScreen;

public class DesktopScreenLocation extends DefaultScreenLocation {

	/**
	 * Create a new screen location based on the coordinates. The screen
	 * relating to those coordinates will be determined automatically and
	 * defaults to screen 0 in case those coordinates are outside of all
	 * screens.
	 */
	public DesktopScreenLocation(int x, int y) {
		super(DesktopScreen.getScreenAtCoord(x, y), x, y);
	}
}
