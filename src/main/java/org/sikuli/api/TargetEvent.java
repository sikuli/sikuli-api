package org.sikuli.api;


/**
 * Event that a target has appeared, vanished, or moved in a specific screen region
 * 
 * @author tomyeh
 *
 */
public class TargetEvent {
	
	/**
	 * Constructor
	 * 
	 * @param screenRegion
	 * @param target
	 * @param targetRegion
	 */
	public TargetEvent(Target target, ScreenRegion screenRegion,
			ScreenRegion targetRegion) {
		super();
		this.screenRegion = screenRegion;
		this.target = target;
		this.targetRegion = targetRegion;
	}
	final private ScreenRegion screenRegion;
	final private Target target;
	final private ScreenRegion targetRegion;

	public ScreenRegion getScreenRegion() {
		return screenRegion;
	}
	public Target getTarget() {
		return target;
	}
	public ScreenRegion getTargetRegion() {
		return targetRegion;
	}


}
