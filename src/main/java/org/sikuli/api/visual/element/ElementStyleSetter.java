package org.sikuli.api.visual.element;

import java.awt.Color;
/**
 * A StyleBuilder is used to to ease style building the Canvas.
 *
 */
public class ElementStyleSetter {

	final private Element element;
	
	/**
	 * Constructs a new StyleBuilder whose Canvas and Element specified with the arguments
	 * of the same name.
	 * 
	 * @param canvas the specified Canvas.
	 * @param element the specified Element.
	 */
	public ElementStyleSetter(Element element) {
		this.element = element;
	}
	/**
	 * Sets the line color of this canvas element.
	 * 
	 * @param color the specified color.
	 * @return this StyleBuilder.
	 */
	public ElementStyleSetter lineColor(Color color){
		element.setLineColor(color);
		return this;
	}
	/**
	 * Sets the foreground color of this canvas element.
	 * 
	 * @param color the specified color.
	 * @return this StyleBuilder.
	 */
	public ElementStyleSetter color(Color color){
		element.setColor(color);
		return this;
	}
	
	public ElementStyleSetter fontSize(float fontSize) {
		element.setFontSize(fontSize);
		return this;
	}
	
	/**
	 * Sets the background color of this canvas element.
	 * 
	 * @param color the specified color.
	 * @return this StyleBuilder.
	 */
	public ElementStyleSetter backgroundColor(Color color) {
		element.setBackgroundColor(color);
		return this;
	}		
	/**
	 * Sets the transparency (alpha component) value of this canvas element.
	 * 
	 * @param f the specified transparency value [0..1].
	 * @return this StyleBuilder.
	 */
	public ElementStyleSetter transparency(float f) {
		element.setTransparency(f);
		return this;
	}
	/**
	 * Sets the font size color of this canvas element.
	 * 
	 * @param size the specified font size in points.
	 * @return this StyleBuilder.
	 */
	public ElementStyleSetter withFontSize(int size){
		element.setFontSize(size);
		return this;
	}
	/**
	 * Sets the line width of this canvas element.
	 * 
	 * @param width the specified line width in points.
	 * @return this StyleBuilder.
	 */
	public ElementStyleSetter lineWidth(int width){
		element.setLineWidth(width);
		return this;
	}
	/**
	 * Sets the vertical alignment of this canvas element in the middle.
	 * 
	 * @return this StyleBuilder.
	 */
	public ElementStyleSetter verticalAlignmentMiddle(){
		element.verticalAlignment = Element.VerticalAlignment.MIDDLE;
		return this;
	}
	/**
	 * Sets the vertical alignment of this canvas element at the top.
	 * 
	 * @return this StyleBuilder.
	 */
	public ElementStyleSetter verticalAlignmentTop(){
		element.verticalAlignment = Element.VerticalAlignment.TOP;
		return this;
	}
	/**
	 * Sets the vertical alignment of this canvas element at the bottom.
	 * 
	 * @return this StyleBuilder.
	 */
	public ElementStyleSetter verticalAlignmentBottom(){
		element.verticalAlignment = Element.VerticalAlignment.BOTTOM;
		return this;
	}
	/**
	 * Sets the horizontal alignment of this canvas element on the left.
	 * 
	 * @return this StyleBuilder.
	 */
	public ElementStyleSetter horizontalAlignmentLeft(){
		element.horizontalAlignment = Element.HorizontalAlignment.LEFT;
		return this;
	}
	/**
	 * Sets the horizontal alignment of this canvas element at the center.
	 * 
	 * @return this StyleBuilder.
	 */
	public ElementStyleSetter horizontalAlignmentCenter(){
		element.horizontalAlignment = Element.HorizontalAlignment.CENTER;
		return this;
	}
	/**
	 * Sets the horizontal alignment of this canvas element on the right.
	 * 
	 * @return this StyleBuilder.
	 */
	public ElementStyleSetter horizontalAlignmentRight(){
		element.horizontalAlignment = Element.HorizontalAlignment.RIGHT;
		return this;
	}

}