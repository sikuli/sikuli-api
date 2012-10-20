package org.sikuli.api;

import java.util.Map;
import java.util.Set;


/**
 * Event that a screen region has changed its state
 * 
 * null means the state is unknown
 * 
 * @author tomyeh
 *
 */
public class StateChangeEvent {
	public ScreenRegion getScreenRegion() {
		return screenRegion;
	}
	public Object getNewState() {
		return newState;
	}
	public Object getOldState() {
		return oldState;
	}
	public StateChangeEvent(ScreenRegion screenRegion, Object oldState,
			Object newState) {
		super();
		this.screenRegion = screenRegion;
		this.newState = newState;
		this.oldState = oldState;
	}
	final private ScreenRegion screenRegion;
	final private Object newState;
	final private Object oldState;
}

class StateChangeEventDetectionTask implements EventDetectionTask{

	public StateChangeEventDetectionTask(ScreenRegion screenRegion,
			StateChangeListener listener) {
		super();
		this.screenRegion = screenRegion;
		this.listener = listener;
	}

	final private ScreenRegion screenRegion;
	final private StateChangeListener listener;
	private Target previousStateTarget = null;

	@Override
	public void run() {

		Target topStateTarget = null;
		double topScore = 0;

		Map<Target, Object> states = screenRegion.getStates();		
		Set<Target> keySet = states.keySet();		
		for (Target target : keySet){	
			ScreenRegion match = screenRegion.find(target);
			if (match != null && match.getScore() > topScore){
				topStateTarget = target;
				topScore = match.getScore();
			}			
		}


		// if the current top state target is the same as the one before
		if (topStateTarget == previousStateTarget){
			// no state change
		}else{
			// state change
			Object oldState = previousStateTarget == null ? null : states.get(previousStateTarget);
			Object newState = topStateTarget == null ? null : states.get(topStateTarget);
			listener.stateChanged(new StateChangeEvent(screenRegion, oldState, newState));
			previousStateTarget = topStateTarget;
		}

	}


}
