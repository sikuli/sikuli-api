package org.sikuli.api.visual;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ListIterator;

import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.desktop.DesktopScreen;
import org.sikuli.api.visual.element.Element;
import org.sikuli.core.cv.VisionUtils;

import com.google.common.collect.Lists;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.util.PBounds;

public class ScreenRegionCanvas extends Canvas {

	private ScreenRegion screenRegion;
	/**
	 * Constructs a new DesktopCanvas whose screen region is specified with the 
	 * argument of the same name.
	 * 
	 * @param screenRegion the specified screen region of the Canvas.
	 */
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
			removeAllChildrenRecursively(canvas.getLayer());
		}
		displayableList.clear();
		
		// force garbage collection
		System.gc();
	}	
	
	static private void removeAllChildrenRecursively(PNode node){
		ListIterator it = node.getChildrenIterator();
		while (it.hasNext()){
			PNode n = (PNode) it.next();
			removeAllChildrenRecursively(n);
		}
		node.removeAllChildren();
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
	/**
	 * Returns the ScreenRegion of this ScreenRegionCanvas object.
	 * 
	 * @return the ScreenRegion object.
	 */
	public ScreenRegion getScreenRegion() {
		return screenRegion;
	}
	/**
	 * Sets the ScreenRegion of this ScreenRegionCanvas object.
	 * 
	 * @param screenRegion the specified ScreenRegion.
	 */
	public void setScreenRegion(ScreenRegion screenRegion) {
		this.screenRegion = screenRegion;
	}

}

