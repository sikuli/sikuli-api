package org.sikuli.api.remote.client;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Map;

import org.openqa.selenium.remote.Response;
import org.sikuli.api.Screen;
import org.sikuli.api.remote.Remote;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

public class RemoteScreen implements Screen {
	
	final private Remote remote;
	final private int width;
	final private int height;
	
	public RemoteScreen(Remote remoteRef){
		remote = remoteRef;
		
		Response response = (Response) remote.getExecutionMethod().execute(GET_SIZE, ImmutableMap.of("",""));
		Object value = response.getValue();
		if (value instanceof Map<?, ?>) {
			Map<?, ?> valueAsMap = (Map<?, ?>) value;
			valueAsMap = (Map<?, ?>) valueAsMap.get("size");
			width = ((Long) valueAsMap.get("width")).intValue();
			height = ((Long) valueAsMap.get("height")).intValue();
		}else{
			width = 0;
			height = 0;
		}
	}

	@Override
	public BufferedImage getScreenshot(int x, int y, int width, int height) {
		return null;
	}

	@Override
	public Dimension getSize() {
		return new Dimension(width, height);
	}
	
	@Override
	public String toString(){
		return Objects.toStringHelper(this).add("width",width).add("height",height).toString();
	}

}
