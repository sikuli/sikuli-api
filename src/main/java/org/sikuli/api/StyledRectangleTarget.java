package org.sikuli.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.sikuli.core.search.RegionMatch;
/**
 * StyledRectangleTarget class is used to identify rectangular targets that have a particular rectangle style
 *  (lines and corners) while ignoring the content inside the rectangle. <p>
 * For example, it can be used to find buttons that have the same rounded corners. 
 *
 */
public class StyledRectangleTarget extends ImageTarget {
	/**
	 * Creates a StyledRectangleTarget from an image at a given URL. 
	 * The corners will be automatically identified from the specified URL image.
	 * 
	 * @param url the URL to load the image of the target.
	 * @throws IOException thrown if the image can not be loaded
	 */
	public StyledRectangleTarget(URL url) {
		super(url);
	}
	/**
	 * Creates a StyledRectangleTarget from a File object.
	 * The corners will be automatically identified from the specified File image.
	 * @param file the File to read image data from.
	 * @throws IOException	thrown if the File can not be read.
	 */
	public StyledRectangleTarget(File file) {
		super(file);
	}
	/**
	 * Creates a StyledRectangleTarget from a BufferedImage.
	 * 
	 * @param image the image representing this StyledRectangleTarget.
	 * The corners will be automatically identified from the specified BufferedImage.
	 */
	public StyledRectangleTarget(BufferedImage image) {
		super(image);
	}


	@Override
	protected List<ScreenRegion> getUnorderedMatches(ScreenRegion screenRegion){
		BufferedImage exampleImage = getImage();
		FourCornerModel buttonModel = FourCornerModel.learnFrom(exampleImage);
		List<RegionMatch> matches = VisualModelFinder.searchButton(buttonModel, 
				screenRegion.capture());	
		List<RegionMatch> subList = matches.subList(0,  Math.min(getLimit(), matches.size()));
		return convertToScreenRegions(screenRegion, subList);
	}

}
