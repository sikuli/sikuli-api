package org.sikuli.api.examples;
import static org.sikuli.api.API.pause;

import java.awt.Rectangle;
import java.util.List;

import org.sikuli.api.ColorImageTarget;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.Target.Ordering;
import org.sikuli.api.event.StateChangeEvent;
import org.sikuli.api.event.StateChangeListener;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.ScreenPainter;

public class TwoRegionColorStateChangeExample {

	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static ScreenPainter painter = new ScreenPainter();
	
	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.HollowColorStars1);
			wait(5000);
			showImage(Images.HollowColorStars2);
			wait(3000);
			showImage(Images.HollowColorStars3);
			wait(3000);       					
			showImage(Images.HollowColorStars4);
			wait(3000);
			showImage(Images.HollowColorStars1);
			wait(3000);
			close();
		}
	};
	
	public static void main(String[] args) {
		
		simulator.start();       			

		Rectangle b = simulator.getBounds();

		ScreenRegion s = new DesktopScreenRegion(b.x, b.y, b.width, b.height);
		painter.box(s, 10000);

		List<ScreenRegion> rs;
		Target target = new ImageTarget(Images.HollowRedStar);
		target.setOrdering(Ordering.LEFT_RIGHT);
		rs = s.findAll(target);    			

		ScreenRegion left = rs.get(0);
		ScreenRegion right = rs.get(1);

		left = Relative.to(left).taller(30).wider(30).getScreenRegion();
		right = Relative.to(right).taller(30).wider(30).getScreenRegion();

		painter.box(left, 1000);
		painter.label(left, "left", 1000);

		painter.box(right,  1000);
		painter.label(right, "right", 1000);

		pause(3000);    			

		StateChangeListener l = new StateChangeListener(){       				

			@Override
			public void stateChanged(StateChangeEvent event) {
				System.out.println(event.getScreenRegion() + "'s state is changed " +
						"from {" + event.getOldState() + "} " +
						" to {" + event.getNewState() + "}");	
				
				painter.label(event.getScreenRegion(), event.getOldState() + "->" + event.getNewState(), 1000);
			}					
		};


		left.addState(new ColorImageTarget(Images.HollowGreenStar), "GREEN");
		left.addState(new ColorImageTarget(Images.HollowRedStar), "RED");
		left.addState(new ColorImageTarget(Images.HollowBlueStar), "BLUE");
		left.addStateChangeEventListener(l);

		right.addState(new ColorImageTarget(Images.HollowGreenStar), "GREEN");
		right.addState(new ColorImageTarget(Images.HollowRedStar), "RED");
		right.addState(new ColorImageTarget(Images.HollowBlueStar), "BLUE");
		right.addStateChangeEventListener(l);

	}
}