 package org.sikuli.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * The ColorImageTarget class defines targets based on color patterns. It also takes into account the textures patterns. 
 * This class can be used to distinguish between two targets having the same shapes or texture patterns but only differ 
 * in the color patterns. <p>
 * For example, it can be used to distinguish between a blue circle and a red circle.
 * It's very important to use this class when having targets that differ only in color since other Target classes such as 
 * {@link ImageTarget} may fail to identify targets that differ only in colors.
 *
 */
public class ColorImageTarget extends ImageTarget {
	/**
	 * Creates a ColorImageTarget from an image at a given URL.
	 * 
	 * @param url the URL to load the image.
	 */
	public ColorImageTarget(URL url) {
		super(url);
	}
	/**
	 * Creates a ColorImageTarget from a BufferedImage.
	 * 
	 * @param targetImage the image representing this target.
	 */
	public ColorImageTarget(BufferedImage targetImage){
		super(targetImage);
	}	
	/**
	 * Creates a ColorImageTarget from a given File object.
	 * 
	 * @param file the File to read image data from.
	 */
	public ColorImageTarget(File file) {
		super(file);
	}
	/**
	 * @return returns 0.9 as the default minimum matching score for this ColorImageTarget.
	 */
	@Override
	protected double getDefaultMinScore(){		
		return 0.9;	
	}
	
	@Override
	protected List<ScreenRegion> getUnorderedMatches(ScreenRegion screenRegion){
//		ImageSearcher searcher = new ImageSearcher(screenRegion.capture());
//		ImageQuery query = new ColorImageQuery(targetImage);
//		ScoreFilter<RegionMatch> filter = new ScoreFilter<RegionMatch>(getMinScore());
//		List<RegionMatch> topMatches = searcher.search(query, filter, getLimit());	
//		return convertToScreenRegions(screenRegion, topMatches);
		throw new UnsupportedOperationException("not yet implemented");
	}
	
}