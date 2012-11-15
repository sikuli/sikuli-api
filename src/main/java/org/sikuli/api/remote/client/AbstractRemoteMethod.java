package org.sikuli.api.remote.client;

import java.util.Map;

import org.openqa.selenium.remote.ErrorCodes;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.server.JsonParametersAware;
import org.openqa.selenium.remote.server.rest.ResultType;
import org.sikuli.api.remote.Remote;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

abstract public class AbstractRemoteMethod<T> implements RemoteMethod<T>,
	 JsonParametersAware {
	
	final Response response;

	abstract public String getName();

	abstract protected T execute();
	protected T decodeResult(Remote remote, Map<String,?> value){
		return null;					
	}
	
	protected Map<String,?> encodeResult(T result){
		return null;
	}

	public AbstractRemoteMethod(){	
		response = new Response();
	}

	public Response getResponse() {
		return response;
	}
	
	@Override
	public T call(Remote remote, Map<String,?> parameterMap){
		Response response = (Response) remote.getExecutionMethod().execute(getName(), 
				Objects.firstNonNull(parameterMap, ImmutableMap.of("","")));
		
		Object value = response.getValue();
		if (value instanceof Map<?,?>){
			return decodeResult(remote, (Map<String,?>) response.getValue());	
		}else{
			return null;
		}
	}		

	@Override
	public ResultType handle() throws Exception {
		T result = execute();
		Map<String,?> value = encodeResult(result);

		response.setValue(value);			
		response.setSessionId("dummyId");
		response.setStatus(ErrorCodes.SUCCESS);
		return ResultType.SUCCESS;
	}

	@Override
	public String toString(){
		return Objects.toStringHelper(this).toString();
	}
	
	@Override
	public void setJsonParameters(Map<String, Object> parameterMap)
			throws Exception {
		readParameters(parameterMap);
	}

	protected void readParameters(Map<String, ?> parameterMap) {		
	}

}