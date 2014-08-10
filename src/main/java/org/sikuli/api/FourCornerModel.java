package org.sikuli.api;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.google.common.collect.Lists;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PPath;

class FourCornerModel extends VisualModel {
		
	static final double MIN_ASPECT_RATIO = 0.20f;
	
	static public FourCornerModel learnFrom(BufferedImage trainingImage){
		FourCornerModel model = new FourCornerModel();
		
//		VisualModelFinder.logger.step(trainingImage, "original model image");
		//BufferedImage trimmedModelImage = VisionUtils.getSubimageInsideMargin(trainingImage);
		//Rectangle inside = new R
//		Rectangle inside = VisionUtils.getRegionInsideMargin(trainingImage);
//		inside.grow(2,2);
//		inside.x = Math.max(0, inside.x);
//		inside.y = Math.max(0, inside.y);
//		inside.y = Math.min(in, inside.y);
//		inside.y = Math.min(0, inside.y);
		BufferedImage trimmedModelImage = trainingImage;
		//BufferedImage trimmedModelImage = trainingImage.getSubimage(inside.x,inside.y,inside.width,inside.height);
		
//		VisualModelFinder.logger.step(trimmedModelImage, "margin trimmed");

		final int d = 12;			
		int w = trimmedModelImage.getWidth();
		int h = trimmedModelImage.getHeight();			
		BufferedImage s = trimmedModelImage;			
		model.topLeft = new ModelPart(new Rectangle(0,0,d,d), s);
		model.topRight = new ModelPart(new Rectangle(w-d,0,d,d), s);
		model.bottomRight = new ModelPart(new Rectangle(w-d,h-d,d,d), s);
		model.bottomLeft = new ModelPart(new Rectangle(0,h-d,d,d), s);
					
		final List<ModelPart> parts = Lists.newArrayList(model.topLeft,model.topRight,model.bottomRight,model.bottomLeft);
		
//		ImageRenderer ir = new PiccoloImageRenderer(trimmedModelImage){
//			@Override
//			protected void addContent(PLayer layer) {
//				for (ModelPart part : parts){					
//					PPath p = PPath.createRectangle(0,0,d,d);
//					p.setStrokePaint(Color.red);
//					p.setStroke(new BasicStroke(2f));
//					p.setOffset(part.getBounds().getLocation());
//					layer.addChild(p);
//				}
//			}			
//		};		
//		VisualModelFinder.logger.step(ir, "parts");
		
		return model;
	}
	
	public ModelPart getTopLeft() {
		return topLeft;
	}

	public ModelPart getBottomLeft() {
		return bottomLeft;
	}

	public ModelPart getTopRight() {
		return topRight;
	}

	public ModelPart getBottomRight() {
		return bottomRight;
	}

	private ModelPart topLeft;
	private ModelPart bottomLeft;
	private ModelPart topRight;
	private ModelPart bottomRight;
}