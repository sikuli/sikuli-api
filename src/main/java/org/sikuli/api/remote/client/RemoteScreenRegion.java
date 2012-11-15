package org.sikuli.api.remote.client;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.sikuli.api.AbstractScreenRegion;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.event.StateChangeListener;
import org.sikuli.api.event.TargetEventListener;
import org.sikuli.api.remote.Remote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

public class RemoteScreenRegion extends AbstractScreenRegion implements ScreenRegion {
	
	final static Logger logger = LoggerFactory.getLogger(RemoteScreenRegion.class);
	final private Remote remote;
	
	public RemoteScreenRegion(Remote remoteRef){
		super(remoteRef.getScreen());
		remote = remoteRef;
	}

	@Override
	public ScreenRegion find(Target target) {		
		if (target instanceof ImageTarget){
			URL url = ((ImageTarget) target).getURL();
			return (new Find()).call(remote, ImmutableMap.of("imageUrl", url.toString()));
		}
		return null;
	}

	@Override
	public ScreenRegion wait(Target target, int mills) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImage capture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTargetEventListener(Target target,
			TargetEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTargetEventListener(Target target,
			TargetEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ScreenRegion snapshot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStateChangeEventListener(StateChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<Target, Object> getStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addROI(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Rectangle> getROIs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImage getLastCapturedImage() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void addState(Target target, Object state) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void removeState(Target target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScreenRegion> findAll(Target target) {
		// TODO Auto-generated method stub
		return null;
	}

	
	static public class Find extends AbstractRemoteMethod<ScreenRegion>{
		
		private String imageUrl;

		@Override
		public String getName(){
			return FIND;
		}

		@Override
		protected ScreenRegion execute(){
			ScreenRegion s = new DesktopScreenRegion();			
			Target imageTarget;
			try {
				imageTarget = new ImageTarget(new URL(imageUrl));
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}			
			ScreenRegion foundRegion = s.find(imageTarget);
			return foundRegion;
		}

		@Override
		protected Map<String,?> encodeResult(ScreenRegion screenRegion){
			Rectangle r = screenRegion.getBounds();
			return ImmutableMap.of("x",r.x,"y",r.y,"width",r.width,"height",r.height);
		}

		@Override
		protected ScreenRegion decodeResult(Remote remote, Map<String,?> valueAsMap){
			int x = ((Long) valueAsMap.get("x")).intValue();
			int y = ((Long) valueAsMap.get("y")).intValue();			
			int width = ((Long) valueAsMap.get("width")).intValue();
			int height = ((Long) valueAsMap.get("height")).intValue();
			ScreenRegion ret = new RemoteScreenRegion(remote);
			ret.setBounds(new Rectangle(x,y,width,height));
			return ret;
		}
		
		@Override
		protected void readParameters(Map<String, ?> allParameters){
			imageUrl = (String) allParameters.get("imageUrl");
		}


	}
	
}
