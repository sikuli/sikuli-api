package org.sikuli.api.remote.server.handler;

import java.awt.Rectangle;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.remote.ErrorCodes;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.server.JsonParametersAware;
import org.openqa.selenium.remote.server.rest.RestishHandler;
import org.openqa.selenium.remote.server.rest.ResultType;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;

public class Size implements RestishHandler {

	final Response response;
	
	public Size() {
		response = new Response();
	}

	public Response getResponse() {
		return response;
	}

	public ResultType handle() throws Exception {
		response.setStatus(ErrorCodes.SUCCESS);
		ScreenRegion s = new DesktopScreenRegion();
		Rectangle r = s.getBounds();
		JSONObject info = new JSONObject()
			.put("size", new JSONObject()
			.put("width", r.width)
			.put("height", r.height));

		response.setValue(info);
		response.setSessionId("dummyId");
		return ResultType.SUCCESS;
	}

	@Override
	public String toString() {
		return "[size]";
	}


}