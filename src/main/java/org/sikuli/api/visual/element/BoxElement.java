package org.sikuli.api.visual.element;

import java.awt.Color;

public class BoxElement extends Element {
	
	public BoxElement(){		
		setBackgroundColor(null);
	}
	
	@Override
	public void setColor(Color color){
		setLineColor(color);
	}
}
