package org.sikuli.api.remote.client;

import java.util.Map;

import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.remote.Remote;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.robot.desktop.DesktopScreen;

import com.google.common.collect.ImmutableMap;

public class RemoteMouse implements Mouse {

	private Remote remote;

	public RemoteMouse(Remote remote){
		this.remote = remote;
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
		(new RightClick()).call(remote, ImmutableMap.of("x", screenLoc.getX(),"y", screenLoc.getY()));				
	}

	@Override
	public void doubleClick(ScreenLocation screenLoc) {
		(new DoubleClick()).call(remote, ImmutableMap.of("x", screenLoc.getX(),"y", screenLoc.getY()));		
	}

	@Override
	public void click(ScreenLocation screenLoc) {
		(new Click()).call(remote, ImmutableMap.of("x", screenLoc.getX(),"y", screenLoc.getY()));
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
	
	static abstract public class MouseClick extends AbstractRemoteMethod<Void>  {
		@Override
		protected Void execute(Map<String, ?> allParameters){
			int x = ((Long) allParameters.get("x")).intValue();
			int y = ((Long) allParameters.get("y")).intValue();

			Mouse mouse = new DesktopMouse();
			mouse.rightClick(new DefaultScreenLocation(new DesktopScreen(0), x, y));
			return null;
		}		
		
		abstract protected void execute(int x, int y);

	}
	
	static public class RightClick extends MouseClick {

		@Override
		public String getName(){
			return RIGHT_CLICK;
		}

		@Override
		protected void execute(int x, int y){
			Mouse mouse = new DesktopMouse();
			mouse.rightClick(new DefaultScreenLocation(new DesktopScreen(0), x, y));
		}		
	}
	
	static public class DoubleClick extends MouseClick {

		@Override
		public String getName(){
			return DOUBLE_CLICK;
		}

		@Override
		protected void execute(int x, int y){
			Mouse mouse = new DesktopMouse();
			mouse.doubleClick(new DefaultScreenLocation(new DesktopScreen(0), x, y));
		}		

	}
	
	static public class Click extends MouseClick {

		@Override
		public String getName(){
			return CLICK;
		}

		@Override
		protected void execute(int x, int y){
			Mouse mouse = new DesktopMouse();
			mouse.click(new DefaultScreenLocation(new DesktopScreen(0), x, y));
		}		

	
	}

}
