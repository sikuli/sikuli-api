package org.sikuli.api;

import java.util.List;

/**
 * The Target interface allows you to specify what targets to find or wait for 
 * and also to specify several control parameters. It is intended mainly to 
 * passed as an input argument to target finding functions such as 
 * {@link ScreenRegion#find(Target)} and {@link ScreenRegion#findAll(Target)}.
 * <p>
 * The Target class is an abstract base class. As such, you should not use this 
 * class directly. Instead, you should use one of the concrete classes inheriting 
 * from this class, such as {@link ImageTarget}.
 *  
 * 
 * @author Tom Yeh (tom.yeh@colorado.edu)
 *
 */
public interface Target {

	/**
	 * Defines a set of constants to use to indicate how multiple targets
	 * should be ordered by find functions 
	 * 
	 * @author tomyeh
	 *
	 */
	public enum Ordering {
		/**
		 * Default ordering by scores
		 */
		DEFAULT,
		/**
		 * Ordering from left to right
		 */
		LEFT_RIGHT,
		/**
		 * Ordering from top to down 
		 */
		TOP_DOWN,		
		/**
		 * Ordering from bottom to up
		 */
		BOTTOM_UP, 		
		/**
		 * Ordering from right to left 
		 */
		RIGHT_LEFT
	};
	

	public double getMinScore();

	/**
	 * Sets the minimum matching score. This controls how "fuzzy" the
	 * image matching should be. The score should be between 0 and 1 
	 * where 1 is the most precise (least fuzzy).
	 * 
	 * @param minScore
	 */
	public void setMinScore(double minScore);

	public int getLimit();
	
	/**
	 * Sets the limit on the number of matched targets to return.
	 * 
	 * @param limit	the number of matches
	 */
	public void setLimit(int limit);
	
	public Ordering getOrdering();

	/**
	 * Sets the ordering of the matched targets.
	 * 
	 * @param ordering
	 */
	public void setOrdering(Ordering ordering);
	
	public List<ScreenRegion> doFindAll(ScreenRegion screenRegion);
		
}