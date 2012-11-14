package org.sikuli.api.remote.client;

import java.util.Map;

import org.sikuli.api.Screen;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.remote.client.SikuliHttpCommandExecutor.CommandInfo;
import org.sikuli.api.robot.Mouse;

import com.google.common.collect.ImmutableMap;

import static org.sikuli.api.remote.client.SikuliHttpCommandExecutor.*;

public class UrlMapper {
	static Map<String, CommandInfo>  getMappings(){
		return ImmutableMap.<String, CommandInfo>builder()
				.put(ScreenRegion.FIND, post("/screen/find"))
				.put(Mouse.CLICK, post("/mouse/click"))
				.put(Screen.GET_SIZE, get("/screen/size"))
				.put(Screen.GET_SCREENSHOT, post("/screen/screenshot"))
				.build();	
	}

}
