package org.sikuli.api;

import org.sikuli.api.robot.desktop.DesktopScreen;

public class DesktopScreenRegion extends DefaultScreenRegion implements ScreenRegion {

	/**
	 * Creates a ScreenRegion in full screen on the default screen (i.e., screen 0)
	 */
	public DesktopScreenRegion(){
		super(new DesktopScreen(0));
	}
	
	/**
	 * Creates a ScreenRegion in full screen on a screen specified by id
	 */
	public DesktopScreenRegion(int id){
		super(new DesktopScreen(id));
	}

	/**
	 * Create a screen region based on X, Y, width and height.
	 * The related screen will be determined automatically based on the
	 * center of the rectangle and default to screen 0 in case the center is
	 * outside of all available screens.
	 */
	public DesktopScreenRegion(int x, int y, int width, int height) {
		super(new DesktopScreen(0));
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		DesktopScreen _screen= DesktopScreen.getScreenAtCoord(x+width/2,y+height/2);
		if (_screen!=null) {
			this.setScreen(_screen);
		}
	}
		
}
