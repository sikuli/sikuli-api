package org.sikuli.api;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

interface EventDetectionTask {
	public void run();
}

class TargetEventDetectionTask implements EventDetectionTask{


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
				//System.out.println(watchedTarget.getTarget() + " has appeared");
			}else{
				// still vanished
			}


		}else{				

			if (isTargetStillAtTheLastLocation()){
				// still appearing at the same location

			}else{

				currentTargetRegion = screenRegion.find(target);

				if (currentTargetRegion == null){
					// vanished
					listener.targetVanished(createTargetEvent());
					setLastTargetRegion(null);
					//System.out.println(watchedTarget.getTarget() + " has vanished");
				}else{
					// moved
					setLastTargetRegion(currentTargetRegion);
					listener.targetMoved(createTargetEvent());
					//System.out.println(watchedTarget.getTarget() + " has moved to " + currentMatch.getTopLeft());

				}
			}
		}

	}



	private boolean isTargetStillAtTheLastLocation(){

		if (lastTargetRegion == null)
			return false;

		// TODO: only need to find in a small area around the last location
		ScreenRegion r = screenRegion.find(target);
		if (r == null)
			return false;		

		ScreenLocation newLocation = Relative.to(r).topLeft().getScreenLocation();
		ScreenLocation lastLocation = Relative.to(lastTargetRegion).topLeft().getScreenLocation();

		boolean isXClose = Math.abs(newLocation.x - lastLocation.x) < 3;
		boolean isYClose = Math.abs(newLocation.y - lastLocation.y) < 3;

		return isXClose && isYClose;
	}
}


class VisualEventManager {

	// update is rare, could be done by any user thread that adds or removes items
	// iteration is often, but only done on the single thread, the event manager thread
	// write might happen during iteration
	private List<EventDetectionTask> detectionTaskList = new CopyOnWriteArrayList<EventDetectionTask>();

	private VisualEventManager(){
		Thread t = new TargetEventDispatherThread();
		t.setDaemon(true);
		t.start();
	}

	static public VisualEventManager getSingleton(){
		if (ref == null)
			ref = new VisualEventManager();		
		return ref;
	}

	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException(); 
	}
	static private VisualEventManager ref;

	void addTargetEventListener(ScreenRegion screenRegion, Target target, TargetEventListener listener){
		detectionTaskList.add(new TargetEventDetectionTask(screenRegion,target,listener));		
	}

	void removeTargetEventListener(ScreenRegion screenRegion, Target target, TargetEventListener listener){
		detectionTaskList.remove(new TargetEventDetectionTask(screenRegion,target,listener));
	}

	public void addStateChangeEventListener(ScreenRegion screenRegion, StateChangeListener listener) {
		detectionTaskList.add(new StateChangeEventDetectionTask(screenRegion,listener));
	}


	// Singlton
	class TargetEventDispatherThread extends Thread {

		@Override
		// TODO: should sleep instead of spinning when list is empty
		// use a producer/consumer
		// this consumes the watchedtargets in a blocking queue
		// the queue should be unbounded
		// on a separate thread, a timer thread, it periodically
		// put new watchedtargets in the queue
		// if there are still unprocessed ones, don't put in this round, 
		// try again next time interval
		// 
		public void run() {

			while (true){

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {

				}

				for (EventDetectionTask task : detectionTaskList){											
					task.run();
				}
			}



		}

	}


	
}