package org.sikuli.api;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.sikuli.core.search.RegionMatch;

import com.google.common.collect.Lists;

/**
 *  DefaultTarget is the abstract base class for all targets.
 *  
 * @author Tom Yeh (tom.yeh@colorado.edu)
 *
 */
abstract public class DefaultTarget implements Target {

	/**
	 * Returns the default minimum matching score of this Target. 
	 * This value controls how "fuzzy" the image matching should be.
	 * 
	 * @return returns 0 as the default minimum matching score value for this DefaultTarget, which 
	 * means the least precise (most fuzzy) image recognition, and a match is only somehow similar acceptable.
	 */
	protected double getDefaultMinScore() { return 0;};
	/**
	 * Returns the default limit on the number of matched targets to return.
	 * 
	 * @return returns 100, the default limit on the number of matched targets to return.
	 */
	protected int getDefaultLimit() { return 100;};
		
	/**
	 * Constructs a Target with default parameters
	 * 
	 */
	public DefaultTarget(){
		setMinScore(getDefaultMinScore());
		setLimit(getDefaultLimit());
	}
	/**
	 * Returns the minimum matching score of this DefaultTarget. The score should be between
	 * 0 and 1 where 1 is the best.
	 * 
	 * @return the minimum matching score value for a target to be considered a match.
	 */
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
	/**
	 * Returns the limit on the number of matched targets to return.
	 * 
	 * @return the limit on the number of matches to return.
	 */
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
	/**
	 * Returns the Ordering of matched targets this DefaultTarget uses. The Ordering value indicates 
	 * how multiple targets are ordered by find related functions.
	 */
	public Ordering getOrdering() {
		return ordering;
	}

	/**
	 * Sets the ordering of the matched targets.
	 * 
	 * @param ordering the Ordering of the matched targets for this Target.
	 */
	public void setOrdering(Ordering ordering) {
		this.ordering = ordering;
	}

	private double minScore = 0;
	private int limit = 0;
	private Ordering ordering = Ordering.DEFAULT;
	
	/**
	 * Returns an unsorted list of ScreenRegion objects compared to the target region.
	 * @param screenRegion the screen region to be compared with this Target.
	 * 
	 * @return an unsorted list of ScreenRegion objects compared to the target region.
	 */
	abstract protected List<ScreenRegion> getUnorderedMatches(ScreenRegion screenRegion);
	/**
	 * Converts RegionMatch objects into ScreenRegion objects.
	 * 
	 * @param parent the parent ScreenRegion of the matched screen regions
	 * @param rms the matched screen regions.
	 * @return a new list of ScreenRegion objects that correspond to the matched regions.
	 */
	protected static List<ScreenRegion> convertToScreenRegions(ScreenRegion parent, List<RegionMatch> rms) {
		List<ScreenRegion> irs = Lists.newArrayList();		
		for (RegionMatch rm : rms){
			ScreenRegion ir = new DefaultScreenRegion(parent, rm.getX(),rm.getY(),rm.getWidth(),rm.getHeight());
			ir.setScore(rm.getScore());
			irs.add(ir);
		}
		return irs;
	}
	
	@Override
	public List<ScreenRegion> doFindAll(ScreenRegion screenRegion) {
		// get raw results
		List<ScreenRegion> ScreenRegions = getUnorderedMatches(screenRegion);

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