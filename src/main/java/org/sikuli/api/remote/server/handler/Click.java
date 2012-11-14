package org.sikuli.api.remote.server.handler;

import java.util.Map;

import org.openqa.selenium.remote.server.JsonParametersAware;
import org.openqa.selenium.remote.server.rest.RestishHandler;
import org.openqa.selenium.remote.server.rest.ResultType;
import org.sikuli.api.DefaultScreenLocation;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.robot.desktop.DesktopScreen;

public class Click implements RestishHandler, JsonParametersAware {

	private int x;
	private int y;

	public ResultType handle() throws Exception {
		Mouse mouse = new DesktopMouse();
		mouse.click(new DefaultScreenLocation(new DesktopScreen(0), x, y));
		return ResultType.SUCCESS;
	}

	@Override
	public String toString() {
		return "[click " + x + "," + y + "]";
	}

	@Override
	public void setJsonParameters(Map<String, Object> allParameters)
			throws Exception {
		x = ((Long) allParameters.get("x")).intValue();
		y = ((Long) allParameters.get("y")).intValue();
	}
}
