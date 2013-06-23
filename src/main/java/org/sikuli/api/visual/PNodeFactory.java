package org.sikuli.api.visual;

import java.awt.BasicStroke;
import java.awt.Color;

import org.sikuli.api.visual.element.BoxElement;
import org.sikuli.api.visual.element.CircleElement;
import org.sikuli.api.visual.element.DotElement;
import org.sikuli.api.visual.element.Element;
import org.sikuli.api.visual.element.ImageElement;
import org.sikuli.api.visual.element.LabelElement;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.nodes.PShadow;

class PNodeFactory {

	static public PNode createFrom(Element element){
		Class<? extends Element> clazz = element.getClass();
		if (clazz == LabelElement.class){
			return createFrom((LabelElement)element);
		}else if (clazz == BoxElement.class){
			return createFrom((BoxElement) element);
		}else if (clazz == CircleElement.class){
			return createFrom((CircleElement) element);
		}else if (clazz == ImageElement.class){
			return createFrom((ImageElement) element);
		}else if (clazz == DotElement.class){
			return createFrom((DotElement) element);
		}
		return new PNode();
	}

	static public PNode createFrom(LabelElement element){
		final PText txt = new PText(element.text);
		txt.setTextPaint(Color.black);
		txt.setPaint(element.backgroundColor);
		txt.setTextPaint(element.color);
		txt.setFont(txt.getFont().deriveFont(element.fontSize));

		PNode labelNode = new PNode();
		labelNode.setPaint(element.backgroundColor);		
		labelNode.addChild(txt);
		labelNode.setHeight(txt.getHeight()+2);
		labelNode.setWidth(txt.getWidth()+4);
		txt.setOffset(2,1);
		
		applyAlignment(labelNode, element);		
		return applyTransparencyAndShadow(labelNode, element);
	}	
	
	static public PNode createFrom(DotElement element){
		PPath p = PPath.createEllipse(0,0,4,4);
		p.setStrokePaint(element.lineColor);
		p.setPaint(element.lineColor);		
		p.setStroke(new BasicStroke(element.lineWidth));
		
		PNode foregroundNode = new PNode();
		foregroundNode.addChild(p);
		foregroundNode.setHeight(p.getHeight());
		foregroundNode.setWidth(p.getWidth());
		foregroundNode.setOffset(element.x-2, element.y-2);		

		return applyTransparencyAndShadow(foregroundNode, element);

	}
	
	static public PNode createFrom(CircleElement element){
		PPath p = PPath.createEllipse(0,0,element.width,element.height);
		p.setStrokePaint(element.lineColor);
		p.setPaint(null);		
		p.setStroke(new BasicStroke(element.lineWidth));

		PNode foregroundNode = new PNode();
		foregroundNode.addChild(p);
		foregroundNode.setHeight(p.getHeight());
		foregroundNode.setWidth(p.getWidth());		
		foregroundNode.setOffset(element.x, element.y);

		return applyTransparencyAndShadow(foregroundNode, element);
	}

	static public PNode createFrom(BoxElement element){
		PPath p = PPath.createRectangle(0,0,element.width,element.height);
		p.setStrokePaint(element.lineColor);
		p.setPaint(null);		
		p.setStroke(new BasicStroke(element.lineWidth));

		PNode foregroundNode = new PNode();
		foregroundNode.addChild(p);
		foregroundNode.setHeight(p.getHeight());
		foregroundNode.setWidth(p.getWidth());
		foregroundNode.setOffset(element.x, element.y);

		return applyTransparencyAndShadow(foregroundNode, element);
	}
	
	static public PNode createFrom(ImageElement element){
		PImage p = new PImage(element.image);

		PNode foregroundNode = new PNode();
		foregroundNode.addChild(p);
		foregroundNode.setHeight(p.getHeight());
		foregroundNode.setWidth(p.getWidth());
		foregroundNode.setOffset(element.x, element.y);

		applyAlignment(foregroundNode, element);		
		return applyTransparencyAndShadow(foregroundNode, element);
	}

	static private void applyAlignment(PNode node, Element element){
		double width = node.getWidth();
		double height = node.getHeight();
		
		double x = element.x; 
		double y = element.y;
		
		if (element.verticalAlignment == Element.VerticalAlignment.TOP){
			y = element.y;
		}else if (element.verticalAlignment == Element.VerticalAlignment.MIDDLE){
			y = element.y - height/2;		
		}else if (element.verticalAlignment == Element.VerticalAlignment.BOTTOM){
			y = element.y - height;
		}
		
		if (element.horizontalAlignment == Element.HorizontalAlignment.RIGHT){
			x = element.x - width;
		}else if (element.horizontalAlignment == Element.HorizontalAlignment.LEFT){
			x = element.x;
		}else if (element.horizontalAlignment == Element.HorizontalAlignment.CENTER){
			x = element.x - width / 2;
		}
		node.setOffset(x,y);
	}

	static private PNode applyTransparencyAndShadow(PNode node, Element element){
		PNode shadowedNode = addShadow(node);		
		shadowedNode.setTransparency(element.transparency);
		return shadowedNode;
	}

	
	static private final Color SHADOW_PAINT = new Color(10, 10, 10, 200);
	static private PNode addShadow(PNode contentNode){

		PNode contentNodeWithShadow = new PNode();

		double xoffset = contentNode.getXOffset();
		double yoffset = contentNode.getYOffset();

		int blurRadius = 4;
		int tx = 5;
		int ty = 5;

		PShadow shadowNode = new PShadow(contentNode.toImage(), SHADOW_PAINT, blurRadius );		
		contentNode.setOffset(tx, ty);
		shadowNode.setOffset(tx - (2 * blurRadius) + 1.0d, ty - (2 * blurRadius) + 1.0d);	
		contentNodeWithShadow.addChild(shadowNode);
		contentNodeWithShadow.addChild(contentNode);		      
//		contentNodeWithShadow.setOffset(xoffset - tx  - blurRadius, yoffset - ty - blurRadius);
		contentNodeWithShadow.setOffset(xoffset - tx - 1, yoffset - ty - 1);
		contentNodeWithShadow.setBounds(0,0, contentNode.getWidth() + 2*blurRadius + tx, contentNode.getHeight() + 2*blurRadius + ty);
		return contentNodeWithShadow;
	}
}