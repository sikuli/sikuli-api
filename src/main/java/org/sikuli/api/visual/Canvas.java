package org.sikuli.api.visual;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import org.sikuli.api.Location;
import org.sikuli.api.Region;
import org.sikuli.api.visual.element.BoxElement;
import org.sikuli.api.visual.element.CircleElement;
import org.sikuli.api.visual.element.DotElement;
import org.sikuli.api.visual.element.Element;
import org.sikuli.api.visual.element.ImageElement;
import org.sikuli.api.visual.element.LabelElement;

import com.google.common.collect.Lists;

abstract public class Canvas {

	private final List<Element> elements = 	Lists.newArrayList();;
	
	public StyleBuilder addCircle(Location screenLocation, int radius){		
		CircleElement newElement = new CircleElement();		
		newElement.x = screenLocation.getX() - radius;
		newElement.y = screenLocation.getY() - radius;
		newElement.width = 2 * radius;
		newElement.height = 2 * radius;			
		return addElement(newElement);
	}
	
	public StyleBuilder addDot(Location screenLocation) {
		DotElement newElement = new DotElement();		
		newElement.x = screenLocation.getX();
		newElement.y = screenLocation.getY();
		newElement.width = 0;
		newElement.height = 0;			
		return addElement(newElement);
	}

	
	public StyleBuilder addImage(Location screenLocation, BufferedImage image){		
		ImageElement newElement = new ImageElement();		
		newElement.x = screenLocation.getX();
		newElement.y = screenLocation.getY();
		newElement.image = image;
		return addElement(newElement);
	}

	public StyleBuilder addBox(Region screenRegion){
		Rectangle r = screenRegion.getBounds();
		BoxElement newElement = new BoxElement();		
		newElement.x = r.x;
		newElement.y = r.y;
		newElement.width = r.width;
		newElement.height = r.height;			
		return addElement(newElement);
	}
	
	public StyleBuilder addLabel(Region region, String labelText){
		Rectangle r = region.getBounds();
		LabelElement newElement = new LabelElement();
		newElement.text = labelText;
		newElement.x = r.x + r.width/2;
		newElement.y = r.y + r.height/2;
		return addElement(newElement);
	}
	
	public StyleBuilder addLabel(Location location, String labelText){
		LabelElement newElement = new LabelElement();
		newElement.text = labelText;
		newElement.x = location.getX();
		newElement.y = location.getY();
		return addElement(newElement);
	}

	private StyleBuilder addElement(Element element){
		getElements().add(element);
		return new StyleBuilder(this, element);
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
