package org.sikuli.api.event;

import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;


/**
 * Event that a target has appeared, vanished, or moved in a specific screen region
 * 
 * @author tomyeh
 *
 */
public class TargetEvent {
	
	/**
	 * Constructs a new TargetEvent from the specified Target and ScreenRegion, and targetRegion.
	 * 
	 * @param screenRegion the initial screen region where the target is being watched.
	 * @param target the target representing this event.
	 * @param targetRegion the new screen region that is currently occupied by the target.
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
	/**
	 * Returns the ScreenRegion object that represents the initial screen region 
	 * where the target is being watched.
	 * 
	 * @return ScreenRegion object of this TargetEvent.
	 */
	public ScreenRegion getScreenRegion() {
		return screenRegion;
	}
	/**
	 * Returns the Target object of this TargetEvent.
	 * 
	 * @return the Target object of this TargetEvent.
	 */
	public Target getTarget() {
		return target;
	}
	/**
	 * Returns the ScreenRegion object that represents the new screen region 
	 * that is currently occupied by the target.
	 * 
	 * @return the target ScreenRegion object of this TargetEvent.
	 */
	public ScreenRegion getTargetRegion() {
		return targetRegion;
	}


}
