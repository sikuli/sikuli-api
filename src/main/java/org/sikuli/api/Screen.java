package org.sikuli.api;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public interface Screen {
	
	// x, y, width, height should be within the bounds
	public BufferedImage getScreenshot(int x, int y, int width, int height);
	
	// size of the screen
	public Dimension getSize();
}
