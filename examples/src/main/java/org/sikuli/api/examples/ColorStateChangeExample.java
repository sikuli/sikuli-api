package org.sikuli.api.examples;
import java.awt.Rectangle;

import org.sikuli.api.ColorImageTarget;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.StateChangeEvent;
import org.sikuli.api.StateChangeListener;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.visual.ScreenPainter;

public class ColorStateChangeExample {
	
	static Mouse mouse = new Mouse();
	static Keyboard keyboard = new Keyboard();
	static ScreenPainter painter = new ScreenPainter();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.PersonStatusGreen);
			wait(5000);
			showImage(Images.PersonStatusRed);
			wait(3000);
			showImage(Images.PersonStatusSilver);
			wait(3000);
			showImage(Images.PersonStatusRed);
			wait(3000);        				
			showImage(Images.PersonStatusGreen);
			wait(3000);
			close();
		}
	};
	
	public static void main(String[] args) {
		
		simulator.start();

		Rectangle b = simulator.getBounds();
		
		ScreenRegion s = new DesktopScreenRegion(b.x, b.y, b.width, b.height);
		painter.box(s, 2000);                

		StateChangeListener l = new StateChangeListener(){       				

			@Override
			public void stateChanged(StateChangeEvent event) {
				System.out.println(event.getScreenRegion() + "'s state is changed " +
						"from {" + event.getOldState() + "} " +
						" to {" + event.getNewState() + "}");			
				
				
				String txt = event.getOldState() + "->" + event.getNewState();
				ScreenLocation labelLocation = Relative.to(event.getScreenRegion()).topLeft().above(20).getScreenLocation();
				painter.label(labelLocation,txt, 1000);
			}					
		};

		ScreenRegion personIcon = s.wait(new ImageTarget(Images.PersonIcon),1000);
		painter.box(personIcon, 1000);

		ScreenRegion statusIcon = Relative.to(personIcon).right(30).getScreenRegion();
		painter.box(statusIcon, 1000);

		statusIcon.addState(new ColorImageTarget(Images.GreenBullet), "GREEN");
		statusIcon.addState(new ColorImageTarget(Images.RedBullet), "RED");
		statusIcon.addState(new ColorImageTarget(Images.SilverBullet), "SILVER");
		statusIcon.addStateChangeEventListener(l);

	}
}