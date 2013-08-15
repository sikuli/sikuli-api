package org.sikuli.api;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.sikuli.api.Screen;

class StaticImageScreen implements Screen {
	
	static private BufferedImage crop(BufferedImage src, int x, int y, int width, int height){
	    BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	    Graphics g = dest.getGraphics();
	    g.drawImage(src, 0, 0, width, height, x, y, x + width, y + height, null);
	    g.dispose();
	    return dest;
	}

	
	final private BufferedImage image;
	StaticImageScreen(BufferedImage image){
		this.image = image;
	}
	
	@Override
	public BufferedImage getScreenshot(int x, int y, int width, int height) {
		BufferedImage regionImage = crop(image, x,y,width,height);
		return regionImage;
	}

	@Override
	public Dimension getSize(){
		return new Dimension(image.getWidth(),image.getHeight());
	}
}