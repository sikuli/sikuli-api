/*
 * Copyright 2010-2011, Sikuli.org
 * Released under the MIT License.
 *
 */
package org.sikuli.api.robot.desktop;


import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.GraphicsDevice;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;

import org.sikuli.api.robot.Env;
import org.sikuli.api.robot.Key;
import org.sikuli.api.robot.KeyModifier;
import org.sikuli.api.robot.OS;

class AWTRobot extends Robot {
	final static int MAX_DELAY = 60000;
	public static double DelayBeforeDrop = 0.3;
	public static double DelayAfterDrag = 0.3;


	public enum KeyMode {
		PRESS_ONLY, RELEASE_ONLY, PRESS_RELEASE
	};

	GraphicsDevice gdev;
	public AWTRobot(GraphicsDevice gdev) throws AWTException{
		super(gdev);   
		this.gdev = gdev;
	}

	public AWTRobot() throws AWTException {
	}


	public Point getOrigin(){
		return gdev.getDefaultConfiguration().getBounds().getLocation();
	}

	public void delay(int ms){
		if(ms<0)
			ms = 0;
		while(ms>MAX_DELAY){
			super.delay(MAX_DELAY);
			ms -= MAX_DELAY;
		}
		super.delay(ms);
	}

	public BufferedImage captureScreen(Rectangle rect){
		return createScreenCapture(rect);
	}


	public void pressModifiers(int modifiers){
		if((modifiers & KeyModifier.SHIFT) != 0) keyPress(KeyEvent.VK_SHIFT);
		if((modifiers & KeyModifier.CTRL) != 0) keyPress(KeyEvent.VK_CONTROL);
		if((modifiers & KeyModifier.ALT) != 0) keyPress(KeyEvent.VK_ALT);
		if((modifiers & KeyModifier.META) != 0){
			if( Env.getOS() == OS.WINDOWS )
				keyPress(KeyEvent.VK_WINDOWS);
			else
				keyPress(KeyEvent.VK_META);
		}
	}

	public void releaseModifiers(int modifiers){
		if((modifiers & KeyModifier.SHIFT) != 0) keyRelease(KeyEvent.VK_SHIFT);
		if((modifiers & KeyModifier.CTRL) != 0) keyRelease(KeyEvent.VK_CONTROL);
		if((modifiers & KeyModifier.ALT) != 0) keyRelease(KeyEvent.VK_ALT);
		if((modifiers & KeyModifier.META) != 0){ 
			if( Env.getOS() == OS.WINDOWS )
				keyRelease(KeyEvent.VK_WINDOWS);
			else
				keyRelease(KeyEvent.VK_META);
		}
	}

	protected void doType(KeyMode mode, int... keyCodes) {
		if(mode==KeyMode.PRESS_ONLY){
			for(int i=0;i<keyCodes.length;i++){
				keyPress(keyCodes[i]);
			}
		}
		else if(mode==KeyMode.RELEASE_ONLY){
			for(int i=0;i<keyCodes.length;i++){
				keyRelease(keyCodes[i]);
			}
		}
		else{
			for(int i=0;i<keyCodes.length;i++)
				keyPress(keyCodes[i]);
			for(int i=0;i<keyCodes.length;i++)
				keyRelease(keyCodes[i]);
		}
	}

	public void typeChar(char character, KeyMode mode) {
		doType(mode, Key.toJavaKeyCode(character));
	}

	public Object getDevice(){
		return null;
	}

}
