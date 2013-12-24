package org.sikuli.api.visual.element;

import java.awt.image.BufferedImage;
import java.util.EventListener;

import javax.swing.event.EventListenerList;


public class ImageElement extends Element {


	public interface Listener extends EventListener {
		public void imageUpdated(BufferedImage newImage);
	}

	private BufferedImage image;

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		fireUpdate(image);
	}

	public void addListener(Listener l) {
		listenerList.add(Listener.class, l);
	}

	public void removeListener(Listener l) {
		listenerList.remove(Listener.class, l);
	}

	EventListenerList listenerList = new EventListenerList();
	protected void fireUpdate(BufferedImage newImage) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length-2; i>=0; i-=2) {
			if (listeners[i]==Listener.class) {
				// Lazily create the event:
				((Listener)listeners[i+1]).imageUpdated(newImage);
			}
		}
	}

}