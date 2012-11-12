package org.sikuli.api.robot.desktop;

import org.sikuli.api.ScreenLocation;
import org.sikuli.api.robot.Mouse;

public class DesktopMouse implements Mouse {

	private AWTMouse getAWTMouse(ScreenLocation screenLoc){
		 return AWTDesktop.getMouse(screenLoc);
	}
	
	private AWTMouse getCurrentAWTMouse(){
		 return AWTDesktop.getCurrentMouse();
	}
	
	public void drag(ScreenLocation screenLoc) {
		getAWTMouse(screenLoc).drag(screenLoc);
	}
		
	public void drop(ScreenLocation screenLoc) {
		getAWTMouse(screenLoc).drop(screenLoc);  
	}
	
	public void rightClick(ScreenLocation screenLoc) {
		getAWTMouse(screenLoc).rightClick(screenLoc);
	}
	
	public void doubleClick(ScreenLocation screenLoc) {
		getAWTMouse(screenLoc).doubleClick(screenLoc);
	}
	
	public void click(ScreenLocation screenLoc) {		
		getAWTMouse(screenLoc).click(screenLoc);
	}
	
	public void wheel(int direction, int steps){
		getCurrentAWTMouse().wheel(direction, steps);
	}

	public void mouseDown(int buttons) {
		getCurrentAWTMouse().mouseDown(buttons);
	}

	public void mouseUp() {
		getCurrentAWTMouse().mouseUp(0);
	}

	public void mouseUp(int buttons) {
		getCurrentAWTMouse().mouseUp(buttons);
	}
  
  public ScreenLocation getLocation(){
  	return AWTDesktop.getCurrentMouseScreenLocation();
  }

}