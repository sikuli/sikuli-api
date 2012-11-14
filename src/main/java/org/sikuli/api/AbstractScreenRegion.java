package org.sikuli.api;

import java.awt.Rectangle;

import com.google.common.base.Objects;

abstract public class AbstractScreenRegion implements ScreenRegion {
	private int x;
	private int y;
	private int width;
	private int height;
	private double score;

	private Screen screen;	
	
	public AbstractScreenRegion(Screen screen) {
		setX(0);
		setY(0);		
		setWidth(screen.getSize().width);
		setHeight(screen.getSize().height);
		setScreen(screen);
	}

	
	@Override
	public Rectangle getBounds(){
		return new Rectangle(getX(),getY(),getWidth(),getHeight());
	}
	
	@Override
	public void setBounds(Rectangle newBounds){
		setX(newBounds.x);
		setY(newBounds.y);
		setWidth(newBounds.width);
		setHeight(newBounds.height);		
	}

	@Override
	public ScreenRegion getRelativeScreenRegion(int xoffset, int yoffset, int width, int height){
		return new DefaultScreenRegion(this, xoffset, yoffset, width, height);
	}
	
	@Override
	public ScreenLocation getRelativeScreenLocation(int xoffset, int yoffset){
		return new DefaultScreenLocation(screen, getX() + xoffset, getY() + yoffset);
	}
	
	@Override
	public ScreenLocation getCenter() {
		return getRelativeScreenLocation(getWidth()/2, getHeight()/2);
	}

	
	@Override
	public String toString(){
		return Objects.toStringHelper(this).add("screen", screen).				
				add("x",x).add("y",y).add("width",width).add("height",height).toString();			
	}
	
	@Override	
	public Screen getScreen() {
		return screen;
	}
	
	@Override
	public double getScore() {
		return score;
	}

	@Override
	public void setScore(double score) {
		this.score = score;
	}


	@Override
	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}


}
