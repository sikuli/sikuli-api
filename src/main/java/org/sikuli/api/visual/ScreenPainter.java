package org.sikuli.api.visual;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.URL;

import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.DesktopScreen;

public class ScreenPainter {
		
	public void box(ScreenRegion r, int duration){
		Rectangle b = ((DesktopScreen) r.getScreen()).getBounds();
		new BoxOverlay(b.x+r.getX(), b.y+r.getY(), r.getWidth(), r.getHeight()).show(duration);
	}
	
	public void circle(ScreenRegion r, int duration){
		Rectangle b = ((DesktopScreen) r.getScreen()).getBounds();
		new CircleOverlay(b.x+r.getX(), b.y+r.getY(), r.getWidth(), r.getHeight()).show(duration);
	}
	
	public void circle(ScreenLocation l, int duration){
		Rectangle b = ((DesktopScreen) l.getScreen()).getBounds();
		new CircleOverlay(b.x + l.x-10, b.y + l.y-10, 20, 20).show(duration);
	}
		
	public void label(ScreenRegion r, String txt, int duration){
		label(r.getTopLeft().getAbove(5).getLeft(5), txt, duration);
	}
	
	public void label(ScreenLocation l, String txt, int duration){
		Rectangle b = ((DesktopScreen) l.getScreen()).getBounds();
		new LabelOverlay(txt, b.x + l.x, b.y + l.y).show(duration);
	}

	public void image(ScreenLocation l, BufferedImage image, int time){
		Rectangle b = ((DesktopScreen) l.getScreen()).getBounds();
		new ImageOverlay(image, b.x + l.x, b.y + l.y).show(time);
	}		
}

