package org.sikuli.api.visual;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import org.sikuli.api.ScreenRegion;
import org.sikuli.api.remote.client.RemoteScreen;
import org.sikuli.api.robot.desktop.DesktopScreen;
import org.sikuli.core.cv.VisionUtils;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.util.PBounds;

public class ScreenRegionCanvas extends DrawingCanvas {

	private ScreenRegion canvasScreenRegion;
	private ImageViewer viewer;

	public ScreenRegionCanvas(ScreenRegion screenRegion){
		this.canvasScreenRegion = screenRegion;
	}

	

	public void displayForSeconds(int seconds){
		
		if (canvasScreenRegion.getScreen() instanceof RemoteScreen ){
			
			Rectangle r = canvasScreenRegion.getBounds();
			
			String title = String.format("RemoteScreenRegion (%d,%d) %dx%d", r.x,r.y,r.width,r.height);
			
			BufferedImage image = createImage();
			viewer = Objects.firstNonNull(viewer, new ImageViewer());
			viewer.updateImage(image);
			viewer.setTitle(title);
			viewer.setVisible(true);
			
			try {
				Thread.sleep(seconds*1000);
			} catch (InterruptedException e) {
			}
			
			viewer.setVisible(false);
			
		}else{

			List<ScreenDisplayable> displayableList = Lists.newArrayList();
			for (Element element : elements){
				displayableList.add(createScreenDisplayable(element));
			}

			for (ScreenDisplayable d : displayableList){
				d.displayOnScreen();
			}

			try {
				Thread.sleep(seconds*1000);
			} catch (InterruptedException e) {
			}

			for (ScreenDisplayable d : displayableList){
				d.hideFromScreen();
			}
		}
	}
	
	

	protected ScreenDisplayable createScreenDisplayable(Element element) {

		Rectangle screenBounds = ((DesktopScreen) canvasScreenRegion.getScreen()).getBounds();

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


	protected BufferedImage getBackgroundImage(){
		return canvasScreenRegion.capture();
	}

	public BufferedImage createImage(){
		final PCanvas canvas = new PCanvas();

		BufferedImage backgroundImage = getBackgroundImage();
		final PImage background = new PImage(backgroundImage);
		canvas.getLayer().addChild(background);
		canvas.setBounds(0,0,backgroundImage.getWidth(),backgroundImage.getHeight());

		PLayer layer = canvas.getLayer();
		Rectangle r = canvasScreenRegion.getBounds();
		System.out.println(canvasScreenRegion);
		PLayer foregroundLayer = new PLayer();		
		layer.addChild(foregroundLayer);		
		foregroundLayer.setGlobalTranslation(new Point(-r.x,-r.y));

		layer.addChild(foregroundLayer);

		for (Element element : elements){
			PNode node = PNodeFactory.createFrom(element);
			foregroundLayer.addChild(node);
		}
		return VisionUtils.createComponentImage(canvas);		
	}
}
