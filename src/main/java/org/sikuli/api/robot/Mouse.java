package org.sikuli.api.robot;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.sikuli.api.APILogger;
import org.sikuli.api.ScreenLocation;

public class Mouse {

	private DesktopMouse getDesktopMouse(ScreenLocation screenLoc){
		 return Desktop.getMouse(screenLoc);
	}
	
	private DesktopMouse getCurrentDesktopMouse(){
		 return Desktop.getCurrentMouse();
	}
	
	public void drag(ScreenLocation screenLoc) {
		getDesktopMouse(screenLoc).drag(screenLoc);
	}
		
	public void drop(ScreenLocation screenLoc) {
		getDesktopMouse(screenLoc).drop(screenLoc);  
	}
	
	public void rightClick(ScreenLocation screenLoc) {
		getDesktopMouse(screenLoc).rightClick(screenLoc);
	}
	
	public void doubleClick(ScreenLocation screenLoc) {
		getDesktopMouse(screenLoc).doubleClick(screenLoc);
	}
	
	public void click(ScreenLocation screenLoc) {		
		getDesktopMouse(screenLoc).click(screenLoc);
	}
	
	/**
	 * Move the wheel at the current position
	 *
	 * @param direction the direction applied
	 * @param steps the number of step
	 */
	public void wheel(int direction, int steps){
		getCurrentDesktopMouse().wheel(direction, steps);
	}

	public void mouseDown(int buttons) {
		getCurrentDesktopMouse().mouseDown(buttons);
	}

	public void mouseUp() {
		getCurrentDesktopMouse().mouseUp(0);
	}

	public void mouseUp(int buttons) {
		getCurrentDesktopMouse().mouseUp(buttons);
	}
    
    public ScreenLocation getLocation(){
    	return Desktop.getCurrentMouseScreenLocation();
    }

}


class DesktopMouse {	
	DesktopRobot robot;	
	static private int _hold_buttons = 0;
	
	DesktopMouse(DesktopRobot robot){
		this.robot = robot;
	}
	
	private void _moveTo(ScreenLocation screenLoc){				
		Point o = robot.getOrigin();
		// TODO: figure out why the offset is applied negatively 
		robot.smoothMove(new Point(-o.x+screenLoc.x, -o.y+screenLoc.y));
		robot.waitForIdle();
	}
	
	private void _click(int buttons, int modifiers, boolean dblClick) {		
		robot.pressModifiers(modifiers);
		
		robot.mousePress(buttons);
		robot.mouseRelease(buttons);
		if( dblClick ){
			robot.mousePress(buttons);
			robot.mouseRelease(buttons);
		}
		robot.releaseModifiers(modifiers);
		robot.waitForIdle();
	}	
	
	public void drag(ScreenLocation screenLoc){
		_moveTo(screenLoc);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.waitForIdle();
	}
		
	public void drop(ScreenLocation screenLoc) {
//        APILogger.getLogger().dropPerformed(screenLoc);
		_moveTo(screenLoc);
		int delay = 1;
        robot.delay((int)(delay*1000));
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.waitForIdle();        
	}
	
	public void rightClick(ScreenLocation screenLoc) {		
		APILogger.getLogger().rightClickPerformed(screenLoc);
		_moveTo(screenLoc);
		_click(InputEvent.BUTTON3_MASK, 0, false);
	}
	
	public void doubleClick(ScreenLocation screenLoc) {		
		APILogger.getLogger().doubleClickPerformed(screenLoc);
		_moveTo(screenLoc);
		_click(InputEvent.BUTTON1_MASK, 0, true);
	}
	
	public void click(ScreenLocation screenLoc) {		
		APILogger.getLogger().clickPerformed(screenLoc);
		_moveTo(screenLoc);
		_click(InputEvent.BUTTON1_MASK, 0, false);		
	}
	
	public void wheel(int direction, int steps){
		for(int i=0;i<steps;i++){
			robot.mouseWheel(direction);
			robot.delay(50);
		}
	}

	public void mouseDown(int buttons) {
		_hold_buttons = buttons;
		robot.mousePress(buttons);
		robot.waitForIdle();
	}

	public void mouseUp() {
		mouseUp(0);
	}

	public void mouseUp(int buttons) {
		if(buttons==0)
			robot.mouseRelease(_hold_buttons);
		else
			robot.mouseRelease(buttons);
		robot.waitForIdle();
	}
}