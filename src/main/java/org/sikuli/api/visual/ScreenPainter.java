package org.sikuli.api.visual;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.sikuli.api.Relative;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.DesktopScreen;

public class ScreenPainter {
		
	public void box(ScreenRegion screenRegion, int duration){
		Rectangle b = ((DesktopScreen) screenRegion.getScreen()).getBounds();
		Rectangle r = screenRegion.getBounds();
		new BoxOverlay(b.x+r.x, b.y+r.y, r.width, r.height).show(duration);
	}
	
	public void circle(ScreenRegion screenRegion, int duration){
		Rectangle b = ((DesktopScreen) screenRegion.getScreen()).getBounds();
		Rectangle r = screenRegion.getBounds();
		new CircleOverlay(b.x+r.x, b.y+r.y, r.width, r.height).show(duration);
	}
	
	public void circle(ScreenLocation l, int duration){
		Rectangle b = ((DesktopScreen) l.getScreen()).getBounds();
		new CircleOverlay(b.x + l.getX()-10, b.y + l.getY()-10, 20, 20).show(duration);
	}
		
	public void label(ScreenRegion screenRegion, String txt, int duration){
		ScreenLocation labelLocation = Relative.to(screenRegion).topLeft().above(5).left(5).getScreenLocation();
		label(labelLocation, txt, duration);
	}
	
	public void label(ScreenLocation l, String txt, int duration){
		Rectangle b = ((DesktopScreen) l.getScreen()).getBounds();
		new LabelOverlay(txt, b.x + l.getX(), b.y + l.getY()).show(duration);
	}

	public void image(ScreenLocation l, BufferedImage image, int time){
		Rectangle b = ((DesktopScreen) l.getScreen()).getBounds();
		new ImageOverlay(image, b.x + l.getX(), b.y + l.getY()).show(time);
	}		
}

