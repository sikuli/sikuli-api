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

	private static final int MAX_FONT_SIZE = 14;
	private static final int MIN_FONT_SIZE = 9;
	static private ImageExplainer explainer = ImageExplainer.getExplainer(TextTarget.class);
	static private Logger logger = LoggerFactory.getLogger(TextTarget.class);

	final private String text;

	public TextTarget(String text) {
		this.text = text;
	}

	static class WeightedFontModel extends FontModel {
		int weight = 0;
		double maxScore = 0;

		public String toString() {
			return "weight:" + weight + ", maxScore: " + maxScore + ","
					+ super.toString();
		}
	}

	static List<WeightedFontModel> fontModels = Lists.newArrayList();
	static {
		Font[] fonts = { Fonts.SEGOE_UI_12PT,
				Fonts.WINDOWS_XP_120DPI_DEFAULT_GUI,
				new Font("sansserif", 0, 0), new Font("serif", 0, 0) };
		for (Font font : fonts) {
			for (double size = MIN_FONT_SIZE; size <= MAX_FONT_SIZE; size = size + 1) {
				for (double tracking = 0; tracking > -0.03; tracking = tracking - 0.01) {
					WeightedFontModel fontModel = new WeightedFontModel();
					fontModel.setFont(font);
					fontModel.setTracking(tracking);
					fontModel.setSize(size);
					fontModels.add(fontModel);
				}
			}
		}
	}

	static private List<TextMatch> findCandidateMatches(ScreenRegion screenRegion,
			String word, double minScore, boolean firstMatchOnly) {

		ScreenRegion snapshot = screenRegion.snapshot();
		TextMap map = TextMap.createFrom(snapshot.capture());		
		explainer.step(map.getImage(), "text map");

		List<TextMatch> ret = Lists.newArrayList();
		for (WeightedFontModel fontModel : fontModels) {
			logger.trace("test font model => " + fontModel);
			BufferedImage img = TextImageGenerator.create(word,
					fontModel.getFont(), fontModel.getSize(),
					fontModel.getTracking());
			ImageTarget t = new ImageTarget(img);
			t.setMinScore(minScore);
			List<ScreenRegion> rs = snapshot.findAll(t);

			if (!rs.isEmpty()) {

				logger.trace("top score = " + rs.get(0).getScore());
				for (ScreenRegion r : rs) {
					int localx = r.x - snapshot.x;
					int localy = r.y - snapshot.y;
					if (map.computeTextScore(localx, localy, r.width, r.height) > 0) {
						TextMatch m = new TextMatch(r, fontModel);
						ret.add(m);

						fontModel.maxScore = Math.max(fontModel.maxScore,
								r.getScore());
					}
				}

				double quickAcceptThreshold = Math.max(0.65,
						fontModel.maxScore * 0.85);
				if (firstMatchOnly
						&& rs.get(0).getScore() >= quickAcceptThreshold) {
					return ret;
				}

			}
		}
		return ret;
	}

	static class TextMatch {
		ScreenRegion screenRegion;
		WeightedFontModel fontModel;

		public TextMatch(ScreenRegion screenRegion, WeightedFontModel fontModel) {
			super();
			this.screenRegion = screenRegion;
			this.fontModel = fontModel;
		}
	}

	static List<TextMatch> removeOverlappedMatches(List<TextMatch> candidateMatches) {
		List<TextMatch> filteredCandidateMatches = Lists.newArrayList();
		for (TextMatch m1 : candidateMatches) {

			final ScreenRegion s1 = m1.screenRegion;
			final Rectangle r1 = new Rectangle(s1.x, s1.y, s1.width, s1.height);
			boolean isOverlapping = Iterables.any(filteredCandidateMatches,
					new Predicate<TextMatch>() {
						@Override
						public boolean apply(TextMatch m2) {
							ScreenRegion s2 = m2.screenRegion;
							Rectangle r2 = new Rectangle(s2.x, s2.y, s2.width,
									s2.height);
							return r1.intersects(r2);
						}
					});

			if (!isOverlapping) {
				filteredCandidateMatches.add(m1);
			}
		}
		return filteredCandidateMatches;
	}

	static ImageRenderer visualize(BufferedImage image,
			final List<TextMatch> matches) {
		ImageRenderer a = new PiccoloImageRenderer(image) {

			@Override
			protected void addContent(PLayer layer) {
				for (int i = 0; i < matches.size(); ++i) {
					if (i > 1)
						continue;
					ScreenRegion r = matches.get(i).screenRegion;
					PPath p = PPath
							.createRectangle(r.x, r.y, r.width, r.height);

					// if (map.computeTextScore(r.x,r.y,r.width,r.height) > 0)
					if (i == 0)
						p.setStrokePaint(Color.red);
					else
						p.setStrokePaint(Color.blue);
					p.setPaint(null);
					PText t = new PText("" + i);
					t.setOffset(r.x, r.y);
					layer.addChild(p);
					layer.addChild(t);
				}
			}
		};
		return a;
	}

	List<TextMatch> findMatches(ScreenRegion screenRegion, String text) {

		logger.debug("find matches for [" + text + "]");

		ScreenRegion snapshot = screenRegion.snapshot();

		List<TextMatch> candidateMatches = findCandidateMatches(snapshot, text,
				getMinScore(), true);

		sortCandidateMatchesByScore(candidateMatches);

		candidateMatches = removeOverlappedMatches(candidateMatches);

		updateFontModelWeights(candidateMatches);

		sortFontModelsByWeight();

		explainer.step(visualize(snapshot.capture(), candidateMatches),
				"matches for <" + text + ">");

		return candidateMatches;
	}

	static private void sortFontModelsByWeight() {
		Collections.sort(fontModels, new Comparator<WeightedFontModel>() {
			@Override
			public int compare(WeightedFontModel m0, WeightedFontModel m1) {
				return m1.weight - m0.weight;
			}
		});
	}

	static private void updateFontModelWeights(List<TextMatch> candidateMatches) {
		int w = candidateMatches.size();
		for (TextMatch m : candidateMatches) {
			m.fontModel.weight += w;
			w--;
		}
	}

	static private void sortCandidateMatchesByScore(List<TextMatch> candidateMatches) {
		Collections.sort(candidateMatches, new Comparator<TextMatch>() {
			@Override
			public int compare(TextMatch m0, TextMatch m1) {
				return Double.compare(m1.screenRegion.getScore(),
						m0.screenRegion.getScore());
			}
		});
	}

	 static private List<ScreenRegion> covertToScreenRegions(ScreenRegion parent, List<TextMatch> matches){
		 List<ScreenRegion> ret = Lists.newArrayList();
		 for (TextMatch m : matches){
			 ScreenRegion rm = m.screenRegion;
			 rm.setScreen(parent.getScreen());
			 ret.add(rm);
		 }
		 return ret;
	 }

	protected List<ScreenRegion> getUnordredMatches(ScreenRegion screenRegion) {
		List<TextMatch> matches = findMatches(screenRegion, text);
		return covertToScreenRegions(screenRegion, matches);
	}

	public String toString() {
		return "[StringTarget: " + text + "]";
	}

}

class TextImageGenerator {

	static BufferedImage create(String text, Font font, double size,
			double tracking) {
		Font f = font.deriveFont((float) size);
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