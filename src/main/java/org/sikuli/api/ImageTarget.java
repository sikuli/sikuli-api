package org.sikuli.api;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.sikuli.core.search.TemplateMatcher;

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
	 * Creates an ImageTarget from an image at a given URL.
	 * 
	 * @param url the URL to load the image.
	 * @throws IOException thrown if the image can not be loaded
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
	/**
	 * Returns the URL of the image for this ImageTarget.
	 * 
	 * @return the URL to load the image.
	 */
	public URL getURL(){
		return url;
	}
	
	/**
	 * Creates an ImageTarget from a File object.
	 * 
	 * @param file the File to read image data from.
	 * @throws IOException	thrown if the File can not be read.
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
	 * Creates an ImageTarget from a BufferedImage.
	 * 
	 * @param targetImage the image representing this target. 
	 */
	public ImageTarget(BufferedImage targetImage){
		super();
		this.targetImage = targetImage;
		this.imageSource = "[BufferedImage]";
	}
	/**
	 * Returns a String object representing this ImageTarget object's value.
	 * 
	 * @return a string representation of this ImageTarget.
	 */
	public String toString(){
		return "[ImageTarget: " + imageSource + "]";
	}
	
	/**
	 * Returns the image describing this ImageTarget.
	 * 
	 * @return	a BufferedImage representing this target.
	 */
	public BufferedImage getImage(){
		return targetImage;
	}
	
	
	@Override
	public BufferedImage toImage() {
		return targetImage;
	}

	/**
	 * @return returns 0.7 as the default minimum matching score for this ImageTarget.
	 */
	@Override
	protected double getDefaultMinScore(){
		return 0.7;
	}

	@Override
	protected List<ScreenRegion> getUnorderedMatches(ScreenRegion screenRegion){
		Rectangle screenRegionBounds = screenRegion.getBounds();
		if (screenRegionBounds.width < targetImage.getWidth() || screenRegionBounds.height < targetImage.getHeight()){
			// if screen region is smaller than the target, no target can be found
			// TODO: perhaps a more fault tolerant approach is to return a smaller target with a lower score
			return Lists.newArrayList();
		}
		
		List<TemplateMatcher.Result> matches;
		
		BufferedImage screenImage = screenRegion.capture();		
		List<Rectangle> rois = screenRegion.getROIs();
		if (rois.isEmpty()){		
			matches = TemplateMatcher.findMatchesByGrayscaleAtOriginalResolution(screenImage, targetImage, getLimit(), getMinScore());
		}else{
			matches = TemplateMatcher.findMatchesByGrayscaleAtOriginalResolutionWithROIs(screenImage, targetImage, getLimit(), getMinScore(), rois);			
		}
		return convertToScreenRegions(screenRegion, matches);
	}
	
	
}