package org.sikuli.api.visual;

import java.awt.Color;

public class LabelExample {

	public static void main(String[] args) {		
		Canvas canvas = new DesktopCanvas();		

		canvas.add().label("This is a label").at(100,100);
		canvas.add().label("line 1\nline 2\nline 3").at(100, 150);
		canvas.add().label("larger font size").at(100, 200).styleWith().fontSize(20);
		canvas.add().label("transparent green background").at(100, 250).styleWith().transparency(0.5f).backgroundColor(Color.green);
				
		canvas.display(3);
	}
}
