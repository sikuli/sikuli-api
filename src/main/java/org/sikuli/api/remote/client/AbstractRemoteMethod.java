package org.sikuli.api.remote.client;

import java.util.Map;

import org.openqa.selenium.remote.ErrorCodes;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.server.JsonParametersAware;
import org.openqa.selenium.remote.server.rest.ResultType;
import org.sikuli.api.remote.Remote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

abstract public class AbstractRemoteMethod<T> implements RemoteMethod<T>,
JsonParametersAware {

	final Response response;

	static Logger logger = LoggerFactory.getLogger(RemoteMethod.class); 

	abstract public String getName();

	abstract protected T execute(Map<String,?> parameterMap);
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

	@SuppressWarnings("unchecked")
	@Override
	public T call(Remote remote, Map<String,?> parameterMap){	
		logger.info("calling [{}] params={}", getName(), parameterMap);
		Response response = (Response) remote.getExecutionMethod().execute(getName(), 
				Objects.firstNonNull(parameterMap, ImmutableMap.of("","")));	
		
		if (response.getStatus() == ErrorCodes.SUCCESS){
			Object value = response.getValue();
			if (value instanceof Map<?,?>){
				return decodeResult(remote, (Map<String,?>) response.getValue());	
			}else{
				return null;
			}
		}else{
			logger.info("response: {}", response.getValue());
			throw new RemoteSikuliException(response);
		}
	}		

	@Override
	public ResultType handle() throws Exception {
		if (parameterMap == null){
			// we expect setJsonParameters() has been invoked already
			throw new IllegalArgumentException("parameters have not been set properly");	
		}


		T result = execute(parameterMap);
		Map<String,?> value = encodeResult(result);

		response.setValue(value);			
		response.setSessionId("dummyId");
		response.setStatus(ErrorCodes.SUCCESS);

		return ResultType.SUCCESS;
	}

	@Override
	public String toString(){
		return Objects.toStringHelper(this).add("params", parameterMap).toString();
	}


	Map<String,?> parameterMap;
	@Override
	public void setJsonParameters(Map<String, Object> parameterMap)
			throws Exception {
		this.parameterMap = parameterMap;
	}

}