package org.sikuli.api.robot;

public interface Keyboard {
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
