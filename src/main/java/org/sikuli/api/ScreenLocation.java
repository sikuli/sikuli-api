/*
 * Copyright 2010-2011, Sikuli.org
 * Released under the MIT License.
 *
 */
package org.sikuli.api;

import org.sikuli.api.robot.DesktopScreen;


public class ScreenLocation {

	public int x = 0;
	public int y = 0;
	private Screen screen;

	public ScreenLocation(int _x, int _y){
		x = _x;
		y = _y;
		screen = new DesktopScreen(0);
	}
	
	public ScreenLocation(int _x, int _y, Screen _screen){
		x = _x;
		y = _y;
		screen = _screen;
	}

	public ScreenLocation(ScreenLocation loc){
		x = loc.x;
		y = loc.y;
		screen = loc.screen;
	}

	public ScreenLocation negative(){
		ScreenLocation loc = new ScreenLocation(-x, -y);
		loc.screen = screen;
		return loc;
	}

	public ScreenLocation offset(int dx, int dy){
		ScreenLocation loc = new ScreenLocation(x+dx, y+dy);
		loc.screen = screen;
		return loc;		
	}

	public ScreenLocation getLeft(int dx){
		ScreenLocation loc =  new ScreenLocation(x-dx, y);
		loc.screen = screen;
		return loc;		
	}

	public ScreenLocation getRight(int dx){
		ScreenLocation loc =  new ScreenLocation(x+dx, y);
		loc.screen = screen;
		return loc;		
	}
	
	public ScreenLocation getRelativeScreenLocation(int xoffset, int yoffset){
		ScreenLocation loc =  new ScreenLocation(x + xoffset, y + yoffset);
		loc.screen = screen;
		return loc;
	}

	public ScreenLocation getAbove(int dy){
		ScreenLocation loc =  new ScreenLocation(x, y-dy);
		loc.screen = screen;
		return loc;				
	}

	public ScreenLocation getBelow(int dy){
		ScreenLocation loc =  new ScreenLocation(x, y+dy);
		loc.screen = screen;
		return loc;		
	}

	public String toString(){
		return "(" + x + "," + y + ")";
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}
}

