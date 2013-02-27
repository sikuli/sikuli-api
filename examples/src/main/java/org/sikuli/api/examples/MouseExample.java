package org.sikuli.api.examples;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class MouseExample {
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();
	
	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			addImageButton(Images.Dog, "Dog");
			addImageButton(Images.Cat, "Cat");
			wait(8000);
			close();
		}
	};
	
	public static void main(String[] args) {
		simulator.start();
		
		ScreenRegion s = new DesktopScreenRegion();	

		ScreenRegion dog = s.find(new ImageTarget(Images.Dog));
		ScreenRegion cat = s.find(new ImageTarget(Images.Cat));
		
		System.out.println("Click the dog");
		mouse.click(dog.getCenter());
		System.out.println("Click the cat");
		mouse.click(cat.getCenter());

		System.out.println("Right-click the dog");
		mouse.rightClick(dog.getCenter());
		System.out.println("Right-click the cat");
		mouse.rightClick(cat.getCenter());

		
		System.out.println("Hover over the dog");
		mouse.hover(dog.getCenter());
		System.out.println("Hover over the cat");
		mouse.hover(cat.getCenter());
		
		System.out.println("Press left");
		mouse.press();		
		System.out.println("Release left");
		mouse.release();
		
		System.out.println("Press right");
		mouse.rightPress();		
		System.out.println("Release right");
		mouse.rightRelease();

	}

}
