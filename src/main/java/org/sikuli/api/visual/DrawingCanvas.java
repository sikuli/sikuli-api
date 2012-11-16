package org.sikuli.api.visual;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import com.google.common.collect.Lists;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.nodes.PShadow;


class Element {
	public int x;
	public int y;
	public int width;
	public int height;	

	public Color lineColor = Color.red;
	public Color color = Color.black;
	public int lineWidth = 2;
	
	public PNode createPNode(){
		return new PNode();
	}
}

class BoxElement extends Element {
}

class LabelElement extends Element {
	public String text;
}


class PNodeFactory {
	
	static public PNode createFrom(Element element){
		Class<? extends Element> clazz = element.getClass();
		if (clazz == LabelElement.class){
			return createFrom((LabelElement)element);
		}else if (clazz == BoxElement.class){
			return createFrom((BoxElement) element);
		}	
		return new PNode();
	}
	
	static public PNode createFrom(LabelElement element){
		final PText txt = new PText(element.text);
		txt.setTextPaint(Color.black);
		txt.setPaint(Color.yellow);
		txt.setTextPaint(element.color);
		
		PNode labelNode = new PNode();
		labelNode.setPaint(Color.yellow);
		labelNode.addChild(txt);
		labelNode.setHeight(txt.getHeight()+2);
		labelNode.setWidth(txt.getWidth()+4);
		txt.setOffset(2,1);
	
		
		labelNode.setOffset(element.x, element.y);
		return addShadow(labelNode);
	}
	
	static public PNode createFrom(BoxElement element){
		PPath p = PPath.createRectangle(1,1,element.width,element.height);
		p.setStrokePaint(element.lineColor);
		p.setPaint(null);		
		p.setStroke(new BasicStroke(element.lineWidth));
		

		PNode foregroundNode = new PNode();
		foregroundNode.addChild(p);
		foregroundNode.setHeight(p.getHeight()+4);
		foregroundNode.setWidth(p.getWidth()+4);
		p.setOffset(2,2);
		
		foregroundNode.setOffset(element.x, element.y);
		
		return addShadow(foregroundNode);
	}
	
	static public void setStyle(){
		
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
		contentNodeWithShadow.setOffset(xoffset - tx  - blurRadius, yoffset - ty - blurRadius);
		contentNodeWithShadow.setBounds(0,0, contentNode.getWidth() + 2*blurRadius + tx, contentNode.getHeight() + 2*blurRadius + ty);
        return contentNodeWithShadow;
	}
}


public class DrawingCanvas {

	protected final List<Element> elements = 	Lists.newArrayList();;

	static public class BoxPlacementBuilder{
		
		private Element element;
		public BoxPlacementBuilder(Element newElement) {
			this.element = newElement;
		}

		public StyleBuilder around(Rectangle r){
			element.x = r.x;
			element.y = r.y;
			element.width = r.width;
			element.height = r.height;			
			return new StyleBuilder(element);
		}

	}
	
	static public class PlacementBuilder{

		private Element element;
		public PlacementBuilder(Element newElement) {
			this.element = newElement;
		}


		public StyleBuilder above(Rectangle r){
			return null;
		}

		public StyleBuilder leftOf(Rectangle r, int distance){
			element.x = r.x - distance;
			element.y = r.y;
			return new StyleBuilder(element);
		}
		
		public StyleBuilder topOf(Rectangle r, int distance){
			element.x = r.x;
			element.y = r.y - distance;
			return new StyleBuilder(element);
		}
	}	

	static public class StyleBuilder {

		final private Element element;
		public StyleBuilder(Element element) {
			this.element = element;
		}

		public StyleBuilder withLineColor(Color color){
			element.lineColor = color;
			return this;
		}

		public StyleBuilder withColor(Color color){
			element.color = color;
			return this;
		}
		
		public StyleBuilder withLineWidth(int width){
			element.lineWidth = width;
			return this;
		}

	}

	public BoxPlacementBuilder addBox(){
		BoxElement newElement = new BoxElement();
		elements.add(newElement);
		return new BoxPlacementBuilder(newElement);		
	}

	public PlacementBuilder addLabel(String labelText){
		LabelElement newElement = new LabelElement();
		newElement.text = labelText;
		elements.add(newElement);
		return new PlacementBuilder(newElement);		
	}

	public void clear() {
		elements.clear();	
	}


}
