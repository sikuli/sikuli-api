package org.sikuli.api;

import java.awt.Rectangle;
/**
 *  The Relative class provides new regions relative to the region of a screen region object.
 *
 */
public class Relative{
	/**
	 * A RelativeScreenRegionBuilder represents a copy of the original ScreenRegion. 
	 *
	 */
	static public class RelativeScreenRegionBuilder{	
		private ScreenRegion screenRegion;
		/**
		 * Constructs a new RelativeScreenRegionBuilder that is a copy of the specified ScreenRegion.
		 * 
		 * @param screenRegion the ScreenRegion to which a relative region is created.
		 */
		public RelativeScreenRegionBuilder(ScreenRegion screenRegion) {
			this.screenRegion = screenRegion;
		}

		//
		// Utility methods for obtaining relative regions
		//

		/**
		 * Returns a new RelativeScreenRegionBuilder object to the offset of the ScreenRegion 
		 * 
		 * @param x x offset
		 * @param y y offset
		 * @return a new RelativeScreenRegionBuilder object to the offset of this ScreenRegion.
		 */
		public RelativeScreenRegionBuilder offset(int x, int y){
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(x, y, screenRegion.getBounds().width, screenRegion.getBounds().height));
		}
		/**
		 * Returns a new RelativeScreenRegionBuilder object relative to the right of this ScreenRegion 
		 * by the specified amount.
		 * 
		 * @param amount the pixel amount to move to the right.
		 * @return a new RelativeScreenRegionBuilder object to the right of this ScreenRegion.
		 */
		public RelativeScreenRegionBuilder right(int amount){
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(screenRegion.getBounds().width, 0, amount, screenRegion.getBounds().height));
		}
		/**
		 * Returns a new RelativeScreenRegionBuilder object relative to the left of this ScreenRegion 
		 * by the specified amount.
		 * 
		 * @param amount the pixel amount to move to the left.
		 * @return a new RelativeScreenRegionBuilder object to the left of this ScreenRegion.
		 */
		public RelativeScreenRegionBuilder left(int amount){
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(-amount, 0, amount, screenRegion.getBounds().height));
		}
		/**
		 * Returns a new RelativeScreenRegionBuilder object that is above this ScreenRegion 
		 * by the specified amount.
		 * 
		 * @param amount the pixel amount to go above.
		 * @return a new RelativeScreenRegionBuilder object that is above this ScreenRegion.
		 */
		public RelativeScreenRegionBuilder above(int amount){
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(0, -amount, screenRegion.getBounds().width, amount));
		}
		/**
		 * Returns a new RelativeScreenRegionBuilder object that is below this ScreenRegion 
		 * by the specified amount.
		 * 
		 * @param amount the pixel amount to go below.
		 * @return a new RelativeScreenRegionBuilder object that is below this ScreenRegion.
		 */
		public RelativeScreenRegionBuilder below(int amount){
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(0, screenRegion.getBounds().height, screenRegion.getBounds().width, amount));
		}
		/**
		 * Returns a new RelativeScreenRegionBuilder object that is shorter than this ScreenRegion 
		 * by the specified amount. That is it, it decreases the height of the height of the relative region by the
		 * specified amount.
		 * 
		 * @param amount the pixel amount to decrease the height of this ScreenRegion.
		 * @return a new RelativeScreenRegionBuilder object that is shorter than this ScreenRegion.
		 */
		public RelativeScreenRegionBuilder shorter(int amount){
			return taller(-amount);
		}
		/**
		 * Returns a new RelativeScreenRegionBuilder object that is taller than this ScreenRegion 
		 * by the specified amount. That is it, it increases the height of the relative region by the
		 * specified amount.
		 * 
		 * @param amount the pixel amount to increase the height of this ScreenRegion.
		 * @return a new RelativeScreenRegionBuilder object that is taller than this ScreenRegion.
		 */
		public RelativeScreenRegionBuilder taller(int amount){
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(0, -amount/2, screenRegion.getBounds().width, screenRegion.getBounds().height + amount));
		}
		/**
		 * Returns a new RelativeScreenRegionBuilder object that is narrower than this ScreenRegion 
		 * by the specified amount. That is it, it decreases the width of the relative region by the
		 * specified amount.
		 * 
		 * @param amount the pixel amount to decrease the width of this ScreenRegion.
		 * @return a new RelativeScreenRegionBuilder object that is narrower than this ScreenRegion.
		 */
		public RelativeScreenRegionBuilder narrower(int amount){
			return wider(-amount);
		}
		/**
		 * Returns a new RelativeScreenRegionBuilder object that is wider than this ScreenRegion 
		 * by the specified amount. That is it, it increases the width of the relative region by the
		 * specified amount.
		 * 
		 * @param amount the pixel amount to increase the width of this ScreenRegion.
		 * @return a new RelativeScreenRegionBuilder object that is wider than this ScreenRegion.
		 */
		public RelativeScreenRegionBuilder wider(int amount){
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(-amount/2, 0, screenRegion.getBounds().width + amount, screenRegion.getBounds().height));
		}
		
		/**
		 * Returns a new RelativeScreenRegionBuilder object that is a region relative to the 
		 * upper-left corner (origin) of this ScreenRegion.
		 * 
		 * @param region the region that is relative to this ScreenRegion
		 * @return a new RelativeScreenRegionBuilder object that is relative to this ScreenRegion.
		 */
		public RelativeScreenRegionBuilder region(Region region) {
			Rectangle r = region.getBounds();
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(r.x,r.y,r.width,r.height));
		}
		/**
		 * Returns a new RelativeScreenRegionBuilder object that is a region relative to the 
		 * upper-left corner (origin) of this ScreenRegion. The region is specified as 
		 * the x, y location of its upper-left corner relative to the upper-left corner of 
		 * the original ScreenRegion. 
		 * 
		 * @param x x location of the derived region
		 * @param y y location lowerof the derived region
		 * @param width width of the derived region
		 * @param height height of the derived region
		 * @return a new RelativeScreenRegionBuilder object that is relative to this ScreenRegion.
		 */		
		public RelativeScreenRegionBuilder region(int x, int y, int width, int height) {
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(x,y,width,height));
		}
		
		/**
		 * Returns a new RelativeScreenRegionBuilder object that is a region relative to the 
		 * upper-left corner (origin) of the ScreenRegion. The region is specified as the
		 * the start and end x locations and y locations in ratios with respect to the 
		 * width and height of the original ScreenRegion. (0,0) refers to the upper-left
		 * corner and (1,1) refers to the lower-right corner.
		 * 
		 * @param xmin start x location
		 * @param ymin start y location
		 * @param xmax end x location
		 * @param ymax end y location
		 * @return a new RelativeScreenRegionBuilder object that is relative to this ScreenRegion.
		 */		
		public RelativeScreenRegionBuilder region(double xmin, double ymin, double xmax, double ymax){
			Rectangle r = screenRegion.getBounds();
			int x = (int) (1.0 * r.x + xmin * r.width);
			int y = (int) (1.0 * r.y + ymin * r.height);
			int w = (int) (1.0 * (xmax - xmin) * r.width);
			int h = (int) (1.0 * (ymax - ymin) * r.height);
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(x,y,w,h));
		}
		
		//
		// Utility methods for obtaining relative locations
		//
		/**
		 * Returns a new RelativeScreenLocationBuilder object that corresponds to the center of
		 * this ScreenRegion.
		 * 
		 * @return a new RelativeScreenLocationBuilder object that represents the center 
		 * of this ScreenRegion.
		 */
		public RelativeScreenLocationBuilder center(){
			return new RelativeScreenLocationBuilder(screenRegion.getCenter());
		}
		/**
		 * Returns a new RelativeScreenLocationBuilder object that corresponds to the top-left corner of
		 * this ScreenRegion.
		 * 
		 * @return a new RelativeScreenLocationBuilder object that represents the top-left corner  
		 * of this ScreenRegion.
		 */
		public RelativeScreenLocationBuilder topLeft(){
			return new RelativeScreenLocationBuilder(screenRegion.getRelativeScreenLocation(0,0));
		}
		/**
		 * Returns a new RelativeScreenLocationBuilder object that corresponds to the top-center of
		 * this ScreenRegion.
		 * 
		 * @return a new RelativeScreenLocationBuilder object that represents the top-center  
		 * of this ScreenRegion.
		 */
		public RelativeScreenLocationBuilder topCenter() {
			Rectangle r = screenRegion.getBounds();
			return new RelativeScreenLocationBuilder(screenRegion.getRelativeScreenLocation(r.width/2,0));
		}
		/**
		 * Returns a new RelativeScreenLocationBuilder object that corresponds to the top-right corner of
		 * this ScreenRegion.
		 * 
		 * @return a new RelativeScreenLocationBuilder object that represents the top-right corner  
		 * of this ScreenRegion.
		 */
		public RelativeScreenLocationBuilder topRight(){
			Rectangle r = screenRegion.getBounds();
			return new RelativeScreenLocationBuilder(screenRegion.getRelativeScreenLocation(r.width,0));
		}
		/**
		 * Returns a new RelativeScreenLocationBuilder object that corresponds to the bottom-right corner of
		 * this ScreenRegion.
		 * 
		 * @return a new RelativeScreenLocationBuilder object that represents the bottom-right corner  
		 * of this ScreenRegion.
		 */
		public RelativeScreenLocationBuilder bottomRight(){
			Rectangle r = screenRegion.getBounds();
			return new RelativeScreenLocationBuilder(screenRegion.getRelativeScreenLocation(r.width,r.height));
		}
		/**
		 * Returns a new RelativeScreenLocationBuilder object that corresponds to the bottom-center of
		 * this ScreenRegion.
		 * 
		 * @return a new RelativeScreenLocationBuilder object that represents the bottom-center 
		 * of this ScreenRegion.
		 */
		public RelativeScreenLocationBuilder bottomCenter(){
			Rectangle r = screenRegion.getBounds();
			return new RelativeScreenLocationBuilder(screenRegion.getRelativeScreenLocation(r.width/2,r.height));
		}		
		/**
		 * Returns a new RelativeScreenLocationBuilder object that corresponds to the bottom-left corner of
		 * this ScreenRegion.
		 * 
		 * @return a new RelativeScreenLocationBuilder object that represents the bottom-left corner  
		 * of this ScreenRegion.
		 */
		public RelativeScreenLocationBuilder bottomLeft(){
			Rectangle r = screenRegion.getBounds();
			return new RelativeScreenLocationBuilder(screenRegion.getRelativeScreenLocation(0,r.height));
		}
		/**
		 * Returns a new RelativeScreenLocationBuilder object that corresponds to the middle-left location of
		 * this ScreenRegion.
		 * 
		 * @return a new RelativeScreenLocationBuilder object that represents the middle-left location  
		 * of this ScreenRegion.
		 */
		public RelativeScreenLocationBuilder middleLeft() {
			Rectangle r = screenRegion.getBounds();
			return new RelativeScreenLocationBuilder(screenRegion.getRelativeScreenLocation(0,r.height/2));
		}
		/**
		 * Returns a new RelativeScreenLocationBuilder object that corresponds to the middle-right location of
		 * this ScreenRegion.
		 * 
		 * @return a new RelativeScreenLocationBuilder object that represents the middle-right location  
		 * of this ScreenRegion.
		 */
		public RelativeScreenLocationBuilder middleRight() {
			Rectangle r = screenRegion.getBounds();
			return new RelativeScreenLocationBuilder(screenRegion.getRelativeScreenLocation(r.width,r.height/2));
		}
		
		/**
		 * Returns the ScreenRegion of this RelativeScreenRegionBuilder.
		 * 
		 * @return the ScreenRegion of this RelativeScreenRegionBuilder.
		 */
		public ScreenRegion getScreenRegion(){
			return screenRegion;
		}

		
	}
	
	/**
	 * A RelativeScreenLocationBuilder represents a copy of the original ScreenLocation. 
	 *
	 */
	static public class RelativeScreenLocationBuilder{
		private ScreenLocation screenLocation;
		/**
		 * Constructs a new RelativeScreenLocationBuilder that is a copy of the specified ScreenLocation.
		 * 
		 * @param screenLocation the ScreenLocation to which a relative screen location is created.
		 */
		public RelativeScreenLocationBuilder(ScreenLocation screenLocation) {
			this.screenLocation = screenLocation;
		}
		/**
		 * Returns the ScreenLocation of this RelativeScreenLocationBuilder.
		 * 
		 * @return the ScreenLocation of this RelativeScreenLocationBuilder
		 */
		public ScreenLocation getScreenLocation(){
			return screenLocation;
		}
		/**
		 * Returns a new RelativeScreenLocationBuilder object that is above this ScreenLocation 
		 * by the specified amount.
		 * 
		 * @param amount the pixel amount to move above.
		 * @return a new RelativeScreenLocationBuilder object that is above this ScreenLocation.
		 */
		public RelativeScreenLocationBuilder above(int amount) {
			return new RelativeScreenLocationBuilder(screenLocation.getRelativeScreenLocation(0,  -amount));		
		}
		/**
		 * Returns a new RelativeScreenLocationBuilder object that is below this ScreenLocation 
		 * by the specified amount.
		 * 
		 * @param amount the pixel amount to move below.
		 * @return a new RelativeScreenLocationBuilder object that is below this ScreenLocation.
		 */
		public RelativeScreenLocationBuilder below(int amount) {
			return new RelativeScreenLocationBuilder(screenLocation.getRelativeScreenLocation(0,  amount));		
		}
		/**
		 * Returns a new RelativeScreenLocationBuilder object relative to the left of this ScreenLocation 
		 * by the specified amount.
		 * 
		 * @param amount the pixel amount to move to the left.
		 * @return a new RelativeScreenLocationBuilder object to the left of this ScreenLocation.
		 */
		public RelativeScreenLocationBuilder left(int amount) {
			return new RelativeScreenLocationBuilder(screenLocation.getRelativeScreenLocation(-amount,  0));		
		}
		/**
		 * Returns a new RelativeScreenLocationBuilder object relative to the right of this ScreenLocation 
		 * by the specified amount.
		 * 
		 * @param amount the pixel amount to move to the right.
		 * @return a new RelativeScreenLocationBuilder object to the right of this ScreenLocation.
		 */
		public RelativeScreenLocationBuilder right(int amount) {
			return new RelativeScreenLocationBuilder(screenLocation.getRelativeScreenLocation(amount, 0));		
		}		
	}
	/**
	 * Returns a new RelativeScreenRegionBuilder object using the specified ScreenRegion object.
	 * 
	 * @param screenRegion the original ScreenRegion object for which a relative screen region will be created.
	 * @return RelativeScreenRegionBuilder object used for creating relative regions.
	 */
	public static RelativeScreenRegionBuilder to(ScreenRegion screenRegion){
		return new RelativeScreenRegionBuilder(screenRegion);
	}
	/**
	 * Returns a new RelativeScreenLocationBuilder object using the specified ScreenLocation object.
	 * 
	 * @param screenLocation the original ScreenLocation object for which a relative screen location will be created.
	 * @return RelativeScreenLocationBuilder object used for creating relative regions.
	 */
	public static RelativeScreenLocationBuilder to(ScreenLocation screenLocation){
		return new RelativeScreenLocationBuilder(screenLocation);
	}


}