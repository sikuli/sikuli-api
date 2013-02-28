package org.sikuli.api.examples;
import java.awt.Rectangle;
import java.io.File;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class ScreenRegionRecordExample {
	
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();
	
	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.SceneEmpty);
			wait(1);
			showImage(Images.SceneCat1);
			wait(1);
			showImage(Images.SceneCat2);
			wait(1);       					
			showImage(Images.SceneDogCat2);
			wait(1);
			close();
		}
	};
	
	public static void main(String[] args) {
		
		simulator.start();
		
		Rectangle b = simulator.getBounds(); 
		DesktopScreenRegion s = new DesktopScreenRegion(b.x,b.y,b.width,b.height);	
		canvas.addBox(s).display(5);
		//s.record(new File("ScreenRegionRecordExampleVideo.mov"), 5000);
		
	}
}