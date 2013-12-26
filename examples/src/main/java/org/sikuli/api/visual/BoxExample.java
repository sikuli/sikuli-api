package org.sikuli.api.visual;
import java.awt.Color;

public class BoxExample {

	public static void main(String[] args) {		
		Canvas canvas = new DesktopCanvas();		

		canvas.add().box().around(120,120,400,300).styleWith().lineColor(Color.black);
		
		canvas.add().box().size(400,300).at(150,150);
		canvas.add().box().size(400,300).at(170,170).styleWith().lineColor(Color.blue).lineWidth(5);
		canvas.add().box().size(400,300).at(190,190).styleWith().backgroundColor(Color.green).lineWidth(7);
				
		canvas.display(3);
	}
}
