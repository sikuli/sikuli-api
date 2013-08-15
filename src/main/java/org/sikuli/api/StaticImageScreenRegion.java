package org.sikuli.api;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import org.sikuli.api.DefaultScreenRegion;

public class StaticImageScreenRegion extends DefaultScreenRegion{
	public StaticImageScreenRegion(BufferedImage image){
		super(new StaticImageScreen(image));
	}
}