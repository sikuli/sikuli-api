package org.sikuli.api.visual;

import java.awt.Color;


import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolo.util.PBounds;
import edu.umd.cs.piccolox.nodes.PShadow;

class LabelOverlay extends ScreenOverlayWindow{
		
		private final Color SHADOW_PAINT = new Color(10, 10, 10, 200);

		public LabelOverlay(String labelText, int x, int y){
			super();
			
			// Example: http://code.google.com/p/piccolo2d/source/browse/piccolo2d.java/trunk/examples/src/main/java/edu/umd/cs/piccolo/examples/ShadowExample.java?spec=svn698&r=698

			final PText txt = new PText(labelText);
			txt.setTextPaint(Color.black);
			txt.setPaint(Color.yellow);
			
			PNode labelNode = new PNode();
			labelNode.setPaint(Color.yellow);
			labelNode.addChild(txt);
			labelNode.setHeight(txt.getHeight()+2);
			labelNode.setWidth(txt.getWidth()+4);
			txt.setOffset(2,1);
			
			int blurRadius = 4;
			PShadow shadowNode = new PShadow(labelNode.toImage(), SHADOW_PAINT, blurRadius );
			
			int tx = 5;
			int ty = 5;
			labelNode.setOffset(tx,ty);
			shadowNode.setOffset(tx - (2 * blurRadius) + 1.0d, ty - (2 * blurRadius) + 1.0d);

            getCanvas().getLayer().addChild(shadowNode);
            getCanvas().getLayer().addChild(labelNode);			
			
			PBounds b = txt.getBounds();			
			setSize((int)b.width+25, (int)b.height+25);
			setLocation(x-tx,y-ty);
		}

	}