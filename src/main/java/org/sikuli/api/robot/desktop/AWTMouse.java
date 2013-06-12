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
	
	// move to (x, y) in the global screen coordinate
	private void _move(int x, int y){
		Point o = robot.getOrigin();
		int sx = x - o.x;
		int sy = y - o.y;
		robot.mouseMove(sx,sy);
		robot.waitForIdle();
	}
	
	public void smoothMove(ScreenLocation src, ScreenLocation dest, int ms){
		if(ms == 0){
			_move(dest.getX(), dest.getY());
			return;
		}

		Animator aniX = new TimeBasedAnimator(
				new OutQuarticEase((float)src.getX(), (float)dest.getX(), ms));
		Animator aniY = new TimeBasedAnimator(
				new OutQuarticEase((float)src.getY(), (float)dest.getY(), ms));
		while(aniX.running()){
			float x = aniX.step();
			float y = aniY.step();
			_move((int)x, (int)y);
			robot.delay(50);
		}		
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

	public void move(ScreenLocation dest){
		_move(dest.getX(), dest.getY());
	}

	public void drag(){
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.waitForIdle();
	}

	public void drop() {
		int delay = 1;
		robot.delay((int)(delay*1000));
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.waitForIdle();        
	}

	public void rightClick() {		
		_click(InputEvent.BUTTON3_MASK, 0, false);
	}

	public void doubleClick() {		
		_click(InputEvent.BUTTON1_MASK, 0, true);
	}

	public void click() {		
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