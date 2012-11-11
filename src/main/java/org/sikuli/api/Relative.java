package org.sikuli.api;

import java.awt.Rectangle;

public class Relative{
	
	static public class RelativeScreenRegionBuilder{	
		private ScreenRegion screenRegion;
		public RelativeScreenRegionBuilder(ScreenRegion screenRegion) {
			this.screenRegion = screenRegion;
		}

		//
		// Utility methods for obtaining relative regions
		//
		public RelativeScreenRegionBuilder right(int amount){
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(screenRegion.getBounds().width, 0, amount, screenRegion.getBounds().height));
		}
		public RelativeScreenRegionBuilder left(int amount){
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(-amount, 0, amount, screenRegion.getBounds().height));
		}
		public RelativeScreenRegionBuilder above(int amount){
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(0, -amount, screenRegion.getBounds().width, amount));
		}
		public RelativeScreenRegionBuilder below(int amount){
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(0, screenRegion.getBounds().height, screenRegion.getBounds().width, amount));
		}
		public RelativeScreenRegionBuilder taller(int amount){
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(0, -amount/2, screenRegion.getBounds().width, screenRegion.getBounds().height + amount));
		}
		public RelativeScreenRegionBuilder wider(int amount){
			return new RelativeScreenRegionBuilder(screenRegion.getRelativeScreenRegion(-amount/2, 0, screenRegion.getBounds().width + amount, screenRegion.getBounds().height));
		}
		
		
		//
		// Utility methods for obtaining relative locations
		//
		public RelativeScreenLocationBuilder center(){
			return new RelativeScreenLocationBuilder(screenRegion.getCenter());
		}
		
		public RelativeScreenLocationBuilder topLeft(){
			return new RelativeScreenLocationBuilder(screenRegion.getScreenLocation(0,0));
		}

		public RelativeScreenLocationBuilder topRight(){
			Rectangle r = screenRegion.getBounds();
			return new RelativeScreenLocationBuilder(screenRegion.getScreenLocation(r.width,0));
		}
		
		public RelativeScreenLocationBuilder bottomRight(){
			Rectangle r = screenRegion.getBounds();
			return new RelativeScreenLocationBuilder(screenRegion.getScreenLocation(r.width,r.height));
		}

		public RelativeScreenLocationBuilder bottomLeft(){
			Rectangle r = screenRegion.getBounds();
			return new RelativeScreenLocationBuilder(screenRegion.getScreenLocation(0,r.height));
		}
						
		public ScreenRegion getScreenRegion(){
			return screenRegion;
		}
	}
	

	static public class RelativeScreenLocationBuilder{
		private ScreenLocation screenLocation;
		public RelativeScreenLocationBuilder(ScreenLocation screenLocation) {
			this.screenLocation = screenLocation;
		}
		public ScreenLocation getScreenLocation(){
			return screenLocation;
		}
		
		public RelativeScreenLocationBuilder above(int amount) {
			return new RelativeScreenLocationBuilder(screenLocation.getRelativeScreenLocation(0,  -amount));		
		}	
		public RelativeScreenLocationBuilder below(int amount) {
			return new RelativeScreenLocationBuilder(screenLocation.getRelativeScreenLocation(0,  amount));		
		}	
		public RelativeScreenLocationBuilder left(int amount) {
			return new RelativeScreenLocationBuilder(screenLocation.getRelativeScreenLocation(-amount,  0));		
		}	
		public RelativeScreenLocationBuilder right(int amount) {
			return new RelativeScreenLocationBuilder(screenLocation.getRelativeScreenLocation(amount, 0));		
		}		
	}
		
	public static RelativeScreenRegionBuilder to(ScreenRegion screenRegion){
		return new RelativeScreenRegionBuilder(screenRegion);
	}

}