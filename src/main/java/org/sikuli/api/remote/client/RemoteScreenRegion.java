package org.sikuli.api.remote.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import org.sikuli.api.AbstractScreenRegion;
import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.DefaultScreenRegion;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.SikuliRuntimeException;
import org.sikuli.api.Target;
import org.sikuli.api.event.StateChangeListener;
import org.sikuli.api.event.TargetEventListener;
import org.sikuli.api.remote.Remote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class RemoteScreenRegion extends AbstractScreenRegion implements ScreenRegion {

	final static Logger logger = LoggerFactory.getLogger(RemoteScreenRegion.class);
	final private Remote remote;

	public RemoteScreenRegion(Remote remoteRef){
		super(remoteRef.getScreen());
		remote = remoteRef;
	}
	
	@Override
	public ScreenRegion getRelativeScreenRegion(int xoffset, int yoffset, int width, int height){
		RemoteScreenRegion newRemoteScreenRegion = new RemoteScreenRegion(remote);
		newRemoteScreenRegion.setBounds(new Rectangle(getX() + xoffset, getY() + yoffset, width, height));
		return newRemoteScreenRegion;
	}


	@Override
	public ScreenRegion find(Target target) {		
		if (target instanceof ImageTarget){
			URL url = ((ImageTarget) target).getURL();
			Map<String,?> params = 	ImmutableMap.of("imageUrl", url.toString(), "bounds", rectangleToMap(getBounds()));	
			ScreenRegion foundRegion = (new Find()).call(remote, params);					
			logger.info("result: {}", Objects.firstNonNull(foundRegion, "not found"));
			return foundRegion;
		}
		return null;
	}

	@Override
	public List<ScreenRegion> findAll(Target target) {
		if (target instanceof ImageTarget){
			URL url = ((ImageTarget) target).getURL();
			Map<String,?> params = 	ImmutableMap.of("imageUrl", url.toString(), "bounds", rectangleToMap(getBounds()));
			List<ScreenRegion> foundRegions = (new FindAll()).call(remote, params);

			int counter = 0;
			for (ScreenRegion foundRegion : foundRegions){				
				logger.info("{} {}", counter++, foundRegion);
			}			
			return foundRegions;
		}
		return Lists.newArrayList();
	}


	@Override
	public ScreenRegion wait(Target target, int mills) {
		RepeatFind ru = new RepeatFind(target, mills);
		ScreenRegion result = ru.run();
		return result;
	}

	class RepeatFind{

		private volatile boolean timeout = false;	

		private Target target;
		private int duration;

		private ScreenRegion r = null;

		RepeatFind(Target target, int duration){		
			this.target = target;
			this.duration = duration;
		}

		public ScreenRegion run(){
			Timer t = new Timer(duration, new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					timeout = true;
				}				
			});
			t.start();

			while (r == null && !timeout){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
				}
				r = find(target);            
			}
			return r;
		}
	}

	@Override
	public BufferedImage capture() {
		Rectangle r = getBounds();
		return getScreen().getScreenshot(r.x,r.y,r.width,r.height);
	}

	@Override
	public void addTargetEventListener(Target target,
			TargetEventListener listener) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeTargetEventListener(Target target,
			TargetEventListener listener) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ScreenRegion snapshot() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addStateChangeEventListener(StateChangeListener listener) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<Target, Object> getStates() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addROI(int x, int y, int width, int height) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Rectangle> getROIs() {
		throw new UnsupportedOperationException();
	}

	@Override
	public BufferedImage getLastCapturedImage() {
		throw new UnsupportedOperationException();
	}


	@Override
	public void addState(Target target, Object state) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void removeState(Target target) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getState() {
		throw new UnsupportedOperationException();
	}

	static public Map<String,?> convertToMap(ScreenRegion screenRegion){
		if (screenRegion != null){
			Rectangle r = screenRegion.getBounds();					
			return ImmutableMap.of("x",r.x,"y",r.y,"width",r.width,"height",r.height,"score",screenRegion.getScore());
		}else{
			return null;
		}
	}
	
	static public Rectangle mapToRectangle(Map<String,?> map){				
		int x = ((Long) map.get("x")).intValue();
		int y = ((Long) map.get("y")).intValue();			
		int width = ((Long) map.get("width")).intValue();
		int height = ((Long) map.get("height")).intValue();
		return new Rectangle(x,y,width,height);
	}
	
	static public Map<String,?> rectangleToMap(Rectangle r){
		return ImmutableMap.of("x",r.x,"y",r.y,"width",r.width,"height",r.height);
	}
	

	static public ScreenRegion createFromMap(Remote remote, Map<String,?> map){
		if (map != null){
			int x = ((Long) map.get("x")).intValue();
			int y = ((Long) map.get("y")).intValue();			
			int width = ((Long) map.get("width")).intValue();
			int height = ((Long) map.get("height")).intValue();
			double score = ((Double) map.get("score")).doubleValue();
			ScreenRegion ret = new RemoteScreenRegion(remote);
			ret.setBounds(new Rectangle(x,y,width,height));		
			ret.setScore(score);
			return ret;
		}else{
			return null;
		}
	}


	static public class FindAll extends AbstractRemoteMethod<List<ScreenRegion>>{

		@Override
		public String getName(){
			return FIND_ALL;
		}

		@Override
		protected List<ScreenRegion> execute(Map<String,?> parameterMap){
			String imageUrl = (String) parameterMap.get("imageUrl");
			Rectangle r = mapToRectangle((Map<String,?>) parameterMap.get("bounds"));

			ScreenRegion s = new DesktopScreenRegion(r.x,r.y,r.width,r.height);			
			Target imageTarget;
			try {
				imageTarget = new ImageTarget(new URL(imageUrl));
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}			
			List<ScreenRegion> foundRegions = s.findAll(imageTarget);

			int counter = 0;
			for (ScreenRegion foundRegion : foundRegions){				
				logger.info("{} {}", counter++, foundRegion);
			}			
			return foundRegions;
		}

		@Override
		protected Map<String,?> encodeResult(List<ScreenRegion> screenRegions){			
			Collection<Map<String,?>> maps = Collections2.transform(screenRegions, new Function<ScreenRegion, Map<String,?>>(){
				@Override
				public Map<String,?> apply(ScreenRegion screenRegion) {
					return convertToMap(screenRegion);					
				}
			});			
			return ImmutableMap.of("list", maps);
		}

		@Override
		protected List<ScreenRegion> decodeResult(final Remote remote, Map<String,?> valueAsMap){
			Object result = valueAsMap.get("list");
			@SuppressWarnings("unchecked")
			List<Map<String,?>> listOfMap = (List<Map<String,?>>) result;
			List<ScreenRegion> listOfScreenRegions = Lists.transform(listOfMap, new Function<Map<String,?>,ScreenRegion>(){
				@Override
				public ScreenRegion apply(Map<String,?> valueAsMap) {					
					return createFromMap(remote, valueAsMap);
				}
			});	
			return listOfScreenRegions;
		}

	}

	static public class Find extends AbstractRemoteMethod<ScreenRegion>{

		@Override
		public String getName(){
			return FIND;
		}

		@Override
		protected ScreenRegion execute(Map<String,?> parameterMap){
			String imageUrl = (String) parameterMap.get("imageUrl");		
			Rectangle r = mapToRectangle((Map<String,?>) parameterMap.get("bounds"));

			ScreenRegion s = new DesktopScreenRegion(r.x,r.y,r.width,r.height);			
			Target imageTarget;
			try {
				imageTarget = new ImageTarget(new URL(imageUrl));
			} catch (MalformedURLException e) {
				throw new SikuliRuntimeException(e.getMessage());

			}			
			ScreenRegion foundRegion = s.find(imageTarget);			
			return foundRegion;
		}

		@Override
		protected Map<String,?> encodeResult(ScreenRegion screenRegion){
			return convertToMap(screenRegion);
		}

		@Override
		protected ScreenRegion decodeResult(Remote remote, Map<String,?> valueAsMap){
			return createFromMap(remote, valueAsMap); 
		}

	}

}
