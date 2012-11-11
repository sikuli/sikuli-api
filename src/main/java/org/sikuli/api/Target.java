package org.sikuli.api;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.sikuli.core.search.RegionMatch;

import com.google.common.collect.Lists;

/**
 * The Target class allows you to specify what targets to find or wait for 
 * and also to specify several control parameters. It is intended mainly to 
 * passed as an input argument to target finding functions such as 
 * {@link ScreenRegion#find(Target)} and {@link ScreenRegion#findAll(Target)}.
 * <p>
 * The Target class is an abstract base class. As such, you should not use this 
 * class directly. Instead, you should use one of the concrete classes inheriting 
 * from this class, such as {@link ImageTarget} or {@link ModelTextTarget}.
 *  
 * 
 * @author Tom Yeh (tom.yeh@colorado.edu)
 *
 */
abstract public class Target {

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
	
	protected double getDefaultMinScore() { return 0;};
	protected int getDefaultLimit() { return 100;};
		
	/**
	 * Constructs a Target with default parameters
	 * 
	 */
	public Target(){
		setMinScore(getDefaultMinScore());
		setLimit(getDefaultLimit());
	}

	public double getMinScore() {
		return minScore;
	}	

	/**
	 * Sets the minimum matching score. This controls how "fuzzy" the
	 * image matching should be. The score should be between 0 and 1 
	 * where 1 is the most precise (least fuzzy).
	 * 
	 * @param minScore
	 */
	public void setMinScore(double minScore) {
		this.minScore = minScore;
	}

	public int getLimit() {
		return limit;
	}
	
	/**
	 * Sets the limit on the number of matched targets to return.
	 * 
	 * @param limit	the number of matches
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public Ordering getOrdering() {
		return ordering;
	}

	/**
	 * Sets the ordering of the matched targets.
	 * 
	 * @param ordering
	 */
	public void setOrdering(Ordering ordering) {
		this.ordering = ordering;
	}

	private double minScore = 0;
	private int limit = 0;
	private Ordering ordering = Ordering.DEFAULT;
	
	
	abstract protected List<ScreenRegion> getUnordredMatches(ScreenRegion screenRegion);
	
	protected static List<ScreenRegion> convertToScreenRegions(ScreenRegion parent, List<RegionMatch> rms) {
		List<ScreenRegion> irs = Lists.newArrayList();		
		for (RegionMatch rm : rms){
			ScreenRegion ir = new DefaultScreenRegion(parent, rm.getX(),rm.getY(),rm.getWidth(),rm.getHeight());
			ir.setScore(rm.getScore());
			irs.add(ir);
		}
		return irs;
	}
	
	List<ScreenRegion> doFindAll(ScreenRegion screenRegion) {
		// get raw results
		List<ScreenRegion> ScreenRegions = getUnordredMatches(screenRegion);

		// sorting
		if (ordering == Ordering.TOP_DOWN){
			Collections.sort(ScreenRegions, new Comparator<ScreenRegion>(){
				@Override
				public int compare(ScreenRegion a, ScreenRegion b) {
					return a.getBounds().y - b.getBounds().y;
				}    				    				
			});
		}else if (ordering == Ordering.BOTTOM_UP){
			Collections.sort(ScreenRegions, new Comparator<ScreenRegion>(){
				@Override
				public int compare(ScreenRegion a, ScreenRegion b) {
					return b.getBounds().y - a.getBounds().y;
				}    				    				
			});			
		}else if (ordering == Ordering.LEFT_RIGHT){
			Collections.sort(ScreenRegions, new Comparator<ScreenRegion>(){
				@Override
				public int compare(ScreenRegion a, ScreenRegion b) {
					return a.getBounds().x - b.getBounds().x;
				}    				    				
			});			
		}else if (ordering == Ordering.RIGHT_LEFT){
			Collections.sort(ScreenRegions, new Comparator<ScreenRegion>(){
				@Override
				public int compare(ScreenRegion a, ScreenRegion b) {
					return b.getBounds().x - a.getBounds().x;
				}    				    				
			});			
		}
		
		return ScreenRegions;
	}
	
	/**
	 * Gets the image representation of this target for visualization purposes
	 * 
	 * @return a BufferedImage object
	 */
	public BufferedImage toImage() {
		return null;
	}
		
}