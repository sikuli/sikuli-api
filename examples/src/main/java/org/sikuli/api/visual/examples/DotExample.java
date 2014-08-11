package org.sikuli.api.visual.examples;

import java.awt.Color;

import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class DotExample {

	public static void main(String[] args) {		
		Canvas canvas = new DesktopCanvas();		

		canvas.add().dot().at(100,100);
		canvas.add().dot().at(120,100).styleWith().color(Color.green);
		canvas.add().dot().at(140,100).styleWith().color(Color.blue);
						
		canvas.display(3);
	}
}
