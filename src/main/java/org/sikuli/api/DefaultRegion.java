package org.sikuli.api;

import java.awt.Rectangle;

public class DefaultRegion implements Region {
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	public DefaultRegion(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public DefaultRegion(Rectangle bounds) {
		setBounds(bounds);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}

	@Override
	public void setBounds(Rectangle newBounds) {		
		this.x = newBounds.x;
		this.y = newBounds.y;
		this.width = newBounds.width;
		this.height = newBounds.height;
	}

}
