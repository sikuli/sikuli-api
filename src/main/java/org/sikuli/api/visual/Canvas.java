package org.sikuli.api.visual;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import org.sikuli.api.Location;
import org.sikuli.api.Region;
import org.sikuli.api.visual.CanvasBuilder.ElementAdder;
import org.sikuli.api.visual.element.BoxElement;
import org.sikuli.api.visual.element.CircleElement;
import org.sikuli.api.visual.element.DotElement;
import org.sikuli.api.visual.element.Element;
import org.sikuli.api.visual.element.ImageElement;
import org.sikuli.api.visual.element.LabelElement;

import com.google.common.collect.Lists;
/**
 * Canvas class is the abstract base class for all canvas context.
 *
 */
abstract public class Canvas {

	private final List<Element> elements = 	Lists.newArrayList();
	
	
	public void add(Element element){
		elements.add(element);
	}
	
	/**
	 * Adds a circle at the specified location with the specified radius.
	 * 
	 * @param screenLocation the specified screen location.
	 * @param radius the specified radius of the circle.
	 * @return
	 */
	public StyleBuilder addCircle(Location screenLocation, int radius){		
		CircleElement newElement = new CircleElement();		
		newElement.x = screenLocation.getX() - radius;
		newElement.y = screenLocation.getY() - radius;
		newElement.width = 2 * radius;
		newElement.height = 2 * radius;			
		return addElement(newElement);
	}
	/**
	 * Adds a dot at the specified location.
	 * 
	 * @param screenLocation the specified screen location.
	 * @return
	 */
	public StyleBuilder addDot(Location screenLocation) {
		DotElement newElement = new DotElement();		
		newElement.x = screenLocation.getX();
		newElement.y = screenLocation.getY();
		newElement.width = 0;
		newElement.height = 0;			
		return addElement(newElement);
	}

	/**
	 * Adds an image at the specified location.
	 * 
	 * @param screenLocation the specified screen location.
	 * @param image the specified image.
	 * @return
	 */
	@Deprecated
	public StyleBuilder addImage(Location screenLocation, BufferedImage image){		
		ImageElement newElement = new ImageElement();		
		newElement.x = screenLocation.getX();
		newElement.y = screenLocation.getY();
		newElement.setImage(image);
		return addElement(newElement);
	}
	
	public ElementAdder add(){
		return (new CanvasBuilder(this)).new ElementAdder();
	}
	
	/**
	 * Adds a box at the specified screen region.
	 * 
	 * @param screenRegion the specified screen region.
	 * @return
	 */
	@Deprecated
	public StyleBuilder addBox(Region screenRegion){
		Rectangle r = screenRegion.getBounds();
		if (r == null){
			r = new Rectangle(0,0,0,0); 
		}
		BoxElement newElement = new BoxElement();		
		newElement.x = r.x;
		newElement.y = r.y;
		newElement.width = r.width;
		newElement.height = r.height;	
		return addElement(newElement);
	}
	/**
	 * Adds a label at the specified screen region with the specified text.
	 * 
	 * @param region the specified screen region.
	 * @param labelText the specified text.
	 * @return
	 */
	@Deprecated	
	public StyleBuilder addLabel(Region region, String labelText){
		Rectangle r = region.getBounds();
		LabelElement newElement = new LabelElement();
		newElement.setText(labelText);
		newElement.x = r.x + r.width/2;
		newElement.y = r.y + r.height/2;
		return addElement(newElement);
	}

	/**
	 * Adds a label at the specified screen location with the specified text.
	 * 
	 * @param location the specified screen location.
	 * @param labelText the specified text.
	 * @return
	 */
	@Deprecated
	public StyleBuilder addLabel(Location location, String labelText){
		LabelElement newElement = new LabelElement();
		newElement.setText(labelText);
		newElement.x = location.getX();
		newElement.y = location.getY();
		return addElement(newElement);
	}

	private StyleBuilder addElement(Element element){
		getElements().add(element);
		return new StyleBuilder(this, element);
	}

	/**
	 * Removes all canvas elements.
	 * @return this Canvas object.
	 */
	public Canvas clear() {
		getElements().clear();
		return this;
	}

	/**
	 * Displays a canvas for the specified duration. 
	 * This method blocks the calling thread for the specified duration.
	 * 
	 * @param seconds the specified duration in seconds.
	 */
	public void display(int seconds){
		display((double)seconds);
	}
	/**
	 * Displays a canvas for the specified duration.
	 * This method blocks the calling thread for the specified duration.
	 * 
	 * @param seconds the specified duration in seconds.
	 */

	abstract public void display(double seconds);
	/**
	 * Displays the canvas and blocks the calling thread, 
	 * while running the passed runnable on a background thread.
	 * When the passed Runnable completes, this method returns immediately and hides the canvas.
	 * 
	 * @param runnable A runnable to run in the background.
	 */
	abstract public void displayWhile(Runnable runnable);
	/**
	 * Shows a canvas. This is a non-blocking method.
	 * The canvas should be hidden using the {@link #hide()} method. 
	 */
	abstract public void show();
	/**
	 * Clears the shown canvas.
	 */
	abstract public void hide();

	abstract public BufferedImage createImage();
	/**
	 * Returns a list of Element objects associated with this Canvas.
	 * 
	 * @return a List of Element objects.
	 */
	protected List<Element> getElements() {
		return elements;
	}

}
