package org.sikuli.api.robot.desktop;

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.event.KeyEvent;

import org.sikuli.api.APILogger;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Env;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;


public class DesktopKeyboard implements Keyboard {
	static private int modifiers;
	static private String _hold_keys = "";
	
	static private AWTRobot getRobot(){
		return AWTDesktop.getCurrentRobot();
	}
	

	public String copy() {
		Clipboard.clear();

		int mod = Env.getHotkeyModifier();
		AWTRobot robot = getRobot();
		robot.keyPress(mod);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(mod);

		String text = Clipboard.getText();
		APILogger.getLogger().copyPerformed(text);
		return text;
	}
	
	public void copyRegion(ScreenRegion screenRegion){
		Mouse mouse = new DesktopMouse();
		ScreenLocation start = screenRegion.getUpperLeftCorner();
		ScreenLocation end = screenRegion.getLowerRightCorner();
		mouse.move(start);
		mouse.press();
		mouse.move(end);
		copy();
		mouse.release();
	}
	
	public void paste(String text){
		checkNotNull(text);
		APILogger.getLogger().pastePerformed(text);

		Clipboard.putText(Clipboard.PLAIN, Clipboard.UTF8, 
				Clipboard.BYTE_BUFFER, text);
		int mod = Env.getHotkeyModifier();
		AWTRobot robot = getRobot();
		robot.keyPress(mod);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(mod);
	}

	public void type(String text){
		checkNotNull(text);
		APILogger.getLogger().typePerformed(text);

		AWTRobot robot = getRobot();
		for(int i=0; i < text.length(); i++){
			robot.pressModifiers(modifiers);
			robot.typeChar(text.charAt(i), AWTRobot.KeyMode.PRESS_RELEASE); 
			robot.releaseModifiers(modifiers);
			robot.delay(20);
		}
		robot.waitForIdle();
	}


	/**
	 * press down the key (given by the key code) on the underlying device.
	 * The code depend on the type of the device.
	 */
	public void keyDown(int keycode){
		getRobot().keyPress(keycode);
	}

	/**
	 * release the key (given by the key code) on the underlying device.
	 * The code depend on the type of the device.
	 */
	public void keyUp(int keycode){
		getRobot().keyRelease(keycode);
	}


	public void keyDown(String keys){
		if(keys != null){
			for(int i=0; i < keys.length(); i++){
				if(_hold_keys.indexOf(keys.charAt(i)) == -1){
					//Debug.log(5, "press: " + keys.charAt(i));
					getRobot().typeChar(keys.charAt(i), AWTRobot.KeyMode.PRESS_ONLY); 
					_hold_keys += keys.charAt(i);
				}
			}
			getRobot().waitForIdle();
			return;
		}
	}

	public void keyUp(){
		keyUp(null);
	}

	public void keyUp(String keys){
		if(keys == null)
			keys = _hold_keys;
		for(int i=0; i < keys.length(); i++){
			int pos;
			if( (pos=_hold_keys.indexOf(keys.charAt(i))) != -1 ){
				//Debug.log(5, "release: " + keys.charAt(i));
				getRobot().typeChar(keys.charAt(i), AWTRobot.KeyMode.RELEASE_ONLY); 
				_hold_keys = _hold_keys.substring(0,pos) + 
						_hold_keys.substring(pos+1);
			}
		}
		getRobot().waitForIdle();
	}
}
