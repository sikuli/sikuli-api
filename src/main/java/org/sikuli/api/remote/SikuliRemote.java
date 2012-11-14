package org.sikuli.api.remote;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.ExecuteMethod;
import org.sikuli.api.Screen;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.remote.client.RemoteMouse;
import org.sikuli.api.remote.client.RemoteScreen;
import org.sikuli.api.remote.client.RemoteScreenRegion;
import org.sikuli.api.remote.client.SikuliHttpCommandExecutor;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;

public class SikuliRemote implements Remote, ExecuteMethod {
	
	
	final private SikuliHttpCommandExecutor httpCommandExecutor; 
	final private Mouse remoteMouse;
	final private Screen remoteScreen;
		
	public SikuliRemote(URL serverUrl){
		httpCommandExecutor= new SikuliHttpCommandExecutor(serverUrl);
		remoteMouse = new RemoteMouse(this);
		remoteScreen = new RemoteScreen(this);
	}
	
	
	@Override
	public Object execute(String commandName, Map<String, ?> parameters) {
		Command command = new Command(null, commandName, parameters);
		try {
			return httpCommandExecutor.execute(command);
		} catch (IOException e) {
			return null;
		}
	}	

	@Override
	public ExecuteMethod getExecutionMethod() {
		return this;
	}

	@Override
	public Mouse getMouse() {
		return remoteMouse;
	}

	@Override
	public Keyboard getKeyboard() {
		throw new UnsupportedOperationException();
	}


	@Override
	public ScreenRegion getScreenRegion() {
		return new RemoteScreenRegion(this);
	}


	@Override
	public Screen getScreen() {
		return remoteScreen;
	}

}
