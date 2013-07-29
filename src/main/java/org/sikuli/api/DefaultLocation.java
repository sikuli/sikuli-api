package org.sikuli.api;

import java.awt.Image;

public class DefaultLocation implements Location {

	private int x;
	private int y;
	public DefaultLocation(int x, int y){
		this.x = x;
		this.y = y;
	}		

	@Override
	public int getX() {
		return x;
	}
	@Override
	public void setX(int x) {
		this.x = x;

	}
	@Override
	public int getY() {
		return y;
	}
	@Override
	public void setY(int y) {
		this.y = y;
	}

}
