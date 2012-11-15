package org.sikuli.api;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.sikuli.core.cv.ImagePreprocessor;
import org.sikuli.core.search.ImageQuery;
import org.sikuli.core.search.ImageSearcher;
import org.sikuli.core.search.RegionMatch;
import org.sikuli.core.search.ScoreFilter;
import org.sikuli.core.search.algorithm.SearchByGrayscaleAtOriginalResolution;
import org.sikuli.core.search.algorithm.TemplateMatcher;

import com.google.common.collect.Lists;


/**
 * An ImageTarget object describes a target using an image.
 * 
 * @author Tom Yeh (tom.yeh@colorado.edu)
 *
 */
public class ImageTarget extends DefaultTarget implements Target {
	
	final BufferedImage targetImage;	
	final private String imageSource;
	private URL url = null;
	
	/**
	 * Creates an ImageTarget from an image at a given URL
	 * 
	 * @param url	url to load the image	
	 * @throws IOException	thrown if the image can not be loaded
	 */
	public ImageTarget(URL url) {
		super();
		try{
			targetImage = ImageIO.read(url);
		} catch (IOException e){
			throw new SikuliRuntimeException("Image file can not be loaded from " + url + " because " + e.getMessage());
		}
		this.imageSource = url.toString();
		this.url = url;
	}
	
	public URL getURL(){
		return url;
	}
	
	/**
	 * Creates an ImageTarget from a File object
	 * 
	 * @param file	the File to read image data from
	 * @throws IOException	thrown if the File can not be read
	 */
	public ImageTarget(File file) {
		super();
		try{
		targetImage = ImageIO.read(file);
		} catch (IOException e){
			throw new RuntimeException("Image file can not be loaded from " + file);
		}
		this.imageSource = file.getAbsolutePath();
	}


	/**
	 * Creates an ImageTarget from a BufferedImage
	 * 
	 * @param targetImage	the image representing this target 
	 */
	public ImageTarget(BufferedImage targetImage){
		super();
		this.targetImage = targetImage;
		this.imageSource = "[BufferedImage]";
	}
	
	public String toString(){
		return "[ImageTarget: " + imageSource + "]";
	}
	
	/**
	 * Gets the image describing the target
	 * 
	 * @return	a BufferedImage
	 */
	public BufferedImage getImage(){
		return targetImage;
	}
	
	
	@Override
	public BufferedImage toImage() {
		return targetImage;
	}


	@Override
	protected double getDefaultMinScore(){
		return 0.7;
	}

	@Override
	protected List<ScreenRegion> getUnordredMatches(ScreenRegion screenRegion){
		Rectangle screenRegionBounds = screenRegion.getBounds();
		if (screenRegionBounds.width < targetImage.getWidth() || screenRegionBounds.height < targetImage.getHeight()){
			// if screen region is smaller than the target, no target can be found
			// TODO: perhaps a more fault tolerant approach is to return a smaller target with a lower score
			return Lists.newArrayList();
		}
		
		List<RegionMatch> matches;
		
		List<Rectangle> rois = screenRegion.getROIs();
		if (rois.isEmpty()){		
			matches = TemplateMatcher.findMatchesByGrayscaleAtOriginalResolution(screenRegion.capture(), targetImage, getLimit(), getMinScore());
		}else{
			matches = TemplateMatcher.findMatchesByGrayscaleAtOriginalResolutionWithROIs(screenRegion.capture(), targetImage, getLimit(), getMinScore(), rois);			
		}
//		SearchByGrayscaleAtOriginalResolution alg = new SearchByGrayscaleAtOriginalResolution(screenRegion.getImage(),targetImage);
//		alg.execute();
//		List<RegionMatch> matches = alg.fetchAll(getLimit(), getMinScore());
		

//		ImageSearcher searcher = new ImageSearcher(screenRegion.getImage());
//		ImageQuery query = new ImageQuery(targetImage);
//		ScoreFilter<RegionMatch> filter = new ScoreFilter<RegionMatch>(getMinScore());
//		List<RegionMatch> topMatches = searcher.search(query, filter, getLimit());
		return convertToScreenRegions(screenRegion, matches);
	}
	
	
}