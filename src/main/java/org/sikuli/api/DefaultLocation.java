package org.sikuli.api;

import java.awt.Image;
/**
 * The DefaultLocation class is used to describe a location on the screen coordinate.
 *
 */
public class DefaultLocation implements Location {

	private int x;
	private int y;
	/**
	 * Constructs a default location at the given screen coordinates.
	 * 
	 * @param x the X location of the screen coordinate.
	 * @param y the Y location of the screen coordinate.
	 */
	public DefaultLocation(int x, int y){
		this.x = x;
		this.y = y;
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
