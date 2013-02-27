package org.sikuli.api;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.sikuli.core.draw.ImageRenderer;
import org.sikuli.core.draw.PiccoloImageRenderer;
import org.sikuli.core.logging.ImageExplainer;
import org.sikuli.core.search.ImageSearcher;
import org.sikuli.core.search.RegionMatch;
import org.sikuli.core.search.algorithm.TemplateMatcher;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PPath;

public class VisualModelFinder {
	
	final static ImageExplainer logger = ImageExplainer.getExplainer(VisualModelFinder.class); 
	
	static class ModelPartMatch {
		public ModelPartMatch(ModelPart modelPart, RegionMatch scoreMatch) {
			super();
			this.modelPart = modelPart;
			this.scoreMatch = scoreMatch;
		}
		final private ModelPart modelPart;
		final private RegionMatch scoreMatch;
		public RegionMatch getScoreMatch() {
			return scoreMatch;
		}
		public ModelPart getModelPart() {
			return modelPart;
		}
	}
	
	static class MatchHypothesis {		
		public MatchHypothesis(ModelPartMatch topLeft,
				ModelPartMatch bottomRight) {
			super();
			this.topLeft = topLeft;
			this.bottomRight = bottomRight;
		}
		public ModelPartMatch getTopLeft() {
			return topLeft;
		}
		public ModelPartMatch getBottomRight() {
			return bottomRight;
		}
		
		public Point getLocation(){
			return topLeft.scoreMatch.getBounds().getLocation();
		}
		
		public boolean isValid(){
			boolean isLeftRight = topLeft.getScoreMatch().getX() < bottomRight.getScoreMatch().getX();
			boolean isTopDown   = topLeft.getScoreMatch().getY() < bottomRight.getScoreMatch().getY();
			
			int modelHeight = bottomRight.getModelPart().getBounds().y - topLeft.getModelPart().getBounds().y;
			int hypothesisHeight = bottomRight.getScoreMatch().getY() - topLeft.getScoreMatch().getY(); 
			boolean isHeightSimilar =  Math.abs(hypothesisHeight - modelHeight) < 5;
			return isHeightSimilar && isLeftRight && isTopDown;
		}		
		public void setTopRight(ModelPartMatch topRight) {
			this.topRight = topRight;
		}
		public ModelPartMatch getTopRight() {
			return topRight;
		}
		public void setBottomLeft(ModelPartMatch bottomLeft) {
			this.bottomLeft = bottomLeft;
		}
		public ModelPartMatch getBottomLeft() {
			return bottomLeft;
		}
		
		public Point getExpectedTopRightPartModelLocation(){
			int x = bottomRight.getScoreMatch().getBounds().x;
			int y = topLeft.getScoreMatch().getBounds().y;
			return new Point(x,y);
		}
		
		public Point getExpectedBottomLeftPartModelLocation(){
			int x = topLeft.getScoreMatch().getBounds().x;
			int y = bottomRight.getScoreMatch().getBounds().y;
			return new Point(x,y);
		}

		
		public int getScore(){
			int score = 2;
			if (bottomLeft != null)
				score += 1;
			if (topRight != null)
				score += 1;
			return score;
		}
		

		final private ModelPartMatch topLeft;
		final private ModelPartMatch bottomRight;
		
		private ModelPartMatch topRight;
		private ModelPartMatch bottomLeft;
		
		public Rectangle getBounds() {
			return new Rectangle(getLocation(),getSize());  
		}
		
		public Dimension getSize(){
			return new Dimension(
			bottomRight.getScoreMatch().getX() + bottomRight.getScoreMatch().getWidth() - topLeft.getScoreMatch().getX(),
			bottomRight.getScoreMatch().getY() + bottomRight.getScoreMatch().getHeight() - topLeft.getScoreMatch().getY());						
		}
		
	}
		
	public static List<RegionMatch> searchButton(FourCornerModel model, BufferedImage testImage){	
		
		ImageSearcher search = new ImageSearcher(testImage);	
		
		
		double minSimilarity = 0.7;
		int numMatches = 40;
		
		final List<RegionMatch> tms1 = 
				TemplateMatcher.findMatchesByGrayscaleAtOriginalResolution(testImage, model.getTopLeft().getImage(), numMatches, minSimilarity);

		final List<RegionMatch> tms3 = 
				TemplateMatcher.findMatchesByGrayscaleAtOriginalResolution(testImage, model.getBottomRight().getImage(), numMatches, minSimilarity);

		final List<RegionMatch> tms2 = 
				TemplateMatcher.findMatchesByGrayscaleAtOriginalResolution(testImage, model.getTopRight().getImage(), numMatches, minSimilarity);

		final List<RegionMatch> tms4 = 
				TemplateMatcher.findMatchesByGrayscaleAtOriginalResolution(testImage, model.getBottomLeft().getImage(), numMatches, minSimilarity);
		
		ImageRenderer matchedPartsRenderer = new PiccoloImageRenderer(testImage){
			@Override
			protected void addContent(PLayer layer) {
				for (List<RegionMatch> tmss : Lists.newArrayList(tms1,tms2,tms3,tms4)){
					for (RegionMatch tms : tmss){
						PPath c = PPath.createRectangle(tms.getX(),tms.getY(),tms.getWidth(),tms.getHeight());
						c.setStroke(new BasicStroke(2f));
						c.setStrokePaint(Color.blue);
						c.setTransparency(0.5f);
						layer.addChild(c);
					}				
				}
			}			
		};		
		logger.step(matchedPartsRenderer, "matched parts");
		
		// generate hypotheses
		final List<MatchHypothesis> hypotheses = Lists.newArrayList();
		for (RegionMatch scoreMatch1 : tms1){
			for (RegionMatch scoreMatch3 : tms3){
				
				ModelPartMatch m1 = new ModelPartMatch(model.getTopLeft(), scoreMatch1);
				ModelPartMatch m2 = new ModelPartMatch(model.getBottomRight(), scoreMatch3);
				
				MatchHypothesis newHypothesis = new MatchHypothesis(m1,m2);
				if (newHypothesis.isValid()){
					hypotheses.add(newHypothesis);
				}
			}			
		}
		
		ImageRenderer hypothesesRenderer = new PiccoloImageRenderer(testImage){
			@Override
			protected void addContent(PLayer layer) {
				for (MatchHypothesis h : hypotheses){
					ModelPartMatch m1 = h.getTopLeft();
					ModelPartMatch m2 = h.getBottomRight();
					RegionMatch p1 = m1.getScoreMatch();
					RegionMatch p2 = m2.getScoreMatch();
					PPath line = PPath.createLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());
					line.setStroke(new BasicStroke(2f));
					line.setStrokePaint(Color.red);
					layer.addChild(line);
					
					
					Point p = h.getExpectedBottomLeftPartModelLocation();
					PPath c = PPath.createRectangle(p.x,p.y,10,10);
					c.setStroke(new BasicStroke(2f));
					c.setStrokePaint(Color.green);
					layer.addChild(c);
					
					p = h.getExpectedTopRightPartModelLocation();
					c = PPath.createRectangle(p.x,p.y,10,10);
					c.setStroke(new BasicStroke(2f));
					c.setStrokePaint(Color.green);
					layer.addChild(c);
				}				
			}			
		};
		logger.step(hypothesesRenderer, "hypotheses");
					
		for (MatchHypothesis h1 : hypotheses){
						
			Point expectedLocation = h1.getExpectedTopRightPartModelLocation();
			
			// find if there is a matched part in the expected location
			// according to this hypothesis
			
			for (RegionMatch s2 : tms2){			
				Point seenLocation = s2.getLocation();
				boolean isMatchedPartSeenNearbyExpectedLocation = seenLocation.distance(expectedLocation.x,expectedLocation.y) < 5.0f;
				if (isMatchedPartSeenNearbyExpectedLocation){					
					h1.setTopRight(new ModelPartMatch(model.getTopRight(),s2));					
					break;
				}
			}
			
			expectedLocation = h1.getExpectedBottomLeftPartModelLocation();
				
			for (RegionMatch s4 : tms4){
				Point seenLocation = s4.getLocation();
				boolean isMatchedPartSeenNearbyExpectedLocation = seenLocation.distance(expectedLocation.x, expectedLocation.y) < 5.0f;
				if (isMatchedPartSeenNearbyExpectedLocation){					
					h1.setBottomLeft(new ModelPartMatch(model.getBottomLeft(),s4));					
					break;
				}
			}
		}		
		
		logger.step(new MatchHypotheseRenderer(testImage, hypotheses), "hypotheses + other matched parts");
		//hypotheses.add(newHypothesis);
		
		// keep good hypotheses
		List<MatchHypothesis> goodHypotheses = Lists.newArrayList();
		for (MatchHypothesis h1 : hypotheses){
			if (h1.getScore() == 4){
				goodHypotheses.add(h1);
			}
		}
		
		// sort hypotheses by increasing sizes
		Collections.sort(goodHypotheses, new Comparator<MatchHypothesis>(){
			public int compare(MatchHypothesis arg0, MatchHypothesis arg1) {
				return arg0.getBounds().width * arg0.getBounds().height - 
				arg1.getBounds().width * arg1.getBounds().height;
			}
		});
		
		
		Map<RegionMatch,Integer> alreadyUsedMatch = Maps.newHashMap();
				
		// remove overlapping hypotheses
		List<MatchHypothesis> nonOverlappingGoodHypotheses = Lists.newArrayList();
		for (MatchHypothesis h1 : goodHypotheses){
			
			if (alreadyUsedMatch.containsKey(h1.getTopLeft().getScoreMatch()) ||
					alreadyUsedMatch.containsKey(h1.getBottomLeft().getScoreMatch()) ||
					alreadyUsedMatch.containsKey(h1.getTopRight().getScoreMatch()) ||
					alreadyUsedMatch.containsKey(h1.getBottomRight().getScoreMatch())){
				
				// ignore
								
			}else{
				nonOverlappingGoodHypotheses.add(h1);
				alreadyUsedMatch.put(h1.getTopLeft().getScoreMatch(), 1);
				alreadyUsedMatch.put(h1.getBottomLeft().getScoreMatch(), 1);
				alreadyUsedMatch.put(h1.getTopRight().getScoreMatch(), 1);
				alreadyUsedMatch.put(h1.getBottomRight().getScoreMatch(), 1);
			}
		}
		logger.step(new MatchHypotheseRenderer(testImage, nonOverlappingGoodHypotheses), "non-overlapping good hypotheses");
		
		
		
		// collect the list of ScoreRegionMatch objects to return
		List<RegionMatch> matches = Lists.newArrayList();
		for (MatchHypothesis h1 : nonOverlappingGoodHypotheses){
			RegionMatch regionMatch = new RegionMatch(h1.getBounds());
			matches.add(regionMatch);
		}		
		return matches;		
	}
	
	static class MatchHypotheseRenderer extends PiccoloImageRenderer implements ImageRenderer {
		
		private final List<MatchHypothesis> hypotheses;
		MatchHypotheseRenderer(BufferedImage testImage, List<MatchHypothesis> hypotheses){
			super(testImage);
			this.hypotheses = hypotheses;
		}
		
		@Override
		protected void addContent(PLayer layer) {
			for (MatchHypothesis h : hypotheses){
				ModelPartMatch m1 = h.getTopLeft();
				ModelPartMatch m2 = h.getBottomRight();
				RegionMatch p1 = m1.getScoreMatch();
				RegionMatch p2 = m2.getScoreMatch();
				
				// draw diagonal line				
//				PPath line = PPath.createLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());
//				line.setStroke(new BasicStroke(2f));
//				line.setStrokePaint(Color.red);
//				layer.addChild(line);
				
				
				Rectangle bs = h.getBounds();
				PPath rect = PPath.createRectangle(bs.x,bs.y,bs.width,bs.height);
				rect.setStroke(new BasicStroke(2f));
				rect.setStrokePaint(Color.red);
				rect.setPaint(null);
				layer.addChild(rect);		
				
				// draw score
//				int score = h.getScore();
//				PText text = new PText(""+score);
//				text.setOffset(rect.getBounds().getCenterX(), rect.getBounds().y - 20);
//				text.setHorizontalAlignment(0.5f);
//				layer.addChild(text);
				
				ModelPartMatch m3 = h.getTopRight();
				ModelPartMatch m4 = h.getBottomLeft();
				
				if (m3 != null){
					RegionMatch p3 = m3.getScoreMatch();
					PPath c = PPath.createRectangle(p3.getX(),p3.getY(),p3.getWidth(),p3.getHeight());
					c.setStroke(new BasicStroke(2f));
					c.setStrokePaint(Color.blue);
					c.setTransparency(0.5f);
					layer.addChild(c);
				}
				
				if (m4 != null){
					RegionMatch p4 = m4.getScoreMatch();
					PPath c = PPath.createRectangle(p4.getX(),p4.getY(),p4.getWidth(),p4.getHeight());
					c.setStroke(new BasicStroke(2f));
					c.setStrokePaint(Color.green);
					c.setTransparency(0.5f);
					layer.addChild(c);
				}
			}				
		}			
	}
}

class VisualModel {
	
}

class ModelPart {
	final private Rectangle bounds;
	final private BufferedImage image;

	public ModelPart(Rectangle bounds, BufferedImage sourceImage) {
		super();
		this.bounds = bounds;
		this.image = sourceImage.getSubimage(bounds.x,bounds.y,bounds.width,bounds.height);
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	public BufferedImage getImage(){
		return image;
	}
}
