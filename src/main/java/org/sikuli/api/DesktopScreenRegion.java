package org.sikuli.api;

import org.sikuli.api.robot.desktop.DesktopScreen;
/**
 * A DesktopScreenRegion object is used to describe a region of any DesktopScreen.
 *
 */
public class DesktopScreenRegion extends DefaultScreenRegion implements ScreenRegion {

	/**
	 * Creates a ScreenRegion in full screen on the default screen (i.e., screen 0)
	 */
	public DesktopScreenRegion(){
		super(new DesktopScreen(0));
	}
	
	/**
	 * Creates a ScreenRegion in full screen on a screen specified by an id. The specified screen id refers to 
	 * the order of the connected screens. For example, in a dual monitor setup, a screen id of 0 refers to the main 
	 * display and 1 refers to the secondary display.
	 * 
	 * @param id  the screen id.
	 */
	public DesktopScreenRegion(int id){
		super(new DesktopScreen(id));
	}

	/**
	 * Create a screen region based on X, Y, width and height.
	 * The related screen will be determined automatically based on the
	 * center of the rectangle and default to screen 0 in case the center is
	 * outside of all available screens.
	 * 
	 * @param x The X coordinate of the upper-left corner of the rectangular screen region.
	 * @param y The Y coordinate of the upper-left corner of the rectangular screen region.
	 * @param width The width of the rectangular screen region.
	 * @param height The height of the rectangular screen region.
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
	
	/**
	 * Create a screen region based on the specified screen id and region values.
	 * 
	 * @param id the screen id.
	 * @param x The X coordinate of the upper-left corner of the rectangular screen region.
	 * @param y The Y coordinate of the upper-left corner of the rectangular screen region.
	 * @param width The width of the rectangular screen region.
	 * @param height The height of the rectangular screen region
	 */
	public DesktopScreenRegion(int id, int x, int y, int width, int height) {
		super(new DesktopScreen(id));
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
