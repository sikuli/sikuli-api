package org.sikuli.api;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

import org.sikuli.core.search.RegionMatch;

public class StyledRectangleTarget extends ImageTarget {
	
	public StyledRectangleTarget(URL url) {
		super(url);
	}
	
	public StyledRectangleTarget(BufferedImage image) {
		super(image);
	}


	@Override
	protected List<ScreenRegion> getUnordredMatches(ScreenRegion screenRegion){
		BufferedImage exampleImage = getImage();
		FourCornerModel buttonModel = FourCornerModel.learnFrom(exampleImage);
		List<RegionMatch> matches = VisualModelFinder.searchButton(buttonModel, 
				screenRegion.capture());	
		List<RegionMatch> subList = matches.subList(0,  Math.min(getLimit(), matches.size()));
		return convertToScreenRegions(screenRegion, subList);
	}

}
