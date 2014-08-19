package org.sikuli.api;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import org.bytedeco.javacpp.*;

import org.sikuli.api.SearchByTextureAndColorAtOriginalResolution.ColorRegionMatch;
import org.sikuli.core.cv.ImagePreprocessor;
import org.sikuli.core.cv.VisionUtils;
import org.sikuli.core.search.TemplateMatcher;
import org.sikuli.core.search.TemplateMatcher.Result;
import org.sikuli.core.search.internal.TemplateMatchingUtilities;
import org.sikuli.core.search.internal.TemplateMatchingUtilities.TemplateMatchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;

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
		
		
		BufferedImage screenImage = screenRegion.capture();
		
		SearchByTextureAndColorAtOriginalResolution s = new 
				SearchByTextureAndColorAtOriginalResolution(IplImage.createFrom(screenImage),
						IplImage.createFrom(targetImage));
		
		s.execute();		
		final List<TemplateMatcher.Result> matches = Lists.newArrayList();		
		while(matches.size() < getLimit()){
			ColorRegionMatch m = s.fetchNext();
			if (m.getScore() >= getMinScore()){
				matches.add(m);
			}else{
				break;
			}
		}
		
		return convertToScreenRegions(screenRegion, matches);
	}








}

class SearchByTextureAndColorAtOriginalResolution {


	static private Logger logger = LoggerFactory.getLogger(SearchByTextureAndColorAtOriginalResolution.class);

	private IplImage query;
	private IplImage input;		

	private CvScalar avergerColorOfTheQueryImage;
	private IplImage resultMatrix = null;

	public SearchByTextureAndColorAtOriginalResolution(IplImage input, IplImage query){
		this.input = input;
		this.query = query;
	}

	public void execute(){
		this.resultMatrix = TemplateMatchingUtilities.computeTemplateMatchResultMatrix(
				ImagePreprocessor.createGrayscale(input), 
				ImagePreprocessor.createGrayscale(query));
		// compute average color of the query image		
		logger.trace("channels:" + query.nChannels());
		logger.trace("alpha channel:" + query.alphaChannel());
		logger.trace("bt:" + query.getBufferedImageType());
		if (query.nChannels()==4){
			// When the input image has an alpha channel, the color space is abgr 
			// and must be converted to bgr in order to compare its color to captured screen data
			query = VisionUtils.cloneWithoutAlphaChannel(query); 
		}
		if (input.nChannels()==4){
			// When the input image has an alpha channel, the color space is abgr 
			// and must be converted to bgr in order to compare its color to captured screen data
			input = VisionUtils.cloneWithoutAlphaChannel(input); 
		}
		CvScalar avg = cvAvg(query, null);
		this.avergerColorOfTheQueryImage = avg;
	}


	static private double calculateL1Distance(CvScalar a, CvScalar b){
		////System.out.println();	
		logger.trace(toString(a) + "<->" + toString(b));
		double d = 0;
		for (int i = 0; i < 3; i++){
			d += Math.abs(a.getVal(i) - b.getVal(i));
		}
		return d;
	}

	static private String toString(CvScalar a){
		String s = "[";
		for (int i = 0; i < 4; i++){
			s = s + String.format("%3.0f ",a.getVal(i));
		}
		s = s + "]";
		return s;
	}

	// return the raw distance score 
	private double calculateColorDifferenceBetweenMatchedRegionAndTarget(Rectangle m){
		// compute the average color of the found match
		cvSetImageROI(input, cvRect(m.x,m.y,m.width,m.height));			
		CvScalar averageColorOfTheMatchedRegion = cvAvg(input, null);			
		double diff = calculateL1Distance(avergerColorOfTheQueryImage, averageColorOfTheMatchedRegion);
		cvResetImageROI(input);						
		return diff;
	}

	// return a score between 0 and 1
	private double calculateColorMatchScore(Rectangle r){
		double rawScore =  calculateColorDifferenceBetweenMatchedRegionAndTarget(r);
		return (255 - Math.min(rawScore,255))/(255);
	}

	static class ColorRegionMatch extends Result{
		double colorScore;
		double textureScore;

		public ColorRegionMatch(Rectangle r){
			super(r.getBounds());
		}

		public double getScore(){
			double s1 = textureScore;
			double s2 = colorScore;
			//			double s = (s1+s2)/2;
			double s;
			if (s2 > 0.85){
				s = s1;
			}else{
				s = 0;
			}
			return s;
		}

		public String toString(){
			return " x = " + x + ", y = " + y + ", textureScore = " + String.format("%1.3f", textureScore)
					+ ", colorScore = " + String.format("%1.3f", colorScore);

		}
	}


	private ColorRegionMatch fetchedMatch = null; 
	private LinkedList<ColorRegionMatch> prefetchedCandidates = new LinkedList<ColorRegionMatch>();
	private int MAX_NUMBER_TO_PREFETCH = 10;


	private ColorRegionMatch fetchNextColorRegionMath(){
		TemplateMatchResult result = TemplateMatchingUtilities.fetchNextBestMatch(resultMatrix, query);

		ColorRegionMatch newMatch = new ColorRegionMatch(result.getBounds());
		newMatch.colorScore = calculateColorMatchScore(result.getBounds());			
		newMatch.textureScore = result.score;
		return newMatch;
	}

	private void prefetch(){

		// if there's a previously fetched match that was not added to the list
		if (fetchedMatch != null){
			prefetchedCandidates.add(fetchedMatch);
			fetchedMatch = null;
		}

		while (true){

			// get previous match
			ColorRegionMatch previousMatch;
			if (prefetchedCandidates.isEmpty()){
				previousMatch = null;
			}else{
				previousMatch = prefetchedCandidates.getLast();
			}

			// get new match
			ColorRegionMatch newMatch = fetchNextColorRegionMath();
			logger.trace("prefecth (" + prefetchedCandidates.size() + ")" + newMatch);

			double dropInTextureSimilarity = previousMatch == null ? 0 : 
				(1.0 - newMatch.textureScore / previousMatch.textureScore);

			boolean isDropInTextureSimilaritySingificant =  dropInTextureSimilarity > 0.15;

			//System.out.println("Drop:" + String.format("%1.2f",dropInTextureSimilarity));


			if (isDropInTextureSimilaritySingificant){		
				previousMatch = newMatch;
				break;
			}else{
				prefetchedCandidates.add(newMatch);					
			}


			boolean hasPrefetchedEnough = prefetchedCandidates.size() >= MAX_NUMBER_TO_PREFETCH;
			if (hasPrefetchedEnough){
				break;
			}
		}

		Collections.sort(prefetchedCandidates, new Comparator<ColorRegionMatch>(){
			@Override
			public int compare(ColorRegionMatch a, ColorRegionMatch b) {
				return Doubles.compare(b.getScore(),a.getScore());
			}				
		});
	}

	public ColorRegionMatch fetchNext(){

		if (prefetchedCandidates.isEmpty()){
			prefetch();
		}

		ColorRegionMatch colorRegionMatch = prefetchedCandidates.poll();			
		return colorRegionMatch;
	}
}




