package org.sikuli.api.remote.client;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.Response;
import org.sikuli.api.AbstractScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Screen;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.event.StateChangeListener;
import org.sikuli.api.event.TargetEventListener;
import org.sikuli.api.remote.Remote;

import com.google.common.collect.ImmutableMap;

public class RemoteScreenRegion extends AbstractScreenRegion implements ScreenRegion {
	
	final private Remote remote;
	public RemoteScreenRegion(Remote remoteRef){
		super(remoteRef.getScreen());
		remote = remoteRef;
	}


	@Override
	public ScreenRegion find(Target target) {
		if (target instanceof ImageTarget){
			URL url = ((ImageTarget) target).getURL();
			Response res = (Response) remote.getExecutionMethod().execute(ScreenRegion.FIND, ImmutableMap.of("imageUrl", url.toString()));
			JsonToScreenRegionConverter converter = new JsonToScreenRegionConverter(remote);
			System.out.println("res:" + res.getValue());			
			ScreenRegion foundScreenRegion = (ScreenRegion) converter.apply(res.getValue());			
			System.out.println("con:" + foundScreenRegion);
			return foundScreenRegion;
			
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

	
}
