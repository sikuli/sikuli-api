package org.sikuli.api.visual;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.desktop.DesktopScreen;
import org.sikuli.core.cv.VisionUtils;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PImage;

public class ScreenRegionCanvas extends ScreenDisplayableDrawingCanvas {
	
	private ScreenRegion canvasScreenRegion;

	public ScreenRegionCanvas(ScreenRegion screenRegion){
		this.canvasScreenRegion = screenRegion;
	}

	@Override
	protected ScreenDisplayable createScreenDisplayable(Element element) {
		if (element instanceof BoxElement){
			Rectangle b = ((DesktopScreen) canvasScreenRegion.getScreen()).getBounds();
			Element r = element;			
			return new BoxOverlay(b.x+r.x, b.y+r.y, r.width, r.height);				
		}else if (element instanceof LabelElement){				
			LabelElement labelElement = (LabelElement) element;
			Rectangle b = ((DesktopScreen) canvasScreenRegion.getScreen()).getBounds();				
			return new LabelOverlay(labelElement.text, b.x + element.x, b.y + element.y);
		}
		return new NoOpScreenDisplayable();
	}	
	
	
	protected BufferedImage getBackgroundImage(){
		return canvasScreenRegion.capture();
	}
	
	public BufferedImage createImage(){
		final PCanvas canvas = new PCanvas();
		
		BufferedImage backgroundImage = getBackgroundImage();
		final PImage background = new PImage(backgroundImage);
		canvas.getLayer().addChild(background);
		canvas.setBounds(0,0,backgroundImage.getWidth(),backgroundImage.getHeight());
		
		//canvas.getL
		PLayer layer = canvas.getLayer();
		Rectangle r = canvasScreenRegion.getBounds();
		System.out.println(canvasScreenRegion);
		PLayer foregroundLayer = new PLayer();		
		layer.addChild(foregroundLayer);		
		foregroundLayer.setGlobalTranslation(new Point(-r.x,-r.y));
		
		layer.addChild(foregroundLayer);
		
		//layer.setOffset(new Point(-r.x,-r.y));
		for (Element element : elements){
			
			//PNode node = element.createPNode();
			PNode node = PNodeFactory.createFrom(element);
//			node.setX(element.x - r.x);
//			node.setY(element.y - r.y);
//			System.out.println(element.x);
//			System.out.println(element.y);
//			System.out.println(r.y);
			//node.setOffset(element.x, element.y);
			
			//node.setOffset(element.y - r.y);
			//node.setGlobalTranslation(new Point(r.x,r.y));
			//node.setO
			//node.setOffset(new Point(-r.x,-r.y));
			//node.setOffset(new Point(r.x,r.y));
			foregroundLayer.addChild(node);
		}
		//layer.to
		
		//canvas.add(layer);
		
		return VisionUtils.createComponentImage(canvas);		
	}
}
