package org.sikuli.api.examples;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.sikuli.api.*;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.visual.ScreenPainter;
import org.sikuli.core.cv.DiffFinder;
import org.sikuli.core.cv.ImagePreprocessor;
import org.sikuli.core.logging.ImageExplainer;
import org.sikuli.core.logging.ImageExplainer.Level;
import org.sikuli.core.search.RegionMatch;
import org.sikuli.core.search.TemplateMatchingUtilities;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

import static com.googlecode.javacv.cpp.opencv_core.cvConvertScale;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static org.sikuli.api.API.*;

class StaticImageScreen implements Screen {
	
	static private BufferedImage crop(BufferedImage src, int x, int y, int width, int height){
	    BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	    Graphics g = dest.getGraphics();
	    g.drawImage(src, 0, 0, width, height, x, y, x + width, y + height, null);
	    g.dispose();
	    return dest;
	}

	
	final private BufferedImage image;
	public StaticImageScreen(BufferedImage image){
		this.image = image;
	}
	
	@Override
	public BufferedImage getScreenshot(int x, int y, int width, int height) {
		BufferedImage regionImage = crop(image, x,y,width,height);
		return regionImage;
	}

	@Override
	public Dimension getSize(){
		return new Dimension(image.getWidth(),image.getHeight());
	}
}

public class CopyOfHelloWorldExample {
	
	static BufferedImage visualizeResultMatrix(IplImage result){
		IplImage resultToShow = IplImage.create(cvGetSize(result), 8, 1);		
		cvConvertScale(result, resultToShow, 255, 0);
		return resultToShow.getBufferedImage();
	}
	
	
	public static void imageTarget() throws IOException{
		ImageExplainer.getExplainer(DiffFinder.class).setLevel(Level.ALL);
		
		DiffFinder d = new DiffFinder();
		BufferedImage s1 = ImageIO.read(new File("/Users/tomyeh/Desktop/s1.png"));
		BufferedImage s2 = ImageIO.read(new File("/Users/tomyeh/Desktop/s2.png"));
		d.diff(s1, s2);
		
		//BufferedImage ice = ImageIO.read(new File("/Users/tomyeh/Desktop/icecream.png"));
		
		//Target target = new ImageTarget(new File("targetImage.png"));
		//Target target = new ImageTarget(new URL("http://www.mysite.com/targetImage.png"));
		
		
		//BufferedImage bigImage = ImageIO.read(new File("targetImage.png"));
		//BufferedImage smallImage = bigImage.getSubimage(50,50,100,100);
		//Target target = new ImageTarget(smallImage);
		
		
	}

	
	public static void main(String[] args) throws IOException {
		imageTarget();
	
//		ScreenRegion s = new ScreenRegion();
//	
//		BufferedImage arrow = ImageIO.read(new File("/Users/tomyeh/Desktop/arrow.png"));
//		BufferedImage ice = ImageIO.read(new File("/Users/tomyeh/Desktop/icecream.png"));
//		Target target = new ImageTarget(ice);
//		ScreenRegion r = s.find(target);
//		ScreenPainter painter = new ScreenPainter();		
////		painter.box(r,  3000);
////		painter.label(r, "CLICK HERE", 3000);
////		
//		
//	
//		for (int i=0;i<5;++i){				
//			r = s.find(target);	
//			painter.box(r,  100);
//			painter.label(r, "ICECREAM", 100);			
//			API.pause(100);
//		}
//	
//		
//		
		
		//BufferedImage five = ImageIO.read(new File("/Users/tomyeh/Desktop/five.png"));
//		//BufferedImage five = ImageIO.read(new File("/Users/tomyeh/Desktop/equal.png"));
//		BufferedImage five = ImageIO.read(new File("/Users/tomyeh/Desktop/zero.png"));
//		BufferedImage calculator = ImageIO.read(new File("/Users/tomyeh/Desktop/calculator.png"));
//		Target target = new ImageTarget(five);
//		
//		
//				
//		IplImage input1 = ImagePreprocessor.createGrayscale(calculator);
//		IplImage target1 = ImagePreprocessor.createGrayscale(five);
//				
//		IplImage resultMatrix = TemplateMatchingUtilities.computeTemplateMatchResultMatrix(input1, target1);
//		
//		
//		ScreenRegion ss = new ScreenRegion();
//		ss.setScreen(new StaticImageScreen(calculator));
//		//StaticImageScreen ss = new StaticImageScreen(calculator);
//		
//		
//		ImageExplainer.getExplainer(VisualModelFinder.class).setLevel(Level.STEP);
//		
//		StyledRectangleTarget st = new StyledRectangleTarget(five);
//		st.setLimit(20);
//		List<ScreenRegion> rs = ss.findAll(st);
//		System.out.println(rs.size());
		  
//		BufferedImage exampleImage = five;
//		FourCornerModel buttonModel = FourCornerModel.learnFrom(exampleImage);
//		List<RegionMatch> matches = VisualModelFinder.searchButton(buttonModel, 
//				screenRegion.capture());	
//		List<RegionMatch> subList = matches.subList(0,  Math.min(getLimit(), matches.size()));

		
		
		//PiccoloImageRenderer r = new 
		//ImageIO.write(visualizeResultMatrix(resultMatrix), "png", new File("test.png"));
		
		
		
//		ScreenRegion r = s.find(target);
//		
//		ScreenPainter painter = new ScreenPainter();
//		painter.box(r,  3000);
		
//		List<ScreenRegion> rs = s.find(target);
//				
//		ScreenPainter painter = new ScreenPainter();
//		for (ScreenRegion r : rs){
//			painter.box(r,  3000);
//		}

		//painter.label(s, "Sikuli Will Look Only Here/", 3000);
				
	}
	
	static void example1() throws MalformedURLException{
		ScreenRegion s = new ScreenRegion();
		ScreenRegion autoCompleteText = s.find(new ImageTarget(new URL("https://dl.dropbox.com/u/5104407/SikuliAPIWikiImages/screenRegion/relative_landmark.png")));
		ScreenRegion right = autoCompleteText.getRight(150);		
		
		ScreenPainter painter = new ScreenPainter();
		painter.box(autoCompleteText,  3000);
		painter.box(right,  3000);

	}
}