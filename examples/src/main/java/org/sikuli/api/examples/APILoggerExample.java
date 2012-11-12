package org.sikuli.api.examples;

import java.awt.Rectangle;
import java.io.File;
import org.sikuli.api.*;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.ScreenPainter;

public class APILoggerExample {
	
	static ScreenSimulator simulator;
	
	
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static ScreenPainter painter = new ScreenPainter();

	public static void runStdoutLoggerExample() {
		// set the API logger to write default logging messages to stdout 
		APILogger.setLogger(APILogger.createStdoutLogger());		

		// get the screen region of the simulator window
		Rectangle b = simulator.getBounds();		
		ScreenRegion s = new DesktopScreenRegion(b.x, b.y, b.width, b.height);

		// perform some actions to be logged to stdout		
		ScreenRegion r = s.find(new ImageTarget(Images.OSXDockIcon));
		mouse.click(r.getCenter());				
		mouse.rightClick(Relative.to(r).topLeft().getScreenLocation());
		mouse.doubleClick(r.getCenter());
	}
	
	public static void runVisualLoggerExample() {
		
		// set the API logger to use the visual logger in the fullscreen mode and write
		// the resulting log images to the directory "log"
		APILogger.setLogger(APILogger.createVisualLogger(new DesktopScreenRegion(), new File("log")));

		// get the screen region of the simulator window
		Rectangle b = simulator.getBounds();		
		ScreenRegion s = new DesktopScreenRegion(b.x, b.y, b.width, b.height);

		// perform some actions to be logged by the visual logger
		ScreenRegion r = s.find(new ImageTarget(Images.OSXDockIcon));
		mouse.click(r.getCenter());
		mouse.rightClick(Relative.to(r).topLeft().getScreenLocation());
		mouse.doubleClick(r.getCenter());
	}
	
	public static void runCustomLoggerExample() {
		
		// define a custom logger that logs two actions: find and click
		APILogger myCustomLogger = new APILogger(){
			@Override
			public void findPerformed(ScreenRegion screenRegion, Target target,
					ScreenRegion result) {
				painter.box(screenRegion, 1000);
				painter.label(screenRegion,"Find",1000);
				if (result != null){
					painter.box(result,1000);					
				}
			}

			@Override
			public void clickPerformed(ScreenLocation location) {
				painter.label(location, "Click", 1000);
				painter.circle(location, 1000);
			}
		};

		// set the API logger to use this custom logger
		APILogger.setLogger(myCustomLogger);

		// get the screen region of the simulator window
		Rectangle b = simulator.getBounds();		
		ScreenRegion s = new DesktopScreenRegion(b.x, b.y, b.width, b.height);
		
		// perform some actions that will be logged by the custom logger
		ScreenRegion r = s.find(new ImageTarget(Images.OSXDockIcon));
		mouse.click(r.getCenter());
		mouse.click(Relative.to(r).topLeft().getScreenLocation());
		mouse.click(Relative.to(r).bottomRight().getScreenLocation());
	}

	public static void main(String[] args) {
		simulator = new ScreenSimulator(){
			public void run(){
				showImage(Images.OSXSystemPreferences);
				wait(15000);
				close();
			}
		};
		simulator.start();

		runStdoutLoggerExample();
		runVisualLoggerExample();
		runCustomLoggerExample();
	}
}
