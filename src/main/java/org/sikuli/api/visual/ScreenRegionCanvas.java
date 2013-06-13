package org.sikuli.api.visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.desktop.DesktopScreen;
import org.sikuli.api.visual.element.BoxElement;
import org.sikuli.api.visual.element.CircleElement;
import org.sikuli.api.visual.element.Element;
import org.sikuli.api.visual.element.ImageElement;
import org.sikuli.api.visual.element.LabelElement;
import org.sikuli.core.cv.VisionUtils;

import com.google.common.collect.Lists;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolox.nodes.PShadow;

public class ScreenRegionCanvas extends Canvas {

	private ScreenRegion screenRegion;

	public ScreenRegionCanvas(ScreenRegion screenRegion){
		this.setScreenRegion(screenRegion);
	}

	public void display(int seconds){
		display((double)seconds);
	}

	public void display(double seconds){
		show();
		try {
			Thread.sleep((long)seconds*1000);
		} catch (InterruptedException e) {
		}
		hide();
	}
	
	public void displayWhile(Runnable runnable){
		show();
		runnable.run();
		hide();
	}
	
	List<ScreenDisplayable> displayableList = Lists.newArrayList();	
	public void show(){
		for (Element element : getElements()){
			displayableList.add(createScreenDisplayable(element));		
		}

		for (ScreenDisplayable d : displayableList){
			d.displayOnScreen();
		}
	}
	
	public void hide(){		
		for (ScreenDisplayable d : displayableList){
			d.hideFromScreen();
			ScreenOverlayWindow w = (ScreenOverlayWindow) d;
			// do this to release all references to graphical objects, such as PShadow,
			// which holds references to BufferedImages
			// this deals with the memory leak problem related to the use
			// of ScreenRegionCanvas
			PCanvas canvas = w.getCanvas();
			canvas.removeAll();
		}
		displayableList.clear();
		
		// force garbage collection
		System.gc();
	}	

	protected ScreenDisplayable createScreenDisplayable(Element element) {
		Rectangle screenBounds = ((DesktopScreen) getScreenRegion().getScreen()).getBounds();

		ScreenOverlayWindow overlayWindow = new ScreenOverlayWindow();

		PNode node = PNodeFactory.createFrom(element);
		int x = (int) node.getXOffset();
		int y = (int) node.getYOffset();
		PBounds bounds = node.getBounds();
		node.setOffset(0,0);			
		overlayWindow.getCanvas().getLayer().addChild(node);

		overlayWindow.setLocation(screenBounds.x + x, screenBounds.y + y);
		overlayWindow.setSize((int)bounds.width, (int)bounds.height);
		return overlayWindow;

	}	

	public BufferedImage createImage(){
		final PCanvas canvas = new PCanvas();

		BufferedImage backgroundImage = getScreenRegion().capture();
		final PImage background = new PImage(backgroundImage);
		canvas.getLayer().addChild(background);
		canvas.setBounds(0,0,backgroundImage.getWidth(),backgroundImage.getHeight());

		PLayer layer = canvas.getLayer();
		Rectangle r = getScreenRegion().getBounds();
		PLayer foregroundLayer = new PLayer();		
		layer.addChild(foregroundLayer);		
		foregroundLayer.setGlobalTranslation(new Point(-r.x,-r.y));

		layer.addChild(foregroundLayer);

		for (Element element : getElements()){
			PNode node = PNodeFactory.createFrom(element);
			foregroundLayer.addChild(node);
		}
		return VisionUtils.createComponentImage(canvas);		
	}

	public ScreenRegion getScreenRegion() {
		return screenRegion;
	}

	public void setScreenRegion(ScreenRegion screenRegion) {
		this.screenRegion = screenRegion;
	}
}


class PNodeFactory {

	static public PNode createFrom(Element element){
		Class<? extends Element> clazz = element.getClass();
		if (clazz == LabelElement.class){
			return createFrom((LabelElement)element);
		}else if (clazz == BoxElement.class){
			return createFrom((BoxElement) element);
		}else if (clazz == CircleElement.class){
			return createFrom((CircleElement) element);
		}else if (clazz == ImageElement.class){
			return createFrom((ImageElement) element);
		}	
		return new PNode();
	}

	static public PNode createFrom(LabelElement element){
		final PText txt = new PText(element.text);
		txt.setTextPaint(Color.black);
		txt.setPaint(Color.yellow);
		txt.setTextPaint(element.color);
		txt.setFont(txt.getFont().deriveFont(element.fontSize));

		PNode labelNode = new PNode();
		labelNode.setPaint(Color.yellow);
		labelNode.addChild(txt);
		labelNode.setHeight(txt.getHeight()+2);
		labelNode.setWidth(txt.getWidth()+4);
		txt.setOffset(2,1);


		labelNode.setOffset(element.x, element.y);
		return addShadow(labelNode);
	}
	
	static public PNode createFrom(CircleElement element){
		PPath p = PPath.createEllipse(1,1,element.width,element.height);
		p.setStrokePaint(element.lineColor);
		p.setPaint(null);		
		p.setStroke(new BasicStroke(element.lineWidth));

		PNode foregroundNode = new PNode();
		foregroundNode.addChild(p);
		foregroundNode.setHeight(p.getHeight()+4);
		foregroundNode.setWidth(p.getWidth()+4);
		p.setOffset(2,2);

		foregroundNode.setOffset(element.x, element.y);

		return addShadow(foregroundNode);
	}

	static public PNode createFrom(BoxElement element){
		PPath p = PPath.createRectangle(1,1,element.width,element.height);
		p.setStrokePaint(element.lineColor);
		p.setPaint(null);		
		p.setStroke(new BasicStroke(element.lineWidth));


		PNode foregroundNode = new PNode();
		foregroundNode.addChild(p);
		foregroundNode.setHeight(p.getHeight()+4);
		foregroundNode.setWidth(p.getWidth()+4);
		p.setOffset(2,2);

		foregroundNode.setOffset(element.x, element.y);

		return addShadow(foregroundNode);
	}
	
	static public PNode createFrom(ImageElement element){
		PImage p = new PImage(element.image);

		PNode foregroundNode = new PNode();
		foregroundNode.addChild(p);
		foregroundNode.setHeight(p.getHeight()+4);
		foregroundNode.setWidth(p.getWidth()+4);
		p.setOffset(2,2);

		foregroundNode.setOffset(element.x, element.y);

		return addShadow(foregroundNode);
	}

	static public void setStyle(){

	}

	static private final Color SHADOW_PAINT = new Color(10, 10, 10, 200);
	static private PNode addShadow(PNode contentNode){

		PNode contentNodeWithShadow = new PNode();

		double xoffset = contentNode.getXOffset();
		double yoffset = contentNode.getYOffset();

		int blurRadius = 4;
		int tx = 5;
		int ty = 5;

		PShadow shadowNode = new PShadow(contentNode.toImage(), SHADOW_PAINT, blurRadius );		
		contentNode.setOffset(tx, ty);
		shadowNode.setOffset(tx - (2 * blurRadius) + 1.0d, ty - (2 * blurRadius) + 1.0d);	
		contentNodeWithShadow.addChild(shadowNode);
		contentNodeWithShadow.addChild(contentNode);		      
		contentNodeWithShadow.removeChild(shadowNode);
		contentNodeWithShadow.setOffset(xoffset - tx  - blurRadius, yoffset - ty - blurRadius);
		contentNodeWithShadow.setBounds(0,0, contentNode.getWidth() + 2*blurRadius + tx, contentNode.getHeight() + 2*blurRadius + ty);
		return contentNodeWithShadow;
	}
}

