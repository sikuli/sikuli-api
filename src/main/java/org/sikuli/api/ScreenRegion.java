package org.sikuli.api;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import org.sikuli.api.event.StateChangeListener;
import org.sikuli.api.event.TargetEventListener;

public interface ScreenRegion {
			
	public Screen getScreen();
	public void setScreen(Screen screen);

	
	public Rectangle getBounds();
	public void setBounds(Rectangle newBounds);

	public ScreenRegion getRelativeScreenRegion(int xoffset, int yoffset, int width, int height);
	public ScreenLocation getRelativeScreenLocation(int xoffset, int yoffset);
	
	/**
	 * Gets the upper-left corner of this screen region
	 * 
	 * @return a Location object corresponding to the upper-left corner of the screen region
	 */
	public abstract ScreenLocation getUpperLeftCorner();
	
	/**
	 * Gets the lower-left corner of this screen region
	 * 
	 * @return a Location object corresponding to the lower-left corner of the screen region
	 */
	public abstract ScreenLocation getLowerLeftCorner();
	
	/**
	 * Gets the upper-right corner of this screen region
	 * 
	 * @return a Location object corresponding to the upper-right corner of the screen region
	 */
	public abstract ScreenLocation getUpperRightCorner();
	
	/**
	 * Gets the lower-right corner of this screen region
	 * 
	 * @return a Location object corresponding to the lower-right corner of the screen region
	 */
	public abstract ScreenLocation getLowerRightCorner();

	/**
	 * Gets the center of this screen region 
	 * 
	 * @return a Location object corresponding to the center of the screen region
	 */
	public abstract ScreenLocation getCenter();
	
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
	 * Captures and returns a screenshot of this screen region
	 * 
	 * @return	a BufferedImage containing the screenshot. The type of the image is TYPE_3BYTE_BGR
	 */
	public abstract BufferedImage capture();

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
	public abstract void addStateChangeEventListener(StateChangeListener listener);
	
	public abstract Map<Target, Object> getStates();

	public abstract void addROI(int x, int y, int width, int height);

	public abstract List<Rectangle> getROIs();

	//public abstract boolean contains(ScreenRegion r);

	
	
	public BufferedImage getLastCapturedImage();

}