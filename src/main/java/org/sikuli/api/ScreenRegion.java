package org.sikuli.api;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import org.sikuli.api.event.StateChangeListener;
import org.sikuli.api.event.TargetEventListener;
/**
 * The ScreenRegion interface provides definitions for the objects that represent regions of the screen.
 */
public interface ScreenRegion extends Region {
	/**
	 * Returns a Screen object of this ScreenRegion.
	 * 
	 * @return a Screen object that represents the {@link Screen} of this ScreenRegion.
	 */
	public Screen getScreen();
	/**
	 * Sets the Screen of this ScreenRegion to be the specified Screen.
	 * 
	 * @param screen the Screen for this ScreenRegion.
	 */
	public void setScreen(Screen screen);
	/**
	 * Returns a ScreenRegion relative to the specified region values.
	 * 
	 * @param xoffset Horizontal offset.
	 * @param yoffset Vertical offset.
	 * @param width the width of this ScreenRegion.
	 * @param height the height of this ScreenRegion.
	 * @return a ScreenRegion relative to the specified region values.
	 */
	public ScreenRegion getRelativeScreenRegion(int xoffset, int yoffset, int width, int height);
	/**
	 * Returns a ScreenRegion relative to the specified region values.
	 * 
	 * @param xoffset Horizontal offset.
	 * @param yoffset Vertical offset.
	 * @return
	 */
	public ScreenLocation getRelativeScreenLocation(int xoffset, int yoffset);
	
	/**
	 * Returns the upper-left corner of this screen region.
	 * 
	 * @return a Location object corresponding to the upper-left corner of the screen region.
	 */
	public abstract ScreenLocation getUpperLeftCorner();
	
	/**
	 * Returns the lower-left corner of this screen region.
	 * 
	 * @return a Location object corresponding to the lower-left corner of the screen region.
	 */
	public abstract ScreenLocation getLowerLeftCorner();
	
	/**
	 * Returns the upper-right corner of this screen region.
	 * 
	 * @return a Location object corresponding to the upper-right corner of the screen region.
	 */
	public abstract ScreenLocation getUpperRightCorner();
	
	/**
	 * Returns the lower-right corner of this screen region.
	 * 
	 * @return a Location object corresponding to the lower-right corner of the screen region.
	 */
	public abstract ScreenLocation getLowerRightCorner();

	/**
	 * Returns the center of this screen region.
	 * 
	 * @return a Location object corresponding to the center of the screen region.
	 */
	public abstract ScreenLocation getCenter();
	
	/**
	 * Returns the minimum matching score of this ScreenRegion, which is set if this screen region
	 * was returned as the result of a find command. The score should be between
	 * 0 and 1 where 1 is the best.
	 * 
	 * @return the minimum matching score value for a target to be considered a match.
	 */
	public abstract double getScore();
	/**
	 * Sets the minimum matching score value for a target to be considered a match.
	 * This score indicates how similar the match is to the target specified. It accepts a value between 0 and 1.
	 * A high score close to 1 means the match must be very similar to the target specified.
	 * A low score means a match only somehow similar is acceptable; image recognition is more fuzzy.
	 * 
	 * @param score the minimum matching score value for a target to be considered a match. 
	 * The value should be between 0 and 1.
	 */
	public abstract void setScore(double score);

	/**
	 * Defines a new state of this screen region as represented by the presence
	 * of the given target.
	 * 
	 * @param target the target whose presence indicates this state.
	 * @param state	the state, which can be any object.
	 */
	public abstract void addState(Target target, Object state);

	/**
	 * Removes a particular state represented by the given target from this screen region.
	 * 
	 * @param target the representative target of the state to remove.
	 */
	public abstract void removeState(Target target);
	/**
	 * Returns the state of this ScreenRegion.
	 * 
	 * @return the state of this ScreenRegion.
	 */
	public abstract Object getState();

	/**
	 * Finds all the instances of the target on the screen immediately.
	 * 
	 * @param target the target to find.
	 * @return a list of ScreenRegions, each of which corresponds to a found target, or an empty list.
	 * if no such target can be found.
	 */
	public abstract List<ScreenRegion> findAll(Target target);

	/**
	 * Finds a target on the screen immediately.
	 * 
	 * @param target the target to find on the current screen.
	 * @return	the screen region occupied by the found target or <code>null</code> if the 
	 * can not be found now.
	 */
	public abstract ScreenRegion find(Target target);

	/**
	 * Blocks and waits until a target is found in this screen region within a given time period.
	 * 
	 * @param target the target to wait.
	 * @param mills the maximum time to wait in milliseconds.
	 * @return a {@link ScreenRegion} object representing the region occupied by the found target,
	 * or {@code null} if the specified target can not be found within the given time.
	 */
	public abstract ScreenRegion wait(final Target target, int mills);

	/**
	 * Captures and returns a screenshot of this screen region.
	 * 
	 * @return	a BufferedImage containing the screenshot. The type of the image is TYPE_3BYTE_BGR.
	 */
	public abstract BufferedImage capture();

	/**
	 * Adds a listener for a given target.
	 * 
	 * @param target the target to listener its events for.
	 * @param listener the listener to handle the events associated with the target.
	 */
	public abstract void addTargetEventListener(Target target,
			TargetEventListener listener);

	/**
	 * Removes a particular listener for a particular target.
	 * 
	 * @param target	the target from which the given listener should be removed.
	 * @param listener	the listener to remove from the given target.
	 */
	public abstract void removeTargetEventListener(Target target,
			TargetEventListener listener);
	/**
	 * Returns a {@link ScreenRegion} object that corresponds to the screen and region of this ScreenRegion.
	 * 
	 * @return a ScreenRegion that corresponds to the region of this ScreenRegion.
	 */
	public abstract ScreenRegion snapshot();

	/**
	 * Adds a listener to handle the state changes within this screen region.
	 * 
	 * @param listener	the listener to handle state changes in this screen region.
	 */
	public abstract void addStateChangeEventListener(StateChangeListener listener);
	/**
	 * Returns a map of {@link Target} objects and states, which can be any object, for this screen region.
	 * 
	 * @return a map of Target objects and their states.
	 */
	public abstract Map<Target, Object> getStates();
	/**
	 * Add a rectangular region of interest into this ScreenRegion.
	 * 
	 * @param x The X coordinate of the upper-left corner of the rectangle to be added.
	 * @param y The Y coordinate of the upper-left corner of the rectangle to be added.
	 * @param width The width of the rectangle.
	 * @param height width The width of the rectangle.
	 */
	public abstract void addROI(int x, int y, int width, int height);
	/**
	 * Returns a list of Rectangle that represent the rectangular regions of interest for this ScreenRegion.
	 *  
	 * @return a list of rectangles. 
	 */
	public abstract List<Rectangle> getROIs();

	//public abstract boolean contains(ScreenRegion r);

	
	/**
	 * Returns the last captured image of this ScreenRegion.
	 * 
	 * @return a BufferedImage that contains the last captured image of this ScreenRegion.
	 */
	public BufferedImage getLastCapturedImage();

}