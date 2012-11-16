package org.sikuli.api.visual;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

import com.google.common.collect.Lists;


class Element {
	public int x;
	public int y;
	public int width;
	public int height;	

	public Color lineColor = Color.red;
	public int lineThickness = 5;

}

class BoxElement extends Element {
}

class LabelElement extends Element {
	public String text;
}

public class DrawingCanvas {

	protected final List<Element> elements = 	Lists.newArrayList();;

	static public class PlacementBuilder{

		private Element element;
		public PlacementBuilder(Element newElement) {
			this.element = newElement;
		}

		public StyleBuilder around(Rectangle r){
			element.x = r.x;
			element.y = r.y;
			element.width = r.width;
			element.height = r.height;			
			return new StyleBuilder(element);
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

		public StyleBuilder withLineThickness(int thickness){
			element.lineThickness = thickness;
			return this;
		}

	}

	public PlacementBuilder addBox(){
		return addElement(new BoxElement());
	}

	public PlacementBuilder addLabel(String labelText){
		LabelElement newElement = new LabelElement();
		newElement.text = labelText;
		return addElement(newElement);
	}

	private PlacementBuilder addElement(Element newElement){
		elements.add(newElement);
		return new PlacementBuilder(newElement);		
	}

	public void clear() {
		elements.clear();	
	}
	

}
