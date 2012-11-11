package org.sikuli.api;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;

public class MultiStateTarget extends Target {

	Map<Target, Object> states = new HashMap<Target, Object>();
	
	@Override
	protected List<ScreenRegion> getUnordredMatches(ScreenRegion screenRegion){
		final BufferedImage image = screenRegion.capture();
		
		// get matches for each state and add them to a combined list
		List<ScreenRegion> allMatches = Lists.newArrayList();
		for (Target t : states.keySet()){
			t.setLimit(getLimit());
			List<ScreenRegion> matches = t.getUnordredMatches(screenRegion);
			allMatches.addAll(matches);
		}
				
		// sort them by scores
		Collections.sort(allMatches, new Comparator<ScreenRegion>(){
			@Override
			public int compare(ScreenRegion arg0, ScreenRegion arg1) {
				return Doubles.compare(arg1.getScore(),arg0.getScore());
			}			
		});
		

		// add padding to account for mis-alignment
		for (ScreenRegion m : allMatches){
			Rectangle oldBounds = m.getBounds();
			Rectangle newBounds = new Rectangle();
			newBounds.width = oldBounds.width + 10;
			newBounds.height = oldBounds.height + 10;
			newBounds.x = oldBounds.x - 5;
			newBounds.y = oldBounds.y - 5;
			m.setBounds(newBounds);
		}
			
		
		int n = Math.min(getLimit(), allMatches.size());
		List<ScreenRegion> results = allMatches.subList(0, n);
		
		for (ScreenRegion r : results){
			// copy state information
			r.getStates().putAll(states);		
		}
		
		return results;
	}

	public void addState(Target target, Object state){
		states.put(target,  state);
	}


}