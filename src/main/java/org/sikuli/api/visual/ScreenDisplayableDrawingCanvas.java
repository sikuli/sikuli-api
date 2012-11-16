package org.sikuli.api.visual;

import java.util.List;

import com.google.common.collect.Lists;

abstract class ScreenDisplayableDrawingCanvas extends DrawingCanvas {
	
	
	static class NoOpScreenDisplayable implements ScreenDisplayable {
		@Override
		public void displayOnScreen() {
		}

		@Override
		public void hideFromScreen() {
		}		
	}	
	
	abstract protected ScreenDisplayable createScreenDisplayable(Element element);

	public void displayForSeconds(int seconds){

		List<ScreenDisplayable> displayableList = Lists.newArrayList();
		for (Element element : elements){
			displayableList.add(createScreenDisplayable(element));
		}

		for (ScreenDisplayable d : displayableList){
			d.displayOnScreen();
		}

		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
		}

		for (ScreenDisplayable d : displayableList){
			d.hideFromScreen();
		}

	}
}