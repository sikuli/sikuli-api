package org.sikuli.api;

import java.awt.image.BufferedImage;

import org.sikuli.api.DefaultScreenRegion;

/**
 * StaticImageScreenRegion class behaves like a ScreenRegion but it always
 * provides a given image as the screenshot. Thus, all operations on it will
 * be applied to the image. It is useful for debugging in a headless mode
 * by supplying an image as a mock screenshot rather than taking a real
 * screenshot.
 */
public class StaticImageScreenRegion extends DefaultScreenRegion{
	public StaticImageScreenRegion(BufferedImage image){
		super(new StaticImageScreen(image));
	}
}