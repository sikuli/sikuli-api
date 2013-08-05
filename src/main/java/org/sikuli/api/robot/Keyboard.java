package org.sikuli.api.robot;

import org.sikuli.api.ScreenRegion;
/**
 * Keyboard interface provides definitions for generating native keyboard events.
 *
 */
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
	 * 
	 * @param screenRegion The screen region to be copied
	 */
	public void copyRegion(ScreenRegion screenRegion);
	/**
	 * Gets plain text from the clipboard and performs the "paste" keyboard shortcut.
	 * 
	 * @param text the text to paste
	 */
	public void paste(String text);
	/**
	 * Performs keyboard typing of the text specified by the arguments of the same name.
	 * It presses and releases the given keys.
	 *  
	 * @param text the keys to type
	 */
	public void type(String text);

	/**
	 * Presses down the key (given by the key code) on the underlying device.
	 * The code depends on the type of the device.
	 * The keys should be released using the {@link #keyUp(int)} method.
	 * 
	 * @param keycode Key to press down (e.g. KeyEvent.VK_A)
	 */
	public void keyDown(int keycode);

	/**
	 * Releases the key (given by the key code) on the underlying device.
	 * The code depend on the type of the device.
	 * 
	 * @param keycode Key to release
	 */
	public void keyUp(int keycode);
	/**
	 * Presses down a series of keys specified by the String argument keys.
	 * The keys should be released using the {@link #keyUp(String)} method.
	 * 
	 * @param keys the keys to be typed down.
	 */
	public void keyDown(String keys);
	/**
	 * Releases all the held down keys.
	 */
	public void keyUp();
	/**
	 * Releases a series of keys specified by the String argument keys.
	 * The keys depend on the type of the device.
	 * 
	 * @param keys the keys to release.
	 */
	public void keyUp(String keys);

}
