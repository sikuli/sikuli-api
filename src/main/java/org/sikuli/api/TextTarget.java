package org.sikuli.api;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sikuli.core.cv.TextMap;
import org.sikuli.core.draw.ImageRenderer;
import org.sikuli.core.draw.PiccoloImageRenderer;
import org.sikuli.core.logging.ImageExplainer;
import org.sikuli.ocr.FontModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.jgoodies.looks.Fonts;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;

public class TextTarget extends Target {

	static private ImageExplainer explainer = ImageExplainer.getExplainer(TextTarget.class);
	static private Logger logger = LoggerFactory.getLogger(TextTarget.class);

	final private String text;
	public TextTarget(String text){
		this.text = text;
	}	

	static class WeightedFontModel extends FontModel {
		int weight = 0;
		double maxScore = 0;
		public String toString(){
			return "weight:" + weight + ", maxScore: " + maxScore + "," + super.toString();
		}
	}

	static List<WeightedFontModel> fontModels = Lists.newArrayList();
	static {
		Font[] fonts = {Fonts.SEGOE_UI_12PT
				,Fonts.WINDOWS_XP_120DPI_DEFAULT_GUI
				,new Font("sansserif",0,0)
				,new Font("serif",0,0)
		};		
		for (Font font : fonts){
			for (double size = 9 ; size <= 14; size = size + 1){
				for (double tracking = 0; tracking > -0.03; tracking = tracking - 0.01){
					WeightedFontModel fontModel = new WeightedFontModel();
					fontModel.setFont(font);
					fontModel.setTracking(tracking);
					fontModel.setSize(size);
					fontModels.add(fontModel);
				}
			}
		}
	}

	static private List<Match> findCandidateMatches(ScreenRegion screenRegion, 
			String word, double minScore, boolean firstMatchOnly){

		ScreenRegion snapshot = screenRegion.snapshot();
		TextMap map = TextMap.createFrom(snapshot.capture());

		List<Match> ret = Lists.newArrayList();
		for (WeightedFontModel fontModel : fontModels){
			logger.trace("test font model => " + fontModel);
			BufferedImage img = TextImageGenerator.create(word, 
					fontModel.getFont(),
					fontModel.getSize(), fontModel.getTracking());
			ImageTarget t = new ImageTarget(img);
			t.setMinScore(minScore);
			List<ScreenRegion> rs = snapshot.findAll(t);

			if (!rs.isEmpty()){

				logger.trace("top score = " + rs.get(0).getScore());
				for (ScreenRegion r : rs){					
					int localx = r.x - snapshot.x;
					int localy = r.y - snapshot.y;
					if (map.computeTextScore(localx, localy, r.width, r.height) > 0){
						Match m = new Match(r, fontModel);
						ret.add(m);
						
						fontModel.maxScore = Math.max(fontModel.maxScore, r.getScore());

					}
				}

				double quickAcceptThreshold = Math.max(0.65, fontModel.maxScore * 0.85);
				if (firstMatchOnly && rs.get(0).getScore() >= quickAcceptThreshold){
					return ret;
				}

			}
		}
		return ret;
	}


	static class Match {
		ScreenRegion screenRegion;
		WeightedFontModel fontModel;
		public Match(ScreenRegion screenRegion, WeightedFontModel fontModel) {
			super();
			this.screenRegion = screenRegion;
			this.fontModel = fontModel;
		}		
	}

	static List<Match> removeOverlappedMatches(List<Match> candidateMatches){
		List<Match> filteredCandidateMatches = Lists.newArrayList();
		for (Match m1 : candidateMatches){

			final ScreenRegion s1 = m1.screenRegion;		
			final Rectangle r1 = new Rectangle(s1.x,s1.y,s1.width,s1.height);						
			boolean isOverlapping = Iterables.any(filteredCandidateMatches, new Predicate<Match>(){
				@Override
				public boolean apply(Match m2) {
					ScreenRegion s2 = m2.screenRegion;
					Rectangle r2 = new Rectangle(s2.x,s2.y,s2.width,s2.height);
					return r1.intersects(r2);
				}				
			});			

			if (!isOverlapping){
				filteredCandidateMatches.add(m1);
			}		
		}
		return filteredCandidateMatches; 
	}

	static ImageRenderer visualize(BufferedImage image, final List<Match> matches){
		ImageRenderer a = new PiccoloImageRenderer(image){

			@Override
			protected void addContent(PLayer layer) {				
				for (int i = 0; i < matches.size(); ++i){
					if (i>1)
						continue;
					ScreenRegion r = matches.get(i).screenRegion;
					PPath p = PPath.createRectangle(r.x, r.y, r.width, r.height);

					//					if (map.computeTextScore(r.x,r.y,r.width,r.height) > 0)
					if (i==0)
						p.setStrokePaint(Color.red);
					else
						p.setStrokePaint(Color.blue);
					p.setPaint(null);
					PText t = new PText(""+i);
					t.setOffset(r.x, r.y);
					layer.addChild(p);
					layer.addChild(t);
				}				
			}
		};
		return a;
	}

	List<Match> findMatches(ScreenRegion screenRegion, String text){
		
		logger.debug("find matches for [" + text + "]");

		ScreenRegion snapshot = screenRegion.snapshot();

		List<Match> candidateMatches = findCandidateMatches(snapshot, text, getMinScore(), true);		

		sortCandidateMatchesByScore(candidateMatches);

		candidateMatches = removeOverlappedMatches(candidateMatches);
		
		updateFontModelWeights(candidateMatches);
		
		sortFontModelsByWeight();

		explainer.step(visualize(snapshot.capture(), candidateMatches), "matches for <" + text + ">");

		return candidateMatches;
	}

	static private void sortFontModelsByWeight() {
		Collections.sort(fontModels, new Comparator<WeightedFontModel>(){
			@Override
			public int compare(WeightedFontModel m0, WeightedFontModel m1) {
				return m1.weight - m0.weight;
			}		
		});
	}

	static private void updateFontModelWeights(List<Match> candidateMatches) {
		int w = candidateMatches.size();
		for (Match m : candidateMatches){
			m.fontModel.weight += w;
			w--;
		}
	}

	static private void sortCandidateMatchesByScore(List<Match> candidateMatches) {
		Collections.sort(candidateMatches, new Comparator<Match>(){
			@Override
			public int compare(Match m0, Match m1) {
				return Double.compare(m1.screenRegion.getScore(),
						m0.screenRegion.getScore());
			}		
		});
	}
	
	static public List<ScreenRegion> covertToScreenRegions(List<Match> matches){
		List<ScreenRegion> ret = Lists.newArrayList();
		for (Match m : matches){
			ret.add(m.screenRegion);
		}
		return ret;
	}

	protected List<ScreenRegion> getUnordredMatches(ScreenRegion screenRegion){
		List<Match> matches = findMatches(screenRegion, text);
		return covertToScreenRegions(matches);
	}

	public String toString(){
		return "[StringTarget: " + text + "]"; 
	}

}

class TextImageGenerator {

	static BufferedImage create(String text, Font font, double size, double tracking){
		Font f = font.deriveFont((float)size);
		//		Font f = font;
		Map<TextAttribute, Serializable> textAttributes = new HashMap<TextAttribute, Serializable>();
		textAttributes.put(TextAttribute.KERNING, TextAttribute.KERNING_ON);
		textAttributes.put(TextAttribute.TRACKING, tracking);
		textAttributes.put(TextAttribute.FONT, f);
		textAttributes.put(TextAttribute.SIZE, size);
		f = f.deriveFont(textAttributes);

		PText p = new PText(text);
		p.setFont(f);
		return (BufferedImage) p.toImage();
	}
}