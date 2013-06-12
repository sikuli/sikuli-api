package org.sikuli.api.robot;

import org.sikuli.api.ScreenLocation;

public interface Mouse {
	
	public void drag(ScreenLocation screenLoc);
	public void drop(ScreenLocation screenLoc);

	/**
	 * Left-click on a given screen location
	 * 
	 * @param screenLoc the screen location to click on 
	 */	
	public void click(ScreenLocation screenLoc);

	/**
	 * Right-click on a given screen location
	 * 
	 * @param screenLoc the screen location to click on 
	 */	
	public void rightClick(ScreenLocation screenLoc);
	
	/**
	 * Double-click on a given screen location
	 * 
	 * @param screenLoc the screen location to click on 
	 */	
	public void doubleClick(ScreenLocation screenLoc);
	
	/**
	 * Hover the mouse cursor to a  screen location. The movement
	 * is seen smoothly by the user. 
	 * 
	 * @param screenLoc the screen location for the mouse cursor to hover to
	 */
	public void hover(ScreenLocation screenLoc);
	
	/**
	 * Move the mouse cursor to a destination screen location immediately. The
	 * user won't see any mouse movement. 
	 * 
	 * @param screenLoc the screen location for the mouse cursor to move to
	 */
	public void move(ScreenLocation screenLoc);

	/**
	 * Press the left button
	 */	
	public void press();

	/**
	 * Press the right button
	 */	
	public void rightPress();

	/**
	 * Release the left button
	 */	
	public void release();

	/**
	 * Release the right button
	 */	
	public void rightRelease();
	

	/**
	 * Move the wheel at the current position
	 *
	 * @param direction the direction applied
	 * @param steps the number of step
	 */
	public void wheel(int direction, int steps);
	public void mouseDown(int buttons);
	public void mouseUp();
	public void mouseUp(int buttons);
	
	/**
	 * Get the screen location the mouse cursor is currently at
	 */	
    public ScreenLocation getLocation();

}
