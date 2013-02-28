package org.sikuli.api.visual.element;

import java.awt.Color;

import edu.umd.cs.piccolo.PNode;

public class Element {
	public int x;
	public int y;
	public int width;
	public int height;	

	public Color lineColor = Color.red;
	public Color color = Color.black;
	public int lineWidth = 2;
	public float fontSize = 12;

	public PNode createPNode(){
		return new PNode();
	}
}