package org.sikuli.api.remote.client;

import java.util.Map;

import org.openqa.selenium.remote.Response;
import org.sikuli.api.SikuliRuntimeException;

public class RemoteSikuliException extends SikuliRuntimeException {

	Response response;
	public RemoteSikuliException(Response response) {		
		super();		
		this.response = response;
	}
	
	public String toString(){
		Map<String,?> value = (Map<String,?>) response.getValue();
		String className = (String) value.get("class");
		//String className = (String) value.get("class");
		return "Remote Exception: " + className + " : " + value.get("message"); 
	}
	

}
