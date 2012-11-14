package org.sikuli.api;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public interface Screen {
	
	public String GET_SCREENSHOT = "screenshot";
	public String GET_SIZE = "size";
	
	
	// x, y, width, height should be within the bounds
	public BufferedImage getScreenshot(int x, int y, int width, int height);
	
	// size of the screen
	public Dimension getSize();
}
