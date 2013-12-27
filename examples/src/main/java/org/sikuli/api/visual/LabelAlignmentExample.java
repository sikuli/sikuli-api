package org.sikuli.api.visual;

import java.awt.Color;

import org.sikuli.api.DefaultRegion;
import org.sikuli.api.Region;
import org.sikuli.api.Relative;
import org.sikuli.api.visual.element.Element;

public class LabelAlignmentExample {

	public static void main(String[] args) {		
		Canvas canvas = new DesktopCanvas();		
		
		Region r = new DefaultRegion(100,100,200,300);
		
		canvas.add().box().around(r);		
	//	canvas.add().label("This is a label").at(Relative.to(r).center());
		
//		canvas.add().label("line 1\nline 2\nline 3").at(100, 150);
//		canvas.add().label("larger font size").at(100, 200).styleWith().fontSize(20);
//		canvas.add().label("transparent green background").at(100, 250).styleWith().transparency(0.5f).backgroundColor(Color.green);
				
		canvas.display(3);
	}
}
