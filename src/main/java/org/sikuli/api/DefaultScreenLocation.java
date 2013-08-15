package org.sikuli.api;

import com.google.common.base.Objects;
/**
 * This class provides manipulation methods that can be used for the default screen location.
 *
 */
public class DefaultScreenLocation implements ScreenLocation {

	private int x = 0;
	private int y = 0;
	private Screen screen;


	/**
	 * Constructs a new screen location whose screen is the specified screen (screenRef), and whose 
	 * (x,y) coordinates are specified by the arguments of the same name.
	 * 
	 * @param screenRef the specified screen or monitor. 
	 * @param x the X location of the screen coordinate.
	 * @param y the Y location of the screen coordinate.
	 */
	public DefaultScreenLocation(Screen screenRef, int x, int y){
		setX(x);
		setY(y);
		screen = screenRef;
	}
	/**
	 * Constructs a new screen location whose screen and (x,y) coordinates are specified by the ScreenLocation argument.
	 * 
	 * @param loc a ScreenLocation, specifying screen and (x,y) coordinates.
	 */
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
	/**
	 * Returns a String representation of this DefaultScreenLocation and its values.
	 * @return a String representing this DefaultScreenLocation object's coordinate and screen values.
	 */
	public String toString(){
		return Objects.toStringHelper(getClass().getName()).add("x",x).add("y",y)
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
