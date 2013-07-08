package org.sikuli.api.visual.element;

import java.awt.Color;

public class BoxElement extends Element {
	@Override
	public void setColor(Color color){
		setLineColor(color);
	}
}
