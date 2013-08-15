package org.sikuli.api;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.ImageCanvas;
import org.sikuli.core.cv.ImagePreprocessor;
import org.sikuli.core.cv.VisionUtils;
import org.sikuli.core.search.TemplateMatchingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.googlecode.javacv.cpp.opencv_core;
import com.googlecode.javacv.cpp.opencv_core.CvPoint;
import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.IplImage;


class ImageLoggerFactory {

	public static ImageLogger getLogger(Class clazz){
		Logger textLogger = LoggerFactory.getLogger(clazz);
		return new ImageLogger(textLogger);
	}

}

interface Loggable<T> {	
	public T log();
}

class ImageLogger implements Logger {

	private Logger textLogger;
	public ImageLogger(Logger textLogger){
		this.textLogger = textLogger;
	}

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");	
	static public String getTimeStamp(){
		return sdf.format(new Date());	
	}
	
	private String createUniqueImageName(){
		return getName() + "_" + getTimeStamp() + ".png";
	}

	public void trace(Loggable<BufferedImage> loggable, String title){
		if (isTraceEnabled()){
			BufferedImage image = loggable.log();
			System.out.println(createUniqueImageName() + ": write image");
		}
	}


	public String getName() {
		return textLogger.getName();
	}
	public boolean isTraceEnabled() {
		return textLogger.isTraceEnabled();
	}
	public void trace(String msg) {
		textLogger.trace(msg);
	}
	public void trace(String format, Object arg) {
		textLogger.trace(format, arg);
	}
	public void trace(String format, Object arg1, Object arg2) {
		textLogger.trace(format, arg1, arg2);
	}
	public void trace(String format, Object[] argArray) {
		textLogger.trace(format, argArray);
	}
	public void trace(String msg, Throwable t) {
		textLogger.trace(msg, t);
	}
	public boolean isTraceEnabled(Marker marker) {
		return textLogger.isTraceEnabled(marker);
	}
	public void trace(Marker marker, String msg) {
		textLogger.trace(marker, msg);
	}
	public void trace(Marker marker, String format, Object arg) {
		textLogger.trace(marker, format, arg);
	}
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		textLogger.trace(marker, format, arg1, arg2);
	}
	public void trace(Marker marker, String format, Object[] argArray) {
		textLogger.trace(marker, format, argArray);
	}
	public void trace(Marker marker, String msg, Throwable t) {
		textLogger.trace(marker, msg, t);
	}
	public boolean isDebugEnabled() {
		return textLogger.isDebugEnabled();
	}
	public void debug(String msg) {
		textLogger.debug(msg);
	}
	public void debug(String format, Object arg) {
		textLogger.debug(format, arg);
	}
	public void debug(String format, Object arg1, Object arg2) {
		textLogger.debug(format, arg1, arg2);
	}
	public void debug(String format, Object[] argArray) {
		textLogger.debug(format, argArray);
	}
	public void debug(String msg, Throwable t) {
		textLogger.debug(msg, t);
	}
	public boolean isDebugEnabled(Marker marker) {
		return textLogger.isDebugEnabled(marker);
	}
	public void debug(Marker marker, String msg) {
		textLogger.debug(marker, msg);
	}
	public void debug(Marker marker, String format, Object arg) {
		textLogger.debug(marker, format, arg);
	}
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		textLogger.debug(marker, format, arg1, arg2);
	}
	public void debug(Marker marker, String format, Object[] argArray) {
		textLogger.debug(marker, format, argArray);
	}
	public void debug(Marker marker, String msg, Throwable t) {
		textLogger.debug(marker, msg, t);
	}
	public boolean isInfoEnabled() {
		return textLogger.isInfoEnabled();
	}
	public void info(String msg) {
		textLogger.info(msg);
	}
	public void info(String format, Object arg) {
		textLogger.info(format, arg);
	}
	public void info(String format, Object arg1, Object arg2) {
		textLogger.info(format, arg1, arg2);
	}
	public void info(String format, Object[] argArray) {
		textLogger.info(format, argArray);
	}
	public void info(String msg, Throwable t) {
		textLogger.info(msg, t);
	}
	public boolean isInfoEnabled(Marker marker) {
		return textLogger.isInfoEnabled(marker);
	}
	public void info(Marker marker, String msg) {
		textLogger.info(marker, msg);
	}
	public void info(Marker marker, String format, Object arg) {
		textLogger.info(marker, format, arg);
	}
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		textLogger.info(marker, format, arg1, arg2);
	}
	public void info(Marker marker, String format, Object[] argArray) {
		textLogger.info(marker, format, argArray);
	}
	public void info(Marker marker, String msg, Throwable t) {
		textLogger.info(marker, msg, t);
	}
	public boolean isWarnEnabled() {
		return textLogger.isWarnEnabled();
	}
	public void warn(String msg) {
		textLogger.warn(msg);
	}
	public void warn(String format, Object arg) {
		textLogger.warn(format, arg);
	}
	public void warn(String format, Object[] argArray) {
		textLogger.warn(format, argArray);
	}
	public void warn(String format, Object arg1, Object arg2) {
		textLogger.warn(format, arg1, arg2);
	}
	public void warn(String msg, Throwable t) {
		textLogger.warn(msg, t);
	}
	public boolean isWarnEnabled(Marker marker) {
		return textLogger.isWarnEnabled(marker);
	}
	public void warn(Marker marker, String msg) {
		textLogger.warn(marker, msg);
	}
	public void warn(Marker marker, String format, Object arg) {
		textLogger.warn(marker, format, arg);
	}
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		textLogger.warn(marker, format, arg1, arg2);
	}
	public void warn(Marker marker, String format, Object[] argArray) {
		textLogger.warn(marker, format, argArray);
	}
	public void warn(Marker marker, String msg, Throwable t) {
		textLogger.warn(marker, msg, t);
	}
	public boolean isErrorEnabled() {
		return textLogger.isErrorEnabled();
	}
	public void error(String msg) {
		textLogger.error(msg);
	}
	public void error(String format, Object arg) {
		textLogger.error(format, arg);
	}
	public void error(String format, Object arg1, Object arg2) {
		textLogger.error(format, arg1, arg2);
	}
	public void error(String format, Object[] argArray) {
		textLogger.error(format, argArray);
	}
	public void error(String msg, Throwable t) {
		textLogger.error(msg, t);
	}
	public boolean isErrorEnabled(Marker marker) {
		return textLogger.isErrorEnabled(marker);
	}
	public void error(Marker marker, String msg) {
		textLogger.error(marker, msg);
	}
	public void error(Marker marker, String format, Object arg) {
		textLogger.error(marker, format, arg);
	}
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		textLogger.error(marker, format, arg1, arg2);
	}
	public void error(Marker marker, String format, Object[] argArray) {
		textLogger.error(marker, format, argArray);
	}
	public void error(Marker marker, String msg, Throwable t) {
		textLogger.error(marker, msg, t);
	}

}

/**
 * SmartTarget
 * 
 * @author Tom Yeh (tom.yeh@colorado.edu)
 *
 */
public class SmartTarget extends DefaultTarget implements Target {


	final private static ImageLogger logger = ImageLoggerFactory.getLogger(SmartTarget.class);

	final private  BufferedImage contextImage;	
	final private  int x;
	final private int y;	
	private String imageSource="";

	/**
	 * Creates a StartTarget from a context image at a URL and the location (x,y)
	 * of the center of the target
	 * 
	 * @param imageUrl	url to load the image	
	 * @throws IOException	thrown if the image can not be loaded
	 */
	public SmartTarget(URL imageUrl, int x, int y) {
		this.x = x;
		this.y = y;		
		try{
			contextImage = ImageIO.read(imageUrl);
		} catch (IOException e){
			throw new SikuliRuntimeException("Image file can not be loaded from " + imageUrl + " because " + e.getMessage());
		}
	}

	/**
	 * Creates a SmartTarget from an image and a target location (x,y)
	 * 
	 * @param file	the File to read image data from
	 * @throws IOException	thrown if the File can not be read
	 */
	public SmartTarget(File file, int x, int y) {
		super();
		this.x = x;
		this.y = y;		
		try{
			contextImage = ImageIO.read(file);
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
	public SmartTarget(BufferedImage targetImage, int x, int y){
		super();
		this.x = x;
		this.y = y;		
		this.contextImage = targetImage;
		this.imageSource = "[BufferedImage]";
	}

	public String toString(){
		return "[StartTarget: " + x + "," + y + "]";
	}

	/**
	 * Gets the image describing the target
	 * 
	 * @return	a BufferedImage
	 */
	public BufferedImage getContextImage(){
		return contextImage;
	}

	static class ImageLocation implements Location {
		private int x;
		private int y;
		private Image image;
		public ImageLocation(Image image, int x, int y){
			this.image = image;
			this.x = x;
			this.y = y;
		}		

		@Override
		public int getX() {
			return x;
		}
		@Override
		public void setX(int x) {
			this.x = x;

		}
		@Override
		public int getY() {
			return y;
		}
		@Override
		public void setY(int y) {
			this.y = y;
		}
	}


	public Region computeCombinedRegion(List<Region> regions){
		if (regions.size() == 0){
			return new DefaultRegion(0,0,0,0);
		}

		Rectangle combinedRectangle = regions.get(0).getBounds();
		for (Region r : regions){
			combinedRectangle.add(r.getBounds());
		}
		return new DefaultRegion(combinedRectangle);
	}


	class CandidateRegion extends DefaultRegion implements Region, Comparable<CandidateRegion> {

		private double score;
		public CandidateRegion(int x, int y, int width, int height, double score) {
			super(x, y, width, height);
			this.score = score;
		}

		@Override
		public int compareTo(CandidateRegion o) {
			return Doubles.compare(score, o.score);
		}

	}

	@Override
	protected List<ScreenRegion> getUnorderedMatches(ScreenRegion screenRegion){



		IplImage input1 = ImagePreprocessor.createGrayscale(contextImage);


		int w = input1.width();
		int h = input1.height();
		int d = 10;

		final Location groundTruthLocation = new DefaultLocation(x,y);

		IplImage accum = IplImage.create(cvSize(w,h),32,1);


		List<Region> partRegions = Lists.newArrayList();

		for (int i=0; i < 15; ++i){

			int xi = x + d*i;
			int yi = y + 0;

			Region partRegion = new DefaultRegion(xi-d/2,yi-d/2,d,d);
			partRegions.add(partRegion);

			final Region combinedPartsRegion = computeCombinedRegion(partRegions);


			IplImage target = ImagePreprocessor.createGrayscale(contextImage);
			cvSetImageROI(target, cvRect(xi-d/2,yi-d/2,d,d));
			IplImage target1 = IplImage.create(cvSize(d,d), 8, 1);
			cvCopy(target,target1,null);

			cvSetImageROI(input1, cvRect(0+d*i,0,w-d*i,h));
			IplImage resultMatrix = TemplateMatchingUtilities.computeTemplateMatchResultMatrix(input1, target1);


			cvThreshold(resultMatrix, resultMatrix, 0.8, 0, CV_THRESH_TOZERO);
			IplImage resultMatrix1 = IplImage.create(cvGetSize(resultMatrix), 8, 1);
			cvConvertScale(resultMatrix, resultMatrix1, 255, 0);

			int xa = 0 + 0;
			int ya = 0 + 0;
			int wa = resultMatrix.width();
			int ha = resultMatrix.height();			

			cvSetImageROI(accum, cvRect(xa,ya,wa,ha));
			cvAdd(accum, resultMatrix, accum, null);

			double min[] = new double[1];
			double max[] = new double[1];
			CvPoint minPoint = new CvPoint(2);
			CvPoint maxPoint = new CvPoint(2);

			opencv_core.cvMinMaxLoc(accum, min, max, minPoint, maxPoint, null);

			cvThreshold(accum, accum, max[0] * 0.7, 0, CV_THRESH_TOZERO);

			double maxScore = max[0];
			CvPoint maxLoc = maxPoint;

			logger.trace("maxScore:" + maxScore + " loc:" + maxLoc.x() + "," + maxLoc.y());

			final Location maxLocation = new DefaultLocation(maxLoc.x(), maxLoc.y());

			final IplImage accumV = IplImage.create(cvGetSize(accum), 8, 1);
			cvConvertScale(accum, accumV, 255, 0);

			List<CvRect> peakBlobs = VisionUtils.detectBlobs(accumV);
			int numPeaks = peakBlobs.size();

			logger.trace("number of peaks: " + numPeaks);

			final List<CandidateRegion> candidates = Lists.newArrayList();



			for (CvRect blob : peakBlobs){
				cvSetImageROI(accum, blob);
				cvMinMaxLoc(accum, min, max, minPoint, maxPoint, null);
				double ratio = max[0] / maxScore;
				logger.trace("\tblob: maxScore:" + max[0] + " loc:" + maxPoint.x() + "," + maxPoint.y() + " ->top: " + ratio);

				int cx = blob.x() + maxPoint.x();
				int cy = blob.y() + maxPoint.y();
				int cw = combinedPartsRegion.getBounds().width;
				int ch = combinedPartsRegion.getBounds().height;
				double cscore = max[0];
				CandidateRegion candidate = new CandidateRegion(cx,cy,cw,ch,cscore);
				candidates.add(candidate);					

				cvResetImageROI(accum);				
			}

			Collections.sort(candidates);
			Collections.reverse(candidates);
			final CandidateRegion bestCandidate = candidates.get(0);

			//			Canvas c = new ImageCanvas(contextImage);
			//			c.addImage(new ImageLocation(contextImage,d*i,0), resultMatrix1.getBufferedImage()).withTransparency(0.5f);
			//
			//			Canvas c2 = new ImageCanvas(contextImage);
			//			c2.addDot(new DefaultLocation(xi,yi));


			Loggable<BufferedImage> accumLoggable = new Loggable<BufferedImage>(){ 

				public BufferedImage log(){
					Canvas e = new ImageCanvas(contextImage);
					e.addImage(new DefaultLocation(0,0), accumV.getBufferedImage()).withTransparency(0.5f);	

					e.addDot(groundTruthLocation).withColor(Color.green);
					e.addBox(combinedPartsRegion).withLineWidth(1).withLineColor(Color.green);

					for (CandidateRegion candidate : candidates){
						e.addBox(candidate).withLineWidth(1).withColor(Color.blue);
					}

					e.addDot(maxLocation).withColor(Color.red);
					e.addBox(bestCandidate).withColor(Color.red);
					return e.createImage();
				}
			};

			logger.trace(accumLoggable, "accumulated scores");

			////			BufferedImage image1 = c.createImage();
			////			BufferedImage image2 = c2.createImage();
			//			BufferedImage image3 = e.createImage();
			//			try {
			////				ImageIO.write(image1, "png", new File("output-result" + i + ".png"));
			////				ImageIO.write(image2, "png", new File("output-target" + i + ".png"));
			//				ImageIO.write(image3, "png", new File("output-accum" + i + ".png"));
			//			} catch (IOException ex) {
			//				ex.printStackTrace();
			//			}		



			if (candidates.size() == 1){
				break;
			}
		}

		return Lists.newArrayList();
	}



}