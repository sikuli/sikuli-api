package org.sikuli.api.visual;

import org.sikuli.api.DefaultLocation;
import org.sikuli.api.DefaultRegion;
import org.sikuli.api.Location;
import org.sikuli.api.Region;

public class LabelPositioningExample {

	public static void main(String[] args) {		
		Canvas canvas = new DesktopCanvas();		
		
		Region r = new DefaultRegion(100,100,200,300);
		Location p = new DefaultLocation(500,100);
		
		canvas.add().label("Absolute at (500,100)").at(p).styleWith().fontSize(15);

		canvas.add().box().around(r);

		canvas.add().label("Centered").in(r).styleWith().fontSize(15);
		canvas.add().label("Below").below(r).styleWith().fontSize(15);
		canvas.add().label("Above").above(r).styleWith().fontSize(15);
		canvas.add().label("Left").left(r).styleWith().fontSize(15);
		canvas.add().label("Right").right(r).styleWith().fontSize(15);
				
		canvas.display(3);
	}
}
