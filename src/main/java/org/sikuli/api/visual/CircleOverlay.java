package org.sikuli.api.visual;

import java.awt.Color;


import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolox.nodes.PShadow;

class CircleOverlay extends ScreenOverlayWindow{

	private final Color SHADOW_PAINT = new Color(10, 10, 10, 200);
	
	
	CircleOverlay(int x, int y, int width, int height){
		super();
		
		final PPath circle = PPath.createEllipse(1,1,20,20);
		circle.setStrokePaint(Color.red);
		circle.setPaint(null);	
					
		PNode foregroundNode = new PNode();
		foregroundNode.addChild(circle);
		foregroundNode.setHeight(circle.getHeight()+4);
		foregroundNode.setWidth(circle.getWidth()+4);
		circle.setOffset(2,2);
		
		int blurRadius = 4;
		PShadow shadowNode = new PShadow(foregroundNode.toImage(), SHADOW_PAINT, blurRadius );
		
		
		int tx = 5;
		int ty = 5;
		foregroundNode.setOffset(tx,ty);
		shadowNode.setOffset(tx - (2 * blurRadius) + 1.0d, ty - (2 * blurRadius) + 1.0d);			
		
		getCanvas().getLayer().addChild(foregroundNode);
		getCanvas().getLayer().addChild(shadowNode);

		setLocation(x-tx-4,y-ty-4);
		setSize(40,40);
	}
}