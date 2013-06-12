package org.sikuli.api.visual;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.visual.element.BoxElement;
import org.sikuli.api.visual.element.CircleElement;
import org.sikuli.api.visual.element.Element;
import org.sikuli.api.visual.element.ImageElement;
import org.sikuli.api.visual.element.LabelElement;

import com.google.common.collect.Lists;

abstract public class Canvas {

	private final List<Element> elements = 	Lists.newArrayList();;
	
	public class StyleBuilder {

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
		
		public StyleBuilder withFontSize(int size){
			element.fontSize = size;
			return this;
		}


		public StyleBuilder withLineWidth(int width){
			element.lineWidth = width;
			return this;
		}
		
		public void display(int seconds){
			Canvas.this.display(seconds);
		}

		public void display(double seconds){
			Canvas.this.display(seconds);
		}

	}
	
	public StyleBuilder addCircle(ScreenLocation screenLocation){		
		CircleElement newElement = new CircleElement();		
		newElement.x = screenLocation.getX() - 10;
		newElement.y = screenLocation.getY() - 10;
		newElement.width = 20;
		newElement.height = 20;			
		return addElement(newElement);
	}
	
	public StyleBuilder addImage(ScreenLocation screenLocation, BufferedImage image){		
		ImageElement newElement = new ImageElement();		
		newElement.x = screenLocation.getX();
		newElement.y = screenLocation.getY();
		newElement.image = image;
		return addElement(newElement);
	}

	public StyleBuilder addBox(ScreenRegion screenRegion){
		Rectangle r = screenRegion.getBounds();
		BoxElement newElement = new BoxElement();		
		newElement.x = r.x;
		newElement.y = r.y;
		newElement.width = r.width;
		newElement.height = r.height;			
		return addElement(newElement);
	}
	
	public StyleBuilder addLabel(ScreenRegion screenRegion, String labelText){
		return addLabel(screenRegion.getCenter(), labelText);
	}

	public StyleBuilder addLabel(ScreenLocation screenLocation, String labelText){
		LabelElement newElement = new LabelElement();
		newElement.text = labelText;
		newElement.x = screenLocation.getX();
		newElement.y = screenLocation.getY();
		return addElement(newElement);
	}

	private StyleBuilder addElement(Element element){
		getElements().add(element);
		return new StyleBuilder(element);
	}


	public Canvas clear() {
		getElements().clear();
		return this;
	}

	
	public void display(int seconds){
		display((double)seconds);
	}
	
	abstract public void display(double seconds);
	abstract public void displayWhile(Runnable runnable);
	abstract public void show();
	abstract public void hide();
	
	abstract public BufferedImage createImage();

	protected List<Element> getElements() {
		return elements;
	}

}
