package org.sikuli.api.robot;

import org.sikuli.api.ScreenLocation;

public interface Mouse {
	
	public void drag(ScreenLocation screenLoc);
	public void drop(ScreenLocation screenLoc);
	
	public void click(ScreenLocation screenLoc);
	public void rightClick(ScreenLocation screenLoc);	
	public void doubleClick(ScreenLocation screenLoc);
	
	public void hover(ScreenLocation screenLoc);
	
	public void press();
	public void rightPress();
	
	public void release();
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
    public ScreenLocation getLocation();

}
