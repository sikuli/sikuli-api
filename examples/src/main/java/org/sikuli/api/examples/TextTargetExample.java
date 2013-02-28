package org.sikuli.api.examples;

import java.awt.Rectangle;
import java.util.List;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.TextTarget;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;
import org.sikuli.core.cv.TextMap;
import org.sikuli.core.logging.ImageExplainer;
import org.sikuli.core.logging.ImageExplainer.Level;

public class TextTargetExample {
	
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.SkypeCallsPreferences);
			wait(15000);
			close();
		}
	};
	
	
	// TODO: This example is still not working ...
	public static void main(String[] args) {
		
//		simulator.setSize(800,800);
//		simulator.start();
//		
//		Rectangle b = simulator.getBounds();
//		ScreenRegion s = new ScreenRegion(b.x,b.y,b.width,b.height);
//
//		String[] stringsToFind = new String[]{
//				"Enable Voicemail",
//				"Forward calls",
//				"Incoming calls",
//				"Default Country"
//		};
		
		ImageExplainer.getExplainer(TextMap.class).setLevel(Level.ALL);
		ImageExplainer.getExplainer(TextTarget.class).setLevel(Level.ALL);
		
		ScreenRegion s = new DesktopScreenRegion();
		String[] stringsToFind = new String[]{
				//"TRANSFER",
				//"DIVIDEND",
				"Account Balance",
				"$4,021.77",
				"$0.30",
				"$3,000.00",
				"$0.25",
				"22296"
		};

		// Find each string and display a box and a text label
		for (String stringToFind : stringsToFind){		
			Target textTarget = new TextTarget(stringToFind);
			ScreenRegion r = s.find(textTarget);
			canvas.addBox(r);
			canvas.addLabel(r, stringToFind);		
		}		
		canvas.display(5);
	}
}