package org.sikuli.api.examples;
import java.awt.Rectangle;
import java.io.File;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.ScreenPainter;

public class ScreenRegionRecordExample {
	
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static ScreenPainter painter = new ScreenPainter();
	
	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.SceneEmpty);
			wait(1000);
			showImage(Images.SceneCat1);
			wait(1000);
			showImage(Images.SceneCat2);
			wait(1000);       					
			showImage(Images.SceneDogCat2);
			wait(1000);
			close();
		}
	};
	
	public static void main(String[] args) {
		
		simulator.start();
		
		Rectangle b = simulator.getBounds(); 
		DesktopScreenRegion s = new DesktopScreenRegion(b.x,b.y,b.width,b.height);	
		painter.box(s, 50000);
		//s.record(new File("ScreenRegionRecordExampleVideo.mov"), 5000);
		
	}
}