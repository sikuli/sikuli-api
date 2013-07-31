package org.sikuli.api;

import java.awt.Rectangle;

import com.google.common.base.Objects;
/**
 * AbstractScreenRegion is the abstract base class for all screen region contexts.
 * 
 */
abstract public class AbstractScreenRegion implements ScreenRegion {
	private int x;
	private int y;
	private int width;
	private int height;
	private double score;

	private Screen screen;	
	/**
	 * Constructs a new AbstractScreenRegion object whose Screen is the specified Screen object, and   
	 * upper-left corner is at (0, 0) in the coordinate space.
	 * Since AbstractScreenRegion is an abstract class, applications can't call this constructor directly.
	 * Screen regions are obtained from other screen regions contexts.
	 * 
	 * @param screen The Screen to create a region from.
	 */
	public AbstractScreenRegion(Screen screen) {
		setX(0);
		setY(0);		
		setWidth(screen.getSize().width);
		setHeight(screen.getSize().height);
		setScreen(screen);
	}
	/**
	 * Constructs a new AbstractScreenRegion object.
	 * Since AbstractScreenRegion is an abstract class, applications can't call this constructor directly.
	 * 
	 * @param screen The Screen to create a region from.
	 * @param x The X coordinate of the upper-left corner of the rectangular screen region.
	 * @param y The Y coordinate of the upper-left corner of the rectangular screen region.
	 * @param width The width of the rectangular screen region.
	 * @param height The height of the rectangular screen region.
	 */
	public AbstractScreenRegion(Screen screen, int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setScreen(screen);
	}
	
	@Override
	public Rectangle getBounds(){
		return new Rectangle(getX(),getY(),getWidth(),getHeight());
	}
	
	@Override
	public void setBounds(Rectangle newBounds){
		setX(newBounds.x);
		setY(newBounds.y);
		setWidth(newBounds.width);
		setHeight(newBounds.height);		
	}

	@Override
	public ScreenRegion getRelativeScreenRegion(int xoffset, int yoffset, int width, int height){
		return new DefaultScreenRegion(this, xoffset, yoffset, width, height);
	}
	
	@Override
	public ScreenLocation getRelativeScreenLocation(int xoffset, int yoffset){
		return new DefaultScreenLocation(screen, getX() + xoffset, getY() + yoffset);
	}
	
	@Override
	public ScreenLocation getCenter() {
		return getRelativeScreenLocation(getWidth()/2, getHeight()/2);
	}

	
	@Override
	public String toString(){
		return Objects.toStringHelper(this).add("screen", screen).				
				add("x",x).add("y",y).add("width",width).add("height",height).add("score",score).toString();			
	}
	
	@Override	
	public Screen getScreen() {
		return screen;
	}
	
	@Override
	public double getScore() {
		return score;
	}

	@Override
	public void setScore(double score) {
		this.score = score;
	}


	@Override
	public void setScreen(Screen screen) {
		this.screen = screen;
	}
	/**
	 * Returns the X coordinate of the upper-left corner of the rectangular screen region.
	 * 
	 * @return the X coordinate of the upper-left corner of the rectangular screen region.
	 */
	public int getX() {
		return x;
	}
	/**
	 * Sets the X coordinate of the upper-left corner of the rectangular screen region.
	 * 
	 * @param x the X coordinate of the upper-left corner of the rectangular screen region.
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Returns the Y coordinate of the upper-left corner of the rectangular screen region.
	 * 
	 * @return the Y coordinate of the upper-left corner of the rectangular screen region.
	 */
	public int getY() {
		return y;
	}
	/**
	 * Sets the Y coordinate of the upper-left corner of the rectangular screen region.
	 * 
	 * @param y the Y coordinate of the upper-left corner of the rectangular screen region.
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Returns the width of the rectangular screen region.
	 * 
	 * @return the width of the rectangular screen region.
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Sets the width of the rectangular screen region.
	 * 
	 * @param width the width of the rectangular screen region.
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * Returns the height of the rectangular screen region.
	 *  
	 * @return the height of the rectangular screen region.
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Sets the height of the rectangular screen region.
	 *  
	 * @param height the height of the rectangular screen region.
	 */
	public void setHeight(int height) {
		this.height = height;
	}


}
