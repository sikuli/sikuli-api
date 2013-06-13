package org.sikuli.api.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;

public class VisualEventManager {

	// interval between two detections in ms
	private static final int DETECTION_INTERVAL = 500;

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

	public void addTargetEventListener(ScreenRegion screenRegion, Target target, TargetEventListener listener){
		detectionTaskList.add(new TargetEventDetectionTask(screenRegion,target,listener));		
	}

	public void removeTargetEventListener(ScreenRegion screenRegion, Target target, TargetEventListener listener){
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
					Thread.sleep(DETECTION_INTERVAL);
				} catch (InterruptedException e) {

				}

				for (EventDetectionTask task : detectionTaskList){											
					task.run();
				}
			}
		}

	}



}