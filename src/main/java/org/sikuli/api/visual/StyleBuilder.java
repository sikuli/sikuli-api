package org.sikuli.api.visual;

import java.awt.Color;

import org.sikuli.api.visual.element.Element;
/**
 * A StyleBuilder is used to to ease style building the Canvas.
 *
 */
public class StyleBuilder {

	final private Element element;
	final private Canvas canvas;
	/**
	 * Constructs a new StyleBuilder whose Canvas and Element specified with the arguments
	 * of the same name.
	 * 
	 * @param canvas the specified Canvas.
	 * @param element the specified Element.
	 */
	public StyleBuilder(Canvas canvas, Element element) {
		this.element = element;
		this.canvas = canvas;
	}
	/**
	 * Sets the line color of this canvas element.
	 * 
	 * @param color the specified color.
	 * @return this StyleBuilder.
	 */
	public StyleBuilder withLineColor(Color color){
		element.setLineColor(color);
		return this;
	}
	/**
	 * Sets the foreground color of this canvas element.
	 * 
	 * @param color the specified color.
	 * @return this StyleBuilder.
	 */
	public StyleBuilder withColor(Color color){
		element.setColor(color);
		return this;
	}
	/**
	 * Sets the background color of this canvas element.
	 * 
	 * @param color the specified color.
	 * @return this StyleBuilder.
	 */
	public StyleBuilder withBackgroundColor(Color color) {
		element.setBackgroundColor(color);
		return this;
	}		
	/**
	 * Sets the transparency (alpha component) value of this canvas element.
	 * 
	 * @param f the specified transparency value [0..1].
	 * @return this StyleBuilder.
	 */
	public StyleBuilder withTransparency(float f) {
		element.setTransparency(f);
		return this;
	}
	/**
	 * Sets the font size color of this canvas element.
	 * 
	 * @param size the specified font size in points.
	 * @return this StyleBuilder.
	 */
	public StyleBuilder withFontSize(int size){
		element.setFontSize(size);
		return this;
	}
	/**
	 * Sets the line width of this canvas element.
	 * 
	 * @param width the specified line width in points.
	 * @return this StyleBuilder.
	 */
	public StyleBuilder withLineWidth(int width){
		element.setLineWidth(width);
		return this;
	}
	/**
	 * Sets the vertical alignment of this canvas element in the middle.
	 * 
	 * @return this StyleBuilder.
	 */
	public StyleBuilder withVerticalAlignmentMiddle(){
		element.verticalAlignment = Element.VerticalAlignment.MIDDLE;
		return this;
	}
	/**
	 * Sets the vertical alignment of this canvas element at the top.
	 * 
	 * @return this StyleBuilder.
	 */
	public StyleBuilder withVerticalAlignmentTop(){
		element.verticalAlignment = Element.VerticalAlignment.TOP;
		return this;
	}
	/**
	 * Sets the vertical alignment of this canvas element at the bottom.
	 * 
	 * @return this StyleBuilder.
	 */
	public StyleBuilder withVerticalAlignmentBottom(){
		element.verticalAlignment = Element.VerticalAlignment.BOTTOM;
		return this;
	}
	/**
	 * Sets the horizontal alignment of this canvas element on the left.
	 * 
	 * @return this StyleBuilder.
	 */
	public StyleBuilder withHorizontalAlignmentLeft(){
		element.horizontalAlignment = Element.HorizontalAlignment.LEFT;
		return this;
	}
	/**
	 * Sets the horizontal alignment of this canvas element at the center.
	 * 
	 * @return this StyleBuilder.
	 */
	public StyleBuilder withHorizontalAlignmentCenter(){
		element.horizontalAlignment = Element.HorizontalAlignment.CENTER;
		return this;
	}
	/**
	 * Sets the horizontal alignment of this canvas element on the right.
	 * 
	 * @return this StyleBuilder.
	 */
	public StyleBuilder withHorizontalAlignmentRight(){
		element.horizontalAlignment = Element.HorizontalAlignment.RIGHT;
		return this;
	}
	/**
	 * Displays the canvas for the specified duration.
	 * 
	 * @param seconds the specified duration in seconds.
	 */
	public void display(int seconds){
		canvas.display(seconds);
	}
	/**
	 * Displays the canvas for the specified duration.
	 * 
	 * @param seconds the specified duration in seconds.
	 */
	public void display(double seconds){
		canvas.display(seconds);
	}



}