package org.sikuli.api.examples.advanced;
import java.awt.Rectangle;

import org.sikuli.api.ColorImageTarget;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.event.StateChangeEvent;
import org.sikuli.api.event.StateChangeListener;
import org.sikuli.api.examples.images.Images;
import org.sikuli.api.examples.utils.ScreenSimulator;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class ColorStateChangeExample {
	
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.PersonStatusGreen);
			wait(5);
			showImage(Images.PersonStatusRed);
			wait(3);
			showImage(Images.PersonStatusSilver);
			wait(3);
			showImage(Images.PersonStatusRed);
			wait(3);        				
			showImage(Images.PersonStatusGreen);
			wait(3);
			close();
		}
	};
	
	public static void main(String[] args) {
		
		simulator.start();

		Rectangle b = simulator.getBounds();
		
		ScreenRegion s = new DesktopScreenRegion(b.x, b.y, b.width, b.height);
		canvas.addBox(s).display(2);                

		StateChangeListener l = new StateChangeListener(){       				

			@Override
			public void stateChanged(StateChangeEvent event) {
				System.out.println(event.getScreenRegion() + "'s state is changed " +
						"from {" + event.getOldState() + "} " +
						" to {" + event.getNewState() + "}");			
							
				String txt = event.getOldState() + "->" + event.getNewState();
				canvas.clear().add().label(txt).above(event.getScreenRegion());
				canvas.display(1);
			}					
		};

		ScreenRegion personIcon = s.wait(new ImageTarget(Images.PersonIcon),1000);		
		canvas.clear().add().box().around(personIcon);
		canvas.display(1);
		
		ScreenRegion statusIcon = Relative.to(personIcon).right(30).getScreenRegion();
		canvas.clear().add().box().around(personIcon);
		canvas.display(1);		

		statusIcon.addState(new ColorImageTarget(Images.GreenBullet), "GREEN");
		statusIcon.addState(new ColorImageTarget(Images.RedBullet), "RED");
		statusIcon.addState(new ColorImageTarget(Images.SilverBullet), "SILVER");
		statusIcon.addStateChangeEventListener(l);

	}
}