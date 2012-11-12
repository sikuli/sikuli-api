package org.sikuli.api;

import org.sikuli.api.robot.desktop.DesktopScreen;

public class DesktopScreenRegion extends DefaultScreenRegion implements ScreenRegion {

	/**
	 * Creates a ScreenRegion in full screen on the default screen
	 */
	public DesktopScreenRegion(){
		super(new DesktopScreen(0));
	}

	/**
	 * Creates a ScreenRegion of the given x, y, width, height on the default screen 
	 * 
	 * @param x	x 
	 * @param y	y
	 * @param width	width
	 * @param height	height
	 */	
	public DesktopScreenRegion(int x, int y, int width, int height) {
		super(new DesktopScreen(0), x, y, width, height);
	}

}
