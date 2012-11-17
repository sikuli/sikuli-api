package org.sikuli.api.remote.client;

import java.util.Map;

import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.remote.Remote;
import org.sikuli.api.remote.client.RemoteMouse.Click;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.robot.desktop.DesktopScreen;

import com.google.common.collect.ImmutableMap;

public class RemoteKeyboard implements Keyboard {

	private Remote remote;

	public RemoteKeyboard(Remote remote){
		this.remote = remote;
	}

	@Override
	public void paste(String text) {
		(new Paste()).call(remote, ImmutableMap.of("text", text));		
	}

	@Override
	public void type(String text) {
		(new Type()).call(remote, ImmutableMap.of("text", text));		
	}

	@Override
	public void keyDown(int keycode) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void keyUp(int keycode) {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void keyDown(String keys) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyUp() {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void keyUp(String keys) {
		throw new UnsupportedOperationException();
	}

	
	static public class Type extends AbstractRemoteMethod<Void>  {
		@Override
		protected Void execute(Map<String, ?> allParameters){
			String text = (String) allParameters.get("text");
			(new DesktopKeyboard()).type(text);
			return null;
		}

		@Override
		public String getName() {
			return "/keyboard/type";
		}		
	}
	
	static public class Paste extends AbstractRemoteMethod<Void>  {
		@Override
		protected Void execute(Map<String, ?> allParameters){
			String text = (String) allParameters.get("text");
			(new DesktopKeyboard()).paste(text);
			return null;
		}

		@Override
		public String getName() {
			return "/keyboard/paste";
		}		
	}

}
