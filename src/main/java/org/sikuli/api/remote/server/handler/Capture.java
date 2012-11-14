package org.sikuli.api.remote.server.handler;

import java.util.Date;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.remote.ErrorCodes;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.server.JsonParametersAware;
import org.openqa.selenium.remote.server.rest.RestishHandler;
import org.openqa.selenium.remote.server.rest.ResultType;

public class Capture implements RestishHandler, JsonParametersAware {

	final Response response;

	public Capture() {
		response = new Response();
	}

	public Response getResponse() {
		return response;
	}

	public ResultType handle() throws Exception {
		response.setStatus(ErrorCodes.SUCCESS);

		JSONObject info = new JSONObject()
		.put("screenshot", new JSONObject()
		.put("time", new Date())
		.put("url", "some image's url"));

		response.setValue(info);
		return ResultType.SUCCESS;
	}

	@Override
	public String toString() {
		return "[take screenshot]";
	}

	@Override
	public void setJsonParameters(Map<String, Object> allParameters)
			throws Exception {
		System.out.println("TEST" + (String) allParameters.get("url"));
		// TODO Auto-generated method stub
		
	}


}