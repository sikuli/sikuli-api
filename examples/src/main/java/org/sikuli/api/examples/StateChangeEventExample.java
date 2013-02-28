package org.sikuli.api.examples;
import java.awt.Rectangle;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.event.StateChangeEvent;
import org.sikuli.api.event.StateChangeListener;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class StateChangeEventExample {

	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.SceneEmpty);
			wait(3000);
			showImage(Images.SceneCat1);
			wait(3000);
			showImage(Images.SceneDog);
			wait(3000);
			showImage(Images.SceneCat1);
			wait(3000);
			showImage(Images.SceneEmpty);
			wait(3000);
			close();
		}
	};
	
	public static void main(String[] args) {
		
		simulator.start();
		
		Rectangle b = simulator.getBounds();

		DesktopScreenRegion smallRegion = new DesktopScreenRegion(b.x, b.y, b.width, b.height);

		StateChangeListener l = new StateChangeListener(){       				

			@Override
			public void stateChanged(StateChangeEvent event) {
				System.out.println(event.getScreenRegion() + "'s state is changed " +
						"from {" + event.getOldState() + "} " +
						" to {" + event.getNewState() + "}");						
			}					
		};


		Target cat = new ImageTarget(Images.Cat);
		Target dog = new ImageTarget(Images.Dog);

		smallRegion.addState(cat, "HAS A CAT");
		smallRegion.addState(dog, "HAS A DOG");
		smallRegion.addStateChangeEventListener(l);

		// TODO: test if remove works
		//smallRegion.removeState(cat);

		canvas.addBox(smallRegion).display(10);
		
	}
}