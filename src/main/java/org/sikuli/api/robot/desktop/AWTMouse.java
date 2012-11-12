package org.sikuli.api.robot.desktop;

import java.awt.Point;
import java.awt.event.InputEvent;

import org.sikuli.api.APILogger;
import org.sikuli.api.ScreenLocation;

class AWTMouse {	
	AWTRobot robot;	
	static private int _hold_buttons = 0;
	
	AWTMouse(AWTRobot robot){
		this.robot = robot;
	}
	
	private void _moveTo(ScreenLocation screenLoc){				
		Point o = robot.getOrigin();
		// TODO: figure out why the offset is applied negatively 
		robot.smoothMove(new Point(-o.x+screenLoc.getX(), -o.y+screenLoc.getY()));
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