package org.sikuli.api;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import org.apache.log4j.BasicConfigurator;

class BaseTest {
	
	static {		
		BasicConfigurator.configure();
	}

	protected ScreenRegion createTestScreenRegionFrom(String name) throws IOException{
		BufferedImage testScreenImage = ImageIO.read(this.getClass().getResource(name));
		ScreenRegion r = new DefaultScreenRegion(new StaticImageScreen(testScreenImage));
		return r;
	}
	
}
