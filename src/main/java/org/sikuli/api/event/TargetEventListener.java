package org.sikuli.api.event;

import java.util.EventListener;


interface VisualEventListener extends EventListener{
}

/**
 * Interface for a screen region to register to receive notifications when a target has
 * appeared, vanished, or moved within this screen region
 * 
 * @author Tom Yeh (tom.yeh@colorado.edu)
 *
 */
public interface TargetEventListener extends VisualEventListener {
	
	/**
	 * Invoked when a target has appeared in a screen region
	 * 
	 * event.getTarget() returns the target that has just appeared
	 * event.getScreenRegion() returns the screen region where the target is being watched
	 * event.getTargetRegion() returns the screen region currently occupied by the target
	 * 
	 * @param event
	 */
	public void targetAppeared(TargetEvent event);

	/**
	 * Invoked when a target has vanished from a screen region
	 * 
	 * @param event
	 * 
	 * event.getTarget() returns the target that has just vanished
	 * event.getScreenRegion() returns the screen region where the target is being watched
	 * event.getTargetRegion() returns the screen region last occupied by the target before it vanished.
	 */
	public void targetVanished(TargetEvent event);
	
	/**
	 * Invoked when a target has moved to another location in a screen region
	 * 
	 * @param event
	 * 
	 * event.getTarget() returns the target that has just moved
	 * event.getScreenRegion() returns the screen region where the target is being watched
	 * event.getTargetRegion() returns the screen region currently occupied by the target (i.e., the region
	 * the target has just moved to).
	 * 
	 */
	public void targetMoved(TargetEvent event);
}