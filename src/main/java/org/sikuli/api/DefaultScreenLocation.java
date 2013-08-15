package org.sikuli.api;

import com.google.common.base.Objects;

public class DefaultScreenLocation implements ScreenLocation {

	private int x = 0;
	private int y = 0;
	private Screen screen;


	/**
	 * Create a new screen location on a given screen (screenRef)
	 * at the coordinate specified by x and y.
	 */
	public DefaultScreenLocation(Screen screenRef, int x, int y){
		setX(x);
		setY(y);
		screen = screenRef;
	}

	public DefaultScreenLocation(ScreenLocation loc){
		setX(loc.getX());
		setY(loc.getY());
		screen = loc.getScreen();
	}

	public ScreenLocation getRelativeScreenLocation(int xoffset, int yoffset){
		ScreenLocation loc =  new DefaultScreenLocation(screen, getX() + xoffset, getY() + yoffset);
		loc.setScreen(screen);
		return loc;
	}

	public String toString(){
		return Objects.toStringHelper(this).add("x",x).add("y",y)
				.add("screen",screen).toString();
	}

	@Override
	public Screen getScreen() {
		return screen;
	}

	@Override
	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}
		
}
