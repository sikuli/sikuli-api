package org.sikuli.api.visual;

import java.awt.image.BufferedImage;

import org.sikuli.api.Location;
import org.sikuli.api.Region;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.visual.element.BoxElement;
import org.sikuli.api.visual.element.CircleElement;
import org.sikuli.api.visual.element.DotElement;
import org.sikuli.api.visual.element.Element;
import org.sikuli.api.visual.element.ElementStyleSetter;
import org.sikuli.api.visual.element.Element.VerticalAlignment;
import org.sikuli.api.visual.element.ImageElement;
import org.sikuli.api.visual.element.LabelElement;
import org.sikuli.api.visual.element.Element.HorizontalAlignment;

public class CanvasBuilder {

	private Canvas canvas;

	public CanvasBuilder(Canvas canvas) {
		this.canvas = canvas;
	}
	
	public class ElementAdder {
		public ElementAreaSetter box() {
			BoxElement newElement = new BoxElement();				
			addElement(newElement);
			return new ElementAreaSetter(newElement);
		}
		public ElementAreaSetter circle() {
			CircleElement newElement = new CircleElement();				
			addElement(newElement);
			return new ElementAreaSetter(newElement);
		}
		public ElementPointSetter dot() {
			DotElement newElement = new DotElement();				
			addElement(newElement);
			return new ElementPointSetter(newElement);
		}
		public LabelElementPlacementSetter label(String labelText){
			LabelElement newElement = new LabelElement();
			newElement.setText(labelText);
			addElement(newElement);
			return new LabelElementPlacementSetter(newElement);
		}
		public ElementPointSetter image(BufferedImage image) {
			ImageElement newElement = new ImageElement();
			newElement.setImage(image);
			addElement(newElement);
			return new ElementPointSetter(newElement);
		}
	}
	
	public class LabelElementPlacementSetter extends ElementSetter {
		public LabelElementPlacementSetter(Element element) {
			super(element);
		}
		
		public Element at(Location p){
			element.x = p.getX();
			element.y = p.getY();
			return element;
		}
		
		public Element at(int x, int y){
			element.x = x;
			element.y = y;
			return element;
		}

		
		public Element inside(Region r) {
			element.x = r.getX() + r.getWidth()/2 - element.width/2;
			element.y = r.getY() + r.getHeight()/2 - element.height/2;
			element.horizontalAlignment = HorizontalAlignment.CENTER;
			element.verticalAlignment = VerticalAlignment.MIDDLE;
			return element;
		}

		public Element below(Region r) {
			element.x = r.getX() + r.getWidth()/2 - element.width/2;
			element.y = r.getY() + r.getHeight();
			element.horizontalAlignment = HorizontalAlignment.CENTER;
			return element;
		}
		
		public Element above(Region r) {
			element.x = r.getX() + r.getWidth()/2 - element.width/2;
			element.y = r.getY();
			element.horizontalAlignment = HorizontalAlignment.CENTER;
			element.verticalAlignment = VerticalAlignment.BOTTOM;
			return element;
		}
		
		public Element left(Region r) {
			element.x = r.getX();
			element.y = r.getY() + r.getHeight()/2;
			element.horizontalAlignment = HorizontalAlignment.RIGHT;
			element.verticalAlignment = VerticalAlignment.MIDDLE;			
			return element;
		}
		
		public Element right(Region r) {
			element.x = r.getX() + r.getWidth();
			element.y = r.getY() + r.getHeight()/2;
			element.horizontalAlignment = HorizontalAlignment.LEFT;
			element.verticalAlignment = VerticalAlignment.MIDDLE;			
			return element;
		}
		
	}
	
	public class ElementPointSetter {
		private Element element;		
		public ElementPointSetter(Element element) {
			this.element = element;
		}
		
		public Element at(int x, int y){
			element.x = x;
			element.y = y;
			return element;
		}
		
		public Element at(Location p){
			element.x = p.getX();
			element.y = p.getY();
			return element;
		}
		
		public Element centeredAt(int x, int y){			
			element.x = x - element.width/2;
			element.y = y - element.height/2;
			return element;
		}		
		
		public Element centeredAt(Region r) {
			element.x = r.getX() + r.getWidth()/2 - element.width/2;
			element.y = r.getY() + r.getHeight()/2 - element.height/2;
			element.horizontalAlignment = HorizontalAlignment.CENTER;
			element.verticalAlignment = VerticalAlignment.MIDDLE;
			return element;
		}

		public Element centeredAbove(Region r) {
			// TODO Auto-generated method stub
			return null;
		}

		public RelativeToRegion relativeTo(Region r) {
			return new RelativeToRegion(element, r.getX(), r.getY(), r.getWidth(), r.getHeight());
		}
		
	}
	
	public class ElementSetter {
		protected Element element;
		public ElementSetter(Element element){
			this.element = element;
		}
	}
	
	public class RelativeToRegion extends ElementSetter{
		int x;
		int y;
		int width;
		int height;
		public RelativeToRegion(Element element, int x, int y, int width, int height) {
			super(element);
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;			
		}
		public RelativeToRegion above(int d){
			element.x = x;
			element.y = y - d;
			return this;
		}
		
		public ElementStyleSetter styleWith(){	
			return new ElementStyleSetter(element);
		}
	}
	
	public class ElementAreaSetter {
		private Element element;

		public ElementAreaSetter(Element element){
			this.element = element;
		}
		
		public ElementPointSetter size(int width, int height){
			element.width = width;
			element.height = height;
			return new ElementPointSetter(element);
		}
		
		public Element around(int x, int y, int width, int height){
			element.x = x;
			element.y = y;
			element.width = width;
			element.height = height;
			return element;
		}

		public Element around(Region r) {
			element.x = r.getX();
			element.y = r.getY();
			element.width = r.getWidth();
			element.height = r.getHeight();
			return element;
		}

	}
	
	private Element addElement(Element element){
		canvas.add(element);
		return element;
	}
	
}

