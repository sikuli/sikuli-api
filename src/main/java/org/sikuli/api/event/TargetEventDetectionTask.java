package org.sikuli.api.event;

import org.sikuli.api.Relative;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TargetEventDetectionTask implements EventDetectionTask{

	private static Logger logger = LoggerFactory.getLogger(TargetEventDetectionTask.class);


	/* 
	 * equal when all fields refer to same object instances
	 */
	@Override
	public boolean equals(Object o) {
		TargetEventDetectionTask w = (TargetEventDetectionTask) o;
		return screenRegion == w.screenRegion &&
				target == w.target &&
				listener == w.listener;
	}

	public TargetEventDetectionTask(ScreenRegion screenRegion, Target target,
			TargetEventListener listener) {
		super();
		this.screenRegion = screenRegion;
		this.target = target;
		this.listener = listener;
		this.lastTargetRegion = null;
	}
	public void setLastTargetRegion(ScreenRegion lastTargetRegion) {
		this.lastTargetRegion = lastTargetRegion;
	}
	public TargetEvent createTargetEvent(){
		return new TargetEvent(target, screenRegion, lastTargetRegion);
	}

	final private ScreenRegion screenRegion;
	final private Target target;
	final private TargetEventListener listener;
	private ScreenRegion lastTargetRegion;
	
	public void run(){
		ScreenRegion currentTargetRegion = null;
		
		if (lastTargetRegion == null){					

			currentTargetRegion = screenRegion.find(target);
			if (currentTargetRegion != null){
				// appear
				setLastTargetRegion(currentTargetRegion);	
				listener.targetAppeared(createTargetEvent());
				logger.debug(target  + " has appeared");
			}else{
				// still vanished
			}

		}else{				

			if (isTargetVisibleAt(target, lastTargetRegion)){
				// still there

			}else{

				currentTargetRegion = screenRegion.find(target);

				if (currentTargetRegion == null){
					// vanished
					listener.targetVanished(createTargetEvent());
					setLastTargetRegion(null);
					logger.debug(target  + " has vanished");
				}else{
					// moved
					setLastTargetRegion(currentTargetRegion);
					listener.targetMoved(createTargetEvent());
					logger.debug(target  + " has moved to " + currentTargetRegion);
				}
			}
		}
	}


	static private boolean isTargetVisibleAt(Target target, ScreenRegion region){

		if (region == null)
			return false;

		ScreenRegion neighborRegion = Relative.to(region).taller(2).wider(2).getScreenRegion();
		ScreenRegion newRegion = neighborRegion.find(target);	
		if (newRegion == null){
			logger.debug(target  + " is not visible at " + newRegion);
			return false;
		}else{
			logger.debug(target  + " is visible at " + newRegion);
			return true;
		}
	}
}