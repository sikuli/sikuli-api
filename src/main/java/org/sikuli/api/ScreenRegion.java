package org.sikuli.api;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public interface ScreenRegion {

	public abstract int getX();

	public abstract void setX(int x);

	public abstract int getY();

	public abstract void setY(int y);

	public abstract int getWidth();

	public abstract void setWidth(int width);

	public abstract int getHeight();

	public abstract void setHeight(int height);

	/**
	 * Gets the score of this ScreenRegion, which is set if this screen region
	 * was returned as the result of a find command. The score should be between
	 * 0 and 1 where 1 is the best.
	 * 
	 * @return the score
	 */
	public abstract double getScore();

	public abstract void setScore(double score);

	/**
	 * Defines a new state of this screen region as represented by the presence
	 * of the given target 
	 * 
	 * @param target	the target whose presence indicates this state
	 * @param state	the state, which can be any object
	 */
	public abstract void addState(Target target, Object state);

	/**
	 * Removes a particular state represented by the given target from this screen region
	 * 
	 * @param target the representative target of the state to remove
	 */
	public abstract void removeState(Target target);

	public abstract Object getState();

	/**
	 * Gets a new screen region of the given width to the right of this screen region. This new
	 * screen region would have the same y position and the same height.
	 * 
	 * @param w	the width of the new screen region
	 * @return	the new screen region
	 */
	public abstract ScreenRegion getRight(int w);

	/**
	 * Gets a new screen region of the given width to the left of this screen region. This new
	 * screen region would have the same y position and the same height.
	 * 
	 * @param w	the width of the new screen region
	 * @return	the new screen region
	 */
	public abstract ScreenRegion getLeft(int w);

	/**
	 * Gets a new screen region of the given height above this screen region. This new
	 * screen region would have the same x position and the same width.
	 * 
	 * @param h	the width of the new screen region
	 * @return	the new screen region
	 */
	public abstract ScreenRegion getAbove(int h);

	/**
	 * Gets a new screen region of the given height above this screen region. This new
	 * screen region would have the same x position and the same width.
	 * 
	 * @param h	the width of the new screen region
	 * @return	the new screen region
	 */
	public abstract ScreenRegion getBelow(int h);

	/**
	 * Finds all the instances of the target on the screen immediately
	 * 
	 * @param target	the target to find
	 * @return a list of ScreenRegions, each of which corresponds to a found target, or an empty list
	 * if no such target can be found
	 */
	public abstract List<ScreenRegion> findAll(Target target);

	/**
	 * Finds a target on the screen immediately
	 * 
	 * @param target	the target to find on the current screen
	 * @return	the screen region occupied by the found target or <code>null</code> if the 
	 * can not be found now
	 */
	public abstract ScreenRegion find(Target target);

	/**
	 * Blocks and waits until a target is found in this screen region within a given time period
	 * 
	 * @param target the target to wait
	 * @param mills the maximum time to wait in milliseconds 
	 * @return a ScreenRegion object representing the region occupied by the found target,
	 * or null if the target can not be found within the given time  
	 */
	public abstract ScreenRegion wait(final Target target, int mills);

	/**
	 * Grows this screen region above, left, below, and right 
	 * 
	 * @param above	the amount to grow above
	 * @param below	the amount to grow below
	 * @param left	the amount to grow left
	 * @param right	the amount to grow above
	 * @return	this ScreenRegion that has grown larger
	 */
	public abstract ScreenRegion grow(int above, int right, int below,
			int left);

	/**
	 * Captures and returns a screenshot of this screen region
	 * 
	 * @return	a BufferedImage containing the screenshot. The type of the image is TYPE_3BYTE_BGR
	 */
	public abstract BufferedImage capture();

	/**
	 * Gets the center of this screen region 
	 * 
	 * @return a Location object corresponding to the center of the screen region
	 */
	public abstract ScreenLocation getCenter();

	/**
	 * Gets the location of this screen region, which is the top-left corner of 
	 * this screen region
	 * 
	 * @return the screen location of this screen region
	 */
	public abstract ScreenLocation getLocation();

	/**
	 * Gets the top left corner of this screen region
	 * 
	 * @return	a Location object corresponding to the top left corner of the region
	 */
	public abstract ScreenLocation getTopLeft();

	/**
	 * Gets the bottom right corner of this screen region
	 * 
	 * @return a Location object corresponding to the bottom right corner of the region
	 */
	public abstract ScreenLocation getBottomRight();

	/**
	 * Adds a listener for a given target
	 * 
	 * @param target	the target to listener its events for
	 * @param listener	the listener to handle the events associated with the target
	 */
	public abstract void addTargetEventListener(Target target,
			TargetEventListener listener);

	/**
	 * Removes a particular listener for a particular target
	 * 
	 * @param target	the target from which the given listener should be removed
	 * @param listener	the listener to remove from the given target
	 */
	public abstract void removeTargetEventListener(Target target,
			TargetEventListener listener);

	public abstract ScreenRegion snapshot();

	/**
	 * Adds a listener to handle the state changes within this screen region
	 * 
	 * @param listener	the listener to handle state changes in this screen region
	 */
	public abstract void addStateChangeEventListener(
			StateChangeListener listener);
	
	public abstract Map<Target, Object> getStates();


	public abstract void addROI(int x, int y, int width, int height);

	public abstract List<Rectangle> getROIs();

	public abstract boolean contains(ScreenRegion r);

	public abstract Screen getScreen();

	public abstract void setScreen(Screen screen);
	
	
	public BufferedImage getLastCapturedImage();

}