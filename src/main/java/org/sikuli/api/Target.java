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
 * from this class, such as {@link ImageTarget}, {@link TextTarget}, {@link StyledRectangleTarget}, 
 * {@link MultiStateTarget}, {@link ColorImageTarget}, and {@link ForegroundTarget}.
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
	
	/**
	 * Returns the minimum matching score of this Target. This value controls how "fuzzy" the
	 * image matching should be. The score should be between
	 * 0 and 1, where 1 is the most precise (least fuzzy), and 0 is the least precise (most fuzzy).
	 * 
	 * @return the minimum matching score value for a target to be considered a match.
	 */
	public double getMinScore();

	/**
	 * Sets the minimum matching score. This controls how "fuzzy" the
	 * image matching should be. The score should be between 0 and 1, 
	 * where 1 is the most precise (least fuzzy), and 0 is the least precise (most fuzzy).
	 * 
	 * @param minScore the minimum matching score value for a target to be considered a match.
	 */
	public void setMinScore(double minScore);
	
	/**
	 * Returns the limit on the number of matched targets to return.
	 * 
	 * @return the number of matched targets this Target returns.
	 */
	public int getLimit();
	
	/**
	 * Sets the limit on the number of matched targets to return.
	 * 
	 * @param limit	the number of matches used by this Target
	 */
	public void setLimit(int limit);
	
	/**
	 * Returns the Ordering of matched targets this Target uses. The Ordering value indicates 
	 * how multiple targets are ordered by find related functions.
	 * 
	 * @return the Ordering of the matched targets for this Target.
	 */
	public Ordering getOrdering();

	/**
	 * Sets the ordering of the matched targets.
	 * 
	 * @param ordering the Ordering of the matched targets for this Target
	 */
	public void setOrdering(Ordering ordering);
	/**
	 * Sorts all found instances of a screenRegion objects according to the Ordering value of this Target.
	 * 
	 * @param screenRegion the ScreenRegion to be sorted with other unsorted regions.
	 * 
	 * @return a sorted list of ScreenRegions.
	 */
	public List<ScreenRegion> doFindAll(ScreenRegion screenRegion);
		
}