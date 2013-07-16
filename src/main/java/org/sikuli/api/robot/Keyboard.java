package org.sikuli.api.robot;

import org.sikuli.api.ScreenRegion;

public interface Keyboard {
	
	/**
	 * Clears the clipboard, performs the "copy" keyboard shortcut, then attempts to return the 
	 * clipboard contents as a String. 
	 * 
	 * Returns <code>null</code> if the clipboard couldn't be converted to a string or was empty.
	 * 
	 * @return The clipboard contents converted to a String or <code>null</code>
	 */
	public String copy();
	
	/**
	 * Copies the content of a screen region into the clipboard. It highlights the content of the given 
	 * screen region by clicking on its upper-left corner and moving the mouse to the lower-right corner, 
	 * then it performs the keyboard shortcut to copy the content of the screen region.
	 * @param screenRegion The screen region to be copied
	 */
	public void copyRegion(ScreenRegion screenRegion);
	
	public void paste(String text);
	public void type(String text);

	/**
	 * press down the key (given by the key code) on the underlying device.
	 * The code depend on the type of the device.
	 */
	public void keyDown(int keycode);

	/**
	 * release the key (given by the key code) on the underlying device.
	 * The code depend on the type of the device.
	 */
	public void keyUp(int keycode);

	public void keyDown(String keys);


	public void keyUp();

	public void keyUp(String keys);

}
