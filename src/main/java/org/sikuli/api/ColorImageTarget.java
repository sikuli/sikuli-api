package org.sikuli.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.sikuli.core.search.ColorImageQuery;
import org.sikuli.core.search.ImageQuery;
import org.sikuli.core.search.ImageSearcher;
import org.sikuli.core.search.RegionMatch;
import org.sikuli.core.search.ScoreFilter;
import org.sikuli.core.search.ScoredItem;

import com.google.common.collect.Lists;

public class ColorImageTarget extends ImageTarget {
	
	public ColorImageTarget(URL url) {
		super(url);
	}
	
	public ColorImageTarget(BufferedImage targetImage){
		super(targetImage);
	}	
	
	public ColorImageTarget(File file) {
		super(file);
	}

	@Override
	protected double getDefaultMinScore(){		
		return 0.9;	
	}
	
	@Override
	protected List<ScreenRegion> getUnorderedMatches(ScreenRegion screenRegion){
		ImageSearcher searcher = new ImageSearcher(screenRegion.capture());
		ImageQuery query = new ColorImageQuery(targetImage);
		ScoreFilter<RegionMatch> filter = new ScoreFilter<RegionMatch>(getMinScore());
		List<RegionMatch> topMatches = searcher.search(query, filter, getLimit());	
		return convertToScreenRegions(screenRegion, topMatches);
	}
	
}