package org.sikuli.api.examples;
import java.awt.Rectangle;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.ScreenPainter;

public class EnterTextExample {
	
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static ScreenPainter painter = new ScreenPainter();
	
	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.PersonStatusGreen);
			addTextField(90,50);
			addTextField(90,100);
			addTextField(90,150);
			wait(5000);
			close();
		}
	};

	public static void main(String[] args) {
		simulator.start();

		Rectangle b = simulator.getBounds(); 
		ScreenRegion s = new DesktopScreenRegion(b.x,b.y,b.width,b.height);
		
		ScreenRegion r = s.find(new ImageTarget(Images.GreenBullet));
		painter.box(r, 3000);
		
		ScreenLocation t1 = Relative.to(r).center().right(30).getScreenLocation();
		mouse.click(t1);
		keyboard.type("Hello world!!");
		
		ScreenLocation t2 = Relative.to(t1).below(50).getScreenLocation();
		mouse.click(t2);
		keyboard.paste("Hello world pasted!!");
		
		ScreenLocation t3 = Relative.to(t2).below(50).getScreenLocation();
		mouse.click(t3);
		keyboard.paste("Asian text: 中文 한국 ");
	}
}