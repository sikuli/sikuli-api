package org.sikuli.api.examples.advanced;
import java.awt.Rectangle;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.Relative;
import org.sikuli.api.Target;
import org.sikuli.api.event.TargetEvent;
import org.sikuli.api.event.TargetEventListener;
import org.sikuli.api.examples.images.Images;
import org.sikuli.api.examples.utils.ScreenSimulator;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class TargetEventExample {

	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.SceneEmpty);
			wait(3);
			showImage(Images.SceneCat1);
			wait(3);
			showImage(Images.SceneCat2);
			wait(3);       					
			showImage(Images.SceneDogCat2);
			wait(3);
			showImage(Images.SceneDog);
			wait(3);
			showImage(Images.SceneEmpty);
			wait(3);
			close();
		}
	};

	public static void main(String[] args) {

		simulator.start();       			

		Rectangle b = simulator.getBounds();

		DesktopScreenRegion smallRegion = new DesktopScreenRegion(b.x, b.y, b.width, b.height);

		TargetEventListener l = new TargetEventListener(){       				
			@Override
			public void targetAppeared(TargetEvent event) {
				System.out.println(event.getTarget() + " has appeared within " + event.getScreenRegion() + 
						" at " + Relative.to(event.getTargetRegion()).topLeft().getScreenLocation());	
				canvas.clear().addBox(event.getTargetRegion());
				canvas.addLabel(event.getTargetRegion().getCenter(),"appeared");
				canvas.display(1);
			}

			@Override
			public void targetVanished(TargetEvent event) {
				System.out.println(event.getTarget() + " has vanished from " + event.getScreenRegion());
				canvas.clear().addBox(event.getTargetRegion());
				canvas.addLabel(event.getTargetRegion().getCenter(),"vanished");
				canvas.display(1);
			}

			@Override
			public void targetMoved(TargetEvent event) {
				System.out.println(event.getTarget() + " has moved to " + 
						Relative.to(event.getTargetRegion()).topLeft().getScreenLocation());
				canvas.clear().addBox(event.getTargetRegion());
				canvas.addLabel(event.getTargetRegion().getCenter(),"moved");
				canvas.display(1);
			}					
		};

		// watch the dog and the cat in the screen region
		Target cat = new ImageTarget(Images.Cat);
		Target dog = new ImageTarget(Images.Dog);
		smallRegion.addTargetEventListener(dog, l);       			
		smallRegion.addTargetEventListener(cat, l); 
	}
}