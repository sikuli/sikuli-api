package org.sikuli.api.visual;
import java.awt.Color;

public class CircleExample {

	public static void main(String[] args) {		
		Canvas canvas = new DesktopCanvas();		
				
		canvas.add().circle().size(50,50).centeredAt(100,100).styleWith().lineColor(Color.red);
		canvas.add().circle().size(70,70).centeredAt(100,100).styleWith().lineColor(Color.green);
		
		canvas.add().circle().around(200,100,100,100).styleWith().lineColor(Color.blue).lineWidth(5);
				
		canvas.display(3);
	}
}
