package org.sikuli.api.robot.desktop;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.Screen;
import org.sikuli.api.ScreenLocation;

import com.google.common.collect.Lists;

class AWTDesktop {
	private static GraphicsEnvironment _genv;
	private static GraphicsDevice[] _gdev;
	private static AWTRobot[] _robots;
	//private static DesktopMouse[] _desktopMouse
	private static List<ScreenDevice> _screens;

	static{
		_genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		_gdev = _genv.getScreenDevices();
		initRobots();
		initScreens();
	}


	private static void initScreens(){
		_screens = Lists.newArrayList();
		for(int i=0;i<_gdev.length;i++){
			ScreenDevice device = new ScreenDevice();
			device.id = i;
			device.gdev = _gdev[i];
			device.robot = _robots[i];	
			device.mouse = new AWTMouse(device.robot);
			device.screen = new DesktopScreen(i);
			_screens.add(device);
		}
	}

	private static void initRobots(){
		try{
			_robots = new AWTRobot[_gdev.length];
			for(int i=0;i<_gdev.length;i++){
				_robots[i] = new AWTRobot(_gdev[i]);
				//_robots[i].setAutoWaitForIdle(false); //TODO: make sure we don't need this
				_robots[i].setAutoDelay(10);
			}
		}
		catch(AWTException e){
			//Debug.error("Can't initiate Java Robot: " + e);
		}
	}


	static class ScreenDevice {

		int id;
		GraphicsDevice gdev;
		AWTRobot robot;
		AWTMouse mouse;
		DesktopScreen screen;

		Rectangle getBounds(){
			return gdev.getDefaultConfiguration().getBounds();
		}

		AWTRobot getRobot(){
			return robot; 
		}		
	}

	public static ScreenLocation getCurrentMouseScreenLocation(){		
		Point p = MouseInfo.getPointerInfo().getLocation();		
		Screen screen = getCurrentScreenDevice().screen;
		Rectangle bounds = getCurrentScreenDevice().getBounds();
		ScreenLocation screenLocation = new DefaultScreenLocation(screen, p.x - bounds.x, p.y - bounds.y);
		screenLocation.setScreen(screen);
		return screenLocation;
	}

	static AWTMouse getCurrentMouse(){
		return getCurrentScreenDevice().mouse;
	}

	static ScreenDevice getCurrentScreenDevice(){
		GraphicsDevice currentGraphicsDevice = MouseInfo.getPointerInfo().getDevice();
		for (ScreenDevice s : _screens){
			if (s.gdev == currentGraphicsDevice){
				return s;
			}
		}
		return null;	
	}

	static ScreenDevice getScreenDevice(int id){
		if(id<_screens.size()){
			return _screens.get(id);
		}
		return null;
	}

	public static AWTRobot getCurrentRobot() {
		return getCurrentScreenDevice().getRobot();
	}	

	static AWTMouse getMouse(ScreenLocation location){
		int id = ((DesktopScreen) location.getScreen()).getId();
		return _screens.get(id).mouse;
	}

	static AWTRobot getRobot(ScreenLocation location){
		int id = ((DesktopScreen) location.getScreen()).getId();
		return _screens.get(id).robot;
	}

	public static Rectangle getScreenBounds(int screenId){
		return getScreenDevice(screenId).getBounds();
	}

	public static BufferedImage captureScreenshot(int screenId, int x, int y, int width, int height) {
		return _robots[screenId].captureScreen(new Rectangle(x,y,width,height));
	}


	/*
	 * retrieves the number of available screens
	 */
	public static int getNumberScreens() {
	        return _screens.size();
	}

	

}