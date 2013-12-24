package org.sikuli.api.visual.element;

import java.util.EventListener;

import javax.swing.event.EventListenerList;

public class LabelElement extends Element {
	private String text;
	
	public interface Listener extends EventListener {
		public void textUpdated(String newText);
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		fireUpdate(text);
	}
	
	public void addListener(Listener l) {
		listenerList.add(Listener.class, l);
	}

	public void removeListener(Listener l) {
		listenerList.remove(Listener.class, l);
	}

	EventListenerList listenerList = new EventListenerList();
	protected void fireUpdate(String newText) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i]==Listener.class) {
				// Lazily create the event:
				((Listener)listeners[i+1]).textUpdated(newText);
			}
		}
	}
}