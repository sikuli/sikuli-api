package org.sikuli.api.visual.element;

import java.awt.Color;
import edu.umd.cs.piccolo.PNode;

public class Element {
	public enum VerticalAlignment {
		TOP,
		MIDDLE,
		BOTTOM
	};
	public enum HorizontalAlignment {
		LEFT,
		CENTER,
		RIGHT
	};	
	
	public int x;
	public int y;
	public int width;
	public int height;	

	private Color lineColor = Color.red;
	private Color color = Color.black;
	private Color backgroundColor = Color.yellow;
	private int lineWidth = 2;
	private float fontSize = 12;
	public VerticalAlignment verticalAlignment = VerticalAlignment.TOP;
	public HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
	private float transparency = 1f;
	
	public PNode createPNode(){
		return new PNode();
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getLineColor() {
		return lineColor;
	}
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public float getTransparency() {
		return transparency;
	}

	public void setTransparency(float transparency) {
		this.transparency = transparency;
	}
}