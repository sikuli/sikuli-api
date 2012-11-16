package org.sikuli.api.visual;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.desktop.DesktopScreen;

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
}