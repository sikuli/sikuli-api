package org.sikuli.api.visual;

import java.awt.image.BufferedImage;

public class RefreshableImage {
	private BufferedImage image;
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
}
