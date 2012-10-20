package org.sikuli.api.visual;

import java.awt.Color;


import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolox.nodes.PShadow;

class BoxOverlay extends ScreenOverlayWindow{
	private final Color SHADOW_PAINT = new Color(10, 10, 10, 200);
	BoxOverlay(int x, int y, int width, int height){
		super();

		final PPath p = PPath.createRectangle(1,1,width,height);
		p.setStrokePaint(Color.red);
		p.setPaint(null);	

		PNode foregroundNode = new PNode();
		foregroundNode.addChild(p);
		foregroundNode.setHeight(p.getHeight()+4);
		foregroundNode.setWidth(p.getWidth()+4);
		p.setOffset(2,2);

		int blurRadius = 4;
		PShadow shadowNode = new PShadow(foregroundNode.toImage(), SHADOW_PAINT, blurRadius );


		int tx = 5;
		int ty = 5;
		foregroundNode.setOffset(tx,ty);
		shadowNode.setOffset(tx - (2 * blurRadius) + 1.0d, ty - (2 * blurRadius) + 1.0d);			

		getCanvas().getLayer().addChild(foregroundNode);
		getCanvas().getLayer().addChild(shadowNode);

		setLocation(x-tx-4,y-ty-4);
		setSize(width+tx+8,height+ty+8);
	}

}
