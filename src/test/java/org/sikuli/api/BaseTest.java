package org.sikuli.api;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import org.apache.log4j.BasicConfigurator;

class BaseTest {
	
	static {		
		BasicConfigurator.configure();
	}

	protected DesktopScreenRegion createTestScreenRegionFrom(String name) throws IOException{
		BufferedImage testScreenImage = ImageIO.read(this.getClass().getResource(name));
		DesktopScreenRegion r = new DesktopScreenRegion();
		r.setScreen(new StaticImageScreen(testScreenImage));
		return r;
	}
	
}
