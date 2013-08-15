package org.sikuli.api.examples;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.sikuli.api.DesktopScreenLocation;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class CanvasElementsStylingExample {

	public static void main(String[] args) throws IOException {

		
		Canvas canvas = new DesktopCanvas();
				
		ScreenRegion r = new DesktopScreenRegion(150,150,400,300);
		
		canvas.clear();
		canvas.addBox(r);		
		r = Relative.to(r).shorter(20).narrower(20).getScreenRegion();
		canvas.addBox(r).withLineColor(Color.green);
		r = Relative.to(r).shorter(30).narrower(30).getScreenRegion();
		canvas.addBox(r).withLineWidth(5);
		canvas.display(3);
		
		canvas.clear();
		canvas.addCircle(r.getCenter(), 10);
		canvas.addCircle(r.getCenter(), 30).withLineColor(Color.green).withLineWidth(5);
		canvas.addCircle(r.getCenter(), 50).withLineColor(Color.blue).withLineWidth(7).withTransparency(0.5f);
		canvas.display(3);
		
		ScreenLocation q = new DesktopScreenLocation(400,150);
		
		canvas.clear();
		canvas.addLabel(q,"default style");
		q = Relative.to(q).below(50).getScreenLocation();		
		canvas.addLabel(q,"different color").withColor(Color.blue);
		q = Relative.to(q).below(50).getScreenLocation();
		canvas.addLabel(q,"larger font size").withFontSize(20);
		q = Relative.to(q).below(50).getScreenLocation();
		canvas.addLabel(q,"different background color").withBackgroundColor(Color.green);
		q = Relative.to(q).below(50).getScreenLocation();
		canvas.addLabel(q,"line 1\nline 2\nline 3");
		q = Relative.to(q).below(50).getScreenLocation();
		canvas.addLabel(q,"transparent").withTransparency(0.5f);
		canvas.display(3);		

		// Vertical / Horizontal Alignment wrt a location
		ScreenLocation p = new DesktopScreenLocation(400,400);

		canvas.clear();
		canvas.addLabel(p,"horizontal-right, vertical-middle")
			.withHorizontalAlignmentRight().withVerticalAlignmentMiddle();
		canvas.addLabel(p,"horizontal-left, vertical-middle")
			.withHorizontalAlignmentLeft().withVerticalAlignmentMiddle();
		canvas.addDot(p);		
		canvas.display(3);
		
		canvas.clear();
		canvas.addLabel(p,"horizontal-center, vertical-top")
			.withHorizontalAlignmentCenter().withVerticalAlignmentTop();		
		canvas.addLabel(p,"horizontal-center, vertical-bottom")
			.withHorizontalAlignmentCenter().withVerticalAlignmentBottom();		
		canvas.addDot(p);		
		canvas.display(3);
		
		canvas.clear();
		canvas.addLabel(p,"horizontal-center, vertical-middle")
			.withHorizontalAlignmentCenter().withVerticalAlignmentMiddle();		
		canvas.addDot(p);		
		canvas.display(3);
		
		canvas.clear();
		canvas.addImage(p, ImageIO.read(Images.Dog))
			.withHorizontalAlignmentCenter().withVerticalAlignmentMiddle();
		canvas.addLabel(p,"dog")
			.withHorizontalAlignmentCenter().withVerticalAlignmentMiddle();
		canvas.addDot(p);		
		canvas.display(3);

			
	}
}
