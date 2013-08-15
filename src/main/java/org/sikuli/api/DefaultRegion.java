package org.sikuli.api;

import java.awt.Rectangle;
/**
 * The DefaultRegion class is used to describe a region of the screen.
 *
 */
public class DefaultRegion implements Region {
	
	private int x;
	private int y;
	private int width;
	private int height;
	/**
	 * Constructs a new screen region whose upper-left corner is specified as (x,y)  
	 * and whose width and height are specified by the arguments of the same name.
	 * 
	 * @param x the specified X coordinate
	 * @param y the specified Y coordinate
	 * @param width the specified width of the screen region.
	 * @param height the specified height of the screen region.
	 */
	public DefaultRegion(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	/**
	 * Constructs a new screen region, initialized to match the bounds of the specified Rectangle.
	 * 
	 * @param bounds the Rectangle from which to create the initial values of the newly constructed screen region.
	 */
	public DefaultRegion(Rectangle bounds) {
		setBounds(bounds);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}

	@Override
	public void setBounds(Rectangle newBounds) {		
		this.x = newBounds.x;
		this.y = newBounds.y;
		this.width = newBounds.width;
		this.height = newBounds.height;
	}

}
