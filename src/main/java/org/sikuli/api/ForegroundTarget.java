package org.sikuli.api;

import java.awt.image.BufferedImage;
import java.util.List;

import org.sikuli.core.cv.VisionUtils;

import com.google.common.collect.Lists;
import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class ForegroundTarget extends DefaultTarget implements Target {

	public ForegroundTarget() {
	}

	@Override
	protected List<ScreenRegion> getUnordredMatches(ScreenRegion screenRegion) {
		
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