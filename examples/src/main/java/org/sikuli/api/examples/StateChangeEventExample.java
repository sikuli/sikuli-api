package org.sikuli.api.examples;
import java.awt.Rectangle;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.StateChangeEvent;
import org.sikuli.api.StateChangeListener;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.visual.ScreenPainter;

public class StateChangeEventExample {

	static Mouse mouse = new Mouse();
	static Keyboard keyboard = new Keyboard();
	static ScreenPainter painter = new ScreenPainter();

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

		ScreenRegion smallRegion = new ScreenRegion(b.x, b.y, b.width, b.height);

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

		painter.box(smallRegion,10000);
	}
}