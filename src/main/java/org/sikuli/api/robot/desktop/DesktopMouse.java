package org.sikuli.api.robot.desktop;

import java.awt.event.InputEvent;

import org.sikuli.api.ScreenLocation;
import org.sikuli.api.robot.Mouse;

public class DesktopMouse implements Mouse {

	private AWTMouse getAWTMouse(ScreenLocation screenLoc){
		return AWTDesktop.getMouse(screenLoc);
	}

	private AWTMouse getCurrentAWTMouse(){
		return AWTDesktop.getCurrentMouse();
	}

	@Override
	public void hover(ScreenLocation dest){
		AWTMouse srcMouse = getCurrentAWTMouse();
		AWTMouse destMouse = getAWTMouse(dest);		
		
		if (destMouse != srcMouse){
			// if the move destination is on a different screen
			// move the cursor immediately to the destination
			// because it is difficult to animate a smooth movement
			// across two screens
			destMouse.move(dest);
		} else {
			// if the move is within the same screen
			// move the cursor smoothly to the destination
			ScreenLocation src = getLocation();
			srcMouse.smoothMove(src, dest, 500);
		}		
	}
	
	@Override
	public void move(ScreenLocation dest){
		 getAWTMouse(dest).move(dest);
	}

	@Override
	public void drag(ScreenLocation screenLoc) {
		hover(screenLoc);
		getAWTMouse(screenLoc).drag();
	}

	@Override
	public void drop(ScreenLocation screenLoc) {
		hover(screenLoc);
		getAWTMouse(screenLoc).drop();  
	}

	@Override
	public void rightClick(ScreenLocation screenLoc) {
		hover(screenLoc);
		getAWTMouse(screenLoc).rightClick();
	}

	@Override
	public void doubleClick(ScreenLocation screenLoc) {
		hover(screenLoc);
		getAWTMouse(screenLoc).doubleClick();
	}

	@Override
	public void click(ScreenLocation screenLoc) {	
		hover(screenLoc);
		getAWTMouse(screenLoc).click();
	}

	@Override
	public void press(){
		getCurrentAWTMouse().mouseDown(InputEvent.BUTTON1_MASK);
	}

	@Override
	public void rightPress(){
		getCurrentAWTMouse().mouseDown(InputEvent.BUTTON3_MASK);
	}

	@Override
	public void release(){
		getCurrentAWTMouse().mouseUp(InputEvent.BUTTON1_MASK);
	}

	@Override
	public void rightRelease(){
		getCurrentAWTMouse().mouseUp(InputEvent.BUTTON3_MASK);
	}		

	@Override
	public void wheel(int direction, int steps){
		getCurrentAWTMouse().wheel(direction, steps);
	}

	@Override
	public void mouseDown(int buttons) {
		getCurrentAWTMouse().mouseDown(buttons);
	}

	@Override
	public void mouseUp() {
		getCurrentAWTMouse().mouseUp(0);
	}

	@Override
	public void mouseUp(int buttons) {
		getCurrentAWTMouse().mouseUp(buttons);
	}

	@Override
	public ScreenLocation getLocation(){
		return AWTDesktop.getCurrentMouseScreenLocation();
	}

}