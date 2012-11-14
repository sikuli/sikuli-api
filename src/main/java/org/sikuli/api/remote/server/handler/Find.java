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

public class Find implements RestishHandler, JsonParametersAware {

	final Response response;
	private String imageUrl;
	
	public Find() {
		response = new Response();
	}

	public Response getResponse() {
		return response;
	}

	public ResultType handle() throws Exception {
		response.setStatus(ErrorCodes.SUCCESS);
		ScreenRegion s = new DesktopScreenRegion();
		System.out.println(imageUrl);
		Target imageTarget = new ImageTarget(new URL(imageUrl));
		System.out.println(s);
		ScreenRegion foundRegion = s.find(imageTarget);
		Rectangle r = foundRegion.getBounds();
		
		JSONObject info = new JSONObject()
			.put("find", new JSONObject()
			.put("x", r.x)
			.put("y", r.y)
			.put("width", r.width)
			.put("height", r.height)
			.put("score", foundRegion.getScore()));

		response.setValue(info);
		response.setSessionId("dummyId");
		return ResultType.SUCCESS;
	}

	@Override
	public String toString() {
		return "[find " + imageUrl + "]";
	}

	@Override
	public void setJsonParameters(Map<String, Object> allParameters)
			throws Exception {
		imageUrl = (String) allParameters.get("imageUrl");
	}


}