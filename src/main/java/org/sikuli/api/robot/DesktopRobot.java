/*
 * Copyright 2010-2011, Sikuli.org
 * Released under the MIT License.
 *
 */
package org.sikuli.api.robot;


import java.awt.Point;
import java.awt.Robot;
import java.awt.GraphicsDevice;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;

class DesktopRobot extends Robot {
	final static int MAX_DELAY = 60000;

	public enum KeyMode {
		PRESS_ONLY, RELEASE_ONLY, PRESS_RELEASE
	};

	GraphicsDevice gdev;
	public DesktopRobot(GraphicsDevice gdev) throws AWTException{
		super(gdev);   
		this.gdev = gdev;
	}

	public DesktopRobot() throws AWTException {
	}


	public Point getOrigin(){
		return gdev.getDefaultConfiguration().getBounds().getLocation();
	}

	public void smoothMove(Point dest){
		smoothMove(Env.getMouseLocation(), dest, (long)(Settings.MoveMouseDelay*1000L));
	}

	public void smoothMove(Point src, Point dest, long ms){
		if(ms == 0){
			mouseMove(dest.x, dest.y);
			return;
		}

		Animator aniX = new TimeBasedAnimator(
				new OutQuarticEase((float)src.x, (float)dest.x, ms));
		Animator aniY = new TimeBasedAnimator(
				new OutQuarticEase((float)src.y, (float)dest.y, ms));
		while(aniX.running()){
			float x = aniX.step();
			float y = aniY.step();
			mouseMove((int)x, (int)y);
			delay(50);
		}		
	}

	public void dragDrop(Point start, Point end, int steps, long ms, int buttons){
		mouseMove(start.x, start.y);
		mousePress(buttons);
		delay((int)(Settings.DelayAfterDrag*1000));
		waitForIdle();
		smoothMove(start, end, ms);
		delay((int)(Settings.DelayBeforeDrop*1000));
		mouseRelease(buttons);
		waitForIdle();
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
