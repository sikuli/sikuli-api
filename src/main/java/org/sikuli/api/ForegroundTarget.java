package org.sikuli.api;

import java.awt.image.BufferedImage;
import java.util.List;

import org.sikuli.core.cv.VisionUtils;

import com.google.common.collect.Lists;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import org.bytedeco.javacv.*;
import org.bytedeco.javacpp.*;

/**
 * The ForegroundTarget class defines targets based on whether they have 
 * textures (as opposed to textureless background). This class can be used to distinguish between foreground targets and 
 * the background target. <p>
 * For example, if you have a solid black background image that contains a small white square, you can use 
 * this ForegroundTarget class to find the white square in the black background image without having to specify the image of the  
 * white square the target.
 *
 */
public class ForegroundTarget extends DefaultTarget implements Target {
	/**
	 * Constructs a new ForegroundTarget with default values.
	 */
	public ForegroundTarget() {
	}

	@Override
	protected List<ScreenRegion> getUnorderedMatches(ScreenRegion screenRegion) {
		
		BufferedImage image = screenRegion.capture();
		IplImage foregroundMask = VisionUtils.computeForegroundMaskOf(IplImage.createFrom(image));
		List<CvRect> blobs = VisionUtils.detectBlobs(foregroundMask);
		
		List<ScreenRegion> results = Lists.newArrayList();
		for (CvRect b : blobs){
			System.out.println(String.format("%d,%d,%d,%d",b.x(),b.y(),b.width(),b.height()));
			ScreenRegion r = screenRegion.getRelativeScreenRegion(b.x(), b.y(), b.width(), b.height());
			results.add(r);
		}		
		return results;
	}

}