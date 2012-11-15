package org.sikuli.api.remote.client;

import java.util.Map;

import org.openqa.selenium.remote.server.rest.RestishHandler;
import org.sikuli.api.remote.Remote;

public interface RemoteMethod<R> extends RestishHandler {
	public String getName();
	public R call(Remote remote, Map<String,?> parameters);
}