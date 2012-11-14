package org.sikuli.api.remote.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.ExecuteMethod;
import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.remote.Remote;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;

import com.google.common.collect.ImmutableMap;

public class RemoteMouse implements Mouse {
	
	private ExecuteMethod executor;

	public RemoteMouse(ExecuteMethod executor){
		this.executor = executor;
	}

	@Override
	public void drag(ScreenLocation screenLoc) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void drop(ScreenLocation screenLoc) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void rightClick(ScreenLocation screenLoc) {
		executor.execute(RIGHT_CLICK, ImmutableMap.of("x", screenLoc.getX(),"y", screenLoc.getY()));				
	}

	@Override
	public void doubleClick(ScreenLocation screenLoc) {
		executor.execute(DOUBLE_CLICK, ImmutableMap.of("x", screenLoc.getX(),"y", screenLoc.getY()));		
	}

	@Override
	public void click(ScreenLocation screenLoc) {
		executor.execute(CLICK, ImmutableMap.of("x", screenLoc.getX(),"y", screenLoc.getY()));
	}

	@Override
	public void wheel(int direction, int steps) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void mouseDown(int buttons) {
		throw new UnsupportedOperationException();	
	}

	@Override
	public void mouseUp() {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void mouseUp(int buttons) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public ScreenLocation getLocation() {
		throw new UnsupportedOperationException();
	}
	
	public static void main(String[] args) throws MalformedURLException {
		
//		final SikuliHttpCommandExecutor sikuliHttpCommandExecutor = 
//				new SikuliHttpCommandExecutor(new URL("http://localhost:9000/sikuli/"));
//		final ExecuteMethod executor = new ExecuteMethod(){
//			@Override
//			public Object execute(String commandName, Map<String, ?> parameters) {
//				Command command = new Command(null, commandName, parameters);
//				try {
//					return sikuliHttpCommandExecutor.execute(command);
//				} catch (IOException e) {
//					return null;
//				}
//			}			
//		};
//
//		Mouse mouse = new RemoteMouse(executor);
//		mouse.click(new DefaultScreenLocation(null,100,100));
//		
//		Remote remote = new Remote(){
//
//			@Override
//			public ExecuteMethod getExecutionMethod() {
//				return executor;
//			}
//
//			@Override
//			public Mouse getMouse() {
//				return null;
//			}
//
//			@Override
//			public Keyboard getKeyboard() {
//				return null;
//			}
//			
//		};
//		ScreenRegion s = new RemoteScreenRegion(remote);
//		ScreenRegion r = s.find(new ImageTarget(new URL("http://code.google.com/images/code_logo.gif")));		
//		mouse.click(r.getCenter());
	}

}
