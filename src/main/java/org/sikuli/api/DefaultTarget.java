package org.sikuli.api;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.sikuli.core.search.RegionMatch;

import com.google.common.collect.Lists;

/**
 * 
 * @author Tom Yeh (tom.yeh@colorado.edu)
 *
 */
abstract public class DefaultTarget implements Target {


	protected double getDefaultMinScore() { return 0;};
	protected int getDefaultLimit() { return 100;};
		
	/**
	 * Constructs a Target with default parameters
	 * 
	 */
	public DefaultTarget(){
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
	
	
	abstract protected List<ScreenRegion> getUnorderedMatches(ScreenRegion screenRegion);
	
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