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

	public Color lineColor = Color.red;
	public Color color = Color.black;
	public Color backgroundColor = Color.yellow;
	public int lineWidth = 2;
	public float fontSize = 12;
	public VerticalAlignment verticalAlignment = VerticalAlignment.TOP;
	public HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
	public float transparency = 1f;
	public PNode createPNode(){
		return new PNode();
	}
}