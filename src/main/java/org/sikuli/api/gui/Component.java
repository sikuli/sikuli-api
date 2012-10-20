package org.sikuli.api.gui;

import java.awt.Rectangle;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.sikuli.api.API;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.TextTarget;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.core.cv.ImageMask;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author Tom Yeh (tom.yeh@colorado.edu)
 *
 */
public class Component {	
	protected Component parent = null;
	protected String labelText = "";
	protected Label label = null;
	private ScreenRegion screenRegion;
	
	public Mouse getMouse(){
		if (parent != null){
			return parent.getMouse();
		}
		return null;		
	}
	
	public Keyboard getKeyboard(){
		if (parent != null){
			return parent.getKeyboard();
		}
		return null;
	}
	
	public Component(Component parent, String labelText){
		this.parent = parent;
		this.labelText = labelText;
	}	

	public Component(Component parent, ScreenRegion screenRegion){
		this.parent = parent;
		this.screenRegion = screenRegion;
	}	
	

	/**
	 * Gets the ScreenRegion of this component
	 * 
	 * @return ScreenRegion of this component or <code>null</code> if the component
	 * can not be found
	 */
	public ScreenRegion getScreenRegion(){
		if (screenRegion == null){
			screenRegion = find();
		}
		return screenRegion;
	}
	
	protected ScreenRegion find(){
		return screenRegion;
	}
	
	public boolean isVisible() {
		return getScreenRegion() != null;
	}

	public Component click(){
		ScreenRegion screenRegion = requireVisible();
		getMouse().click(screenRegion.getCenter());
		return this;
	}
	
	public Component rightClick(){
		ScreenRegion screenRegion = requireVisible();
		getMouse().rightClick(screenRegion.getCenter());
		return this;
	}

	public Component doubleClick(){
		ScreenRegion screenRegion = requireVisible();
		getMouse().doubleClick(screenRegion.getCenter());
		return this;
	}

	public Component drag(){
		ScreenRegion screenRegion = requireVisible();
		getMouse().drag(screenRegion.getCenter());
		return this;
	}
	
	public Component drop(){
		ScreenRegion screenRegion = requireVisible();
		getMouse().drop(screenRegion.getCenter());
		return this;
	}

	
	public Component pressAndReleaseKeys(String... keys){
		for (String key : keys){
			pressKey(key);
			releaseKey(key);
		}
		return this;
	}
	
	public Component pressKey(String key){
		getKeyboard().keyDown(key);
		return this;
	}
	
	public Component releaseKey(String key){
		getKeyboard().keyUp(key);
		return this;
	}
	
	public Component showBorder() {
		ScreenRegion screenRegion = requireVisible();
		//screenRegion.box(2000);
		return this;
	}


	public String getStylePath(){
		if (parent != null)
			return parent.getStylePath();
		return null;
	}
	
	public URL getExample(String name){
		return getClass().getResource(getStylePath() + "/" + name);
	}	
	
//	protected Label findLabelBelow(ScreenRegion origin, String labelText){
//		ScreenRegion belowRegion = origin.getBelow(30);
//		belowRegion.grow(10, 100, 0, 100);
//		
//		Component region = new Component(this, belowRegion);
//		Label label = region.label(labelText);
//		if (!label.isVisible())
//			return null;
//		
//		// verify that the label is centered w.r.t. origin
//		ScreenRegion labelRegion = label.getScreenRegion();		
//		ScreenLocation c = labelRegion.getCenter();
//		boolean isCentered = c.x >= origin.x
//				&& c.x <= (origin.x + origin.width);			
//		if (!isCentered)
//			return null;		
//		
//		return label;
//	}
	
	static protected ScreenRegion findScreenRegionWithLabelLeft(List<ScreenRegion> regions, String labelText){
		final Map<ScreenRegion, ScreenRegion> lookup = Maps.newHashMap();
		List<ScreenRegion> nearbyRegions = Lists.transform(regions, new Function<ScreenRegion, ScreenRegion>(){
			@Override
			public ScreenRegion apply(ScreenRegion region) {
				ScreenRegion leftRegion = region.getLeft(200);
				leftRegion.grow(0, 5, 0, 5);		
				lookup.put(leftRegion, region);
				return leftRegion;
			}			
		});
		ScreenRegion nearbyRegion = findScreenRegionWithLabel(nearbyRegions, labelText);
		return lookup.get(nearbyRegion);
	}
	
	static protected ScreenRegion findScreenRegionWithLabelRight(List<ScreenRegion> regions, String labelText){
		final Map<ScreenRegion, ScreenRegion> lookup = Maps.newHashMap();
		List<ScreenRegion> nearbyRegions = Lists.transform(regions, new Function<ScreenRegion, ScreenRegion>(){
			@Override
			public ScreenRegion apply(ScreenRegion region) {
				ScreenRegion rightRegion = region.getRight(200);
				rightRegion.grow(0, 5, 0, 5);
				lookup.put(rightRegion, region);
				return rightRegion;
			}			
		});
		ScreenRegion nearbyRegion = findScreenRegionWithLabel(nearbyRegions, labelText);
		return lookup.get(nearbyRegion);
	}
	
	static protected ScreenRegion findScreenRegionWithLabelBelow(List<ScreenRegion> regions, String labelText){
		final Map<ScreenRegion, ScreenRegion> lookup = Maps.newHashMap();
		List<ScreenRegion> nearbyRegions = Lists.transform(regions, new Function<ScreenRegion, ScreenRegion>(){
			@Override
			public ScreenRegion apply(ScreenRegion region) {
				ScreenRegion belowRegion = region.getBelow(30);
				belowRegion.grow(10, 50, 0, 50);
				lookup.put(belowRegion, region);
				return belowRegion;
			}			
		});
		ScreenRegion nearbyRegion = findScreenRegionWithLabel(nearbyRegions, labelText);
		return lookup.get(nearbyRegion);
	}
	
//	@Deprecated
//	protected Label findLabelRight(ScreenRegion origin, String labelText){
//		ScreenRegion rightRegion = origin.getRight(500);
//		rightRegion.grow(0, 5, 0, 5);
//
//		Component region = new Component(this, rightRegion);		
//		Label label = region.label(labelText);
//		if (!label.isVisible())
//			return null;
//		
//		// verify the label is not too far from the origin
//		ScreenRegion labelRegion = label.getScreenRegion();
//		int xoffset = labelRegion.x - (origin.x + origin.width);
//		if (xoffset > 10){
//			return null;
//		}
//		
//		return label;
//	}
	
//	@Deprecated
//	protected Label findLabelInside(ScreenRegion origin, String labelText){
//		Component region = new Component(this, origin);		
//		Label label = region.label(labelText);
//		if (!label.isVisible())
//			return null;		
//		return label;
//	}	
//	
	static ScreenRegion getBoundingBox(List<ScreenRegion> rs){
		ScreenRegion r0 = rs.get(0);
		Rectangle rec = new Rectangle(0,0,r0.width,r0.height); 
		for (ScreenRegion r : rs){
			rec.add(new Rectangle(r.x-r0.x,r.y-r0.y,r.width,r.height));
		}
		ScreenRegion sub = new ScreenRegion(r0, 
				rec.x, rec.y, rec.width, rec.height);
		return sub;
	}	
	
//	@Deprecated
//	protected Label findLabelInside(List<ScreenRegion> regions, String labelText){
//		ScreenRegion bb = getBoundingBox(regions);
//		ImageMask mask = ImageMask.create(bb.width, bb.height);
//		for (ScreenRegion r : regions){
//			mask.add(r.x - bb.x, r.y - bb.y, r.width, r.height);
//		}		
//		return findLabelInside(bb, labelText);
//	}
//	
	static protected ScreenRegion findScreenRegionWithLabel(List<ScreenRegion> regions, String labelText){
		ScreenRegion bb = getBoundingBox(regions);
		ImageMask mask = ImageMask.create(bb.width, bb.height);
		for (ScreenRegion r : regions){
//			r.box(3000);
			mask.add(r.x - bb.x, r.y - bb.y, r.width, r.height);
		}		
		
		TextTarget labelTarget = new TextTarget(labelText);
		final ScreenRegion labelRegion = bb.find(labelTarget);
		//labelRegion.box(3000);
		if (labelRegion == null)
			return null;
		else
			try {
			return Iterables.find(regions, new Predicate<ScreenRegion>(){
				@Override
				public boolean apply(ScreenRegion r) {
					return r.contains(labelRegion);
				}
			});
			} catch (NoSuchElementException e){
				return null;
			}
	}	
//
//	@Deprecated	
//	protected Label findLabelLeft(ScreenRegion origin, String labelText){
//
//		ScreenRegion leftRegion = origin.getLeft(500);
//		leftRegion.grow(0, 5, 0, 5);
//		
//		Component region = new Component(this, leftRegion);
//		Label label = region.label(labelText);
//		if (!label.isVisible())
//			return null;
//
//		
//		// verify the label is not too far from the origin
//		ScreenRegion labelRegion = label.getScreenRegion();
//		int xoffset = origin.x - (labelRegion.x + labelRegion.width);
//		if (xoffset > 30){
//			return null;
//		}
//		
//		return label;
//	}	
	
	/**
	 * Requires the component to be visible or throws an exception
	 * if it is not.
	 * 
	 * @return ScreenRegion the screen region where the component is visible
	 * @throws IllegalStateException if the component is not visible
	 */
	protected ScreenRegion requireVisible(){
		int retries = 0; 
		int maxRetries = 3;
		while (screenRegion == null && retries < maxRetries){
			screenRegion = find();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			retries++;
		}
		if (screenRegion == null){
			throw new IllegalStateException("Component is not visible");
		}
		return screenRegion;
	}
	
}
