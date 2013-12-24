package org.sikuli.api.visual.element;

import java.awt.Color;


public class DotElement extends Element {
	
	public DotElement(){
		super();
		setColor(Color.red);
	}
	
	@Override
	public void setLineColor(Color color){
		setColor(color);
	}

}