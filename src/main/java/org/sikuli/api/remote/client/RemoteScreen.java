package org.sikuli.api.remote.client;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.internal.Base64Encoder;
import org.openqa.selenium.remote.ErrorCodes;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.server.JsonParametersAware;
import org.openqa.selenium.remote.server.rest.RestishHandler;
import org.openqa.selenium.remote.server.rest.ResultType;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.Screen;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.remote.Remote;
import org.sikuli.api.robot.desktop.DesktopScreen;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

public class RemoteScreen implements Screen {
	
	final private Remote remote;
	final private int width;
	final private int height;
	
	public RemoteScreen(Remote remoteRef){
		remote = remoteRef;
		
		Dimension d = getSize();
		width = d.width;
		height = d.height;
		
//		Response response = (Response) remote.getExecutionMethod().execute(GET_SIZE, ImmutableMap.of("",""));
//		Object value = response.getValue();
//		if (value instanceof Map<?, ?>) {
//			Map<?, ?> valueAsMap = (Map<?, ?>) value;
//			valueAsMap = (Map<?, ?>) valueAsMap.get("size");
//			width = ((Long) valueAsMap.get("width")).intValue();
//			height = ((Long) valueAsMap.get("height")).intValue();
//		}else{
//			width = 0;
//			height = 0;
//		}
	}

	@Override
	public BufferedImage getScreenshot(int x, int y, int width, int height) {		
		GetScreenshot action = new GetScreenshot(remote);
		return action.call(ImmutableMap.of("x",x,"y",y,"width",width,"height",height));
		
		
//		Response response = (Response) remote.getExecutionMethod().execute(GET_SCREENSHOT, 
//				ImmutableMap.of("x",x,"y",y,"width",width,"height",height));
//		Object value = response.getValue();
//		String valueAsString = (String) value;
//		return GetScreenshot.decodeResult(valueAsString);		
	}

	@Override
	public Dimension getSize() {
		GetSize action = new GetSize(remote);
		return action.call(null);
	}
	
	@Override
	public String toString(){
		return Objects.toStringHelper(this).add("width",width).add("height",height).toString();
	}

	public interface RemoteMethod<R> {
		R call(Map<String,?> parameters);
	};
	
	abstract static public class AbstractAction<T> implements RestishHandler, JsonParametersAware {
		
		final private Remote remote;
		final Response response;
		
		abstract public String getName();
		
		abstract protected T run();
		abstract protected T decodeResult(Object value);
		abstract protected Object encodeResult(T result);
		
		public AbstractAction(){	
			response = new Response();
			remote = null;
		}
		
		public AbstractAction(Remote remoteRef) {
			remote = remoteRef;
			response = null;
		}

		
		public T call(Map<String,?> parameterMap){
			if (parameterMap == null){
				parameterMap = ImmutableMap.of("","");
			}
			Response response = (Response) remote.getExecutionMethod().execute(getName(), parameterMap);
			return decodeResult(response.getValue());
		}		
		
		@Override
		public ResultType handle() throws Exception {
			T result = run();
			Object value = encodeResult(result);
						
			response.setValue(value);			
			response.setSessionId("dummyId");
			response.setStatus(ErrorCodes.SUCCESS);
			return ResultType.SUCCESS;
		}
		
		@Override
		public String toString(){
			return Objects.toStringHelper(this).toString();
		}
	}
	
	static public class GetSize extends AbstractAction<Dimension>
	implements RestishHandler, JsonParametersAware,
		RemoteMethod<Dimension>	 {
		
		public String getName(){
			return GET_SIZE;
		}
		
		public GetSize() {
			super();
		}

		public GetSize(Remote remote) {
			super(remote);
		}

		public Response getResponse() {
			return response;
		}
		
		@Override
		protected Dimension run(){
			Screen s = new DesktopScreen(0);
			return s.getSize();
		}
		
		@Override
		protected Object encodeResult(Dimension size){			
			JSONObject info;
			try {
				info = new JSONObject()
				.put("size", new JSONObject()
				.put("width", size.width)
				.put("height",size.height));
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
			return info;
		}
				
		@Override
		protected Dimension decodeResult(Object value){
			int width;
			int height;
			if (value instanceof Map<?, ?>) {
				Map<?, ?> valueAsMap = (Map<?, ?>) value;
				valueAsMap = (Map<?, ?>) valueAsMap.get("size");
				width = ((Long) valueAsMap.get("width")).intValue();
				height = ((Long) valueAsMap.get("height")).intValue();
			}else{
				width = 0;
				height = 0;
			}
			return new Dimension(width,height);
		}

		@Override
		public void setJsonParameters(Map<String, Object> allParameters)
				throws Exception {
		}
	}
	
	static public class GetScreenshot implements RestishHandler, JsonParametersAware,
	RemoteMethod<BufferedImage> {

	final Response response;
	private int x;
	private int y;
	private int width;
	private int height;
	final private Remote remote;

	public String getName(){
		return GET_SCREENSHOT;
	}
	
	public GetScreenshot() {
		response = new Response();
		remote = null;
	}

	public GetScreenshot(Remote remoteRef) {
		remote = remoteRef;
		response = null;
	}

	public Response getResponse() {
		return response;
	}

	public ResultType handle() throws Exception {
		response.setStatus(ErrorCodes.SUCCESS);

		ScreenRegion s = new DesktopScreenRegion();
		BufferedImage image = s.getScreen().getScreenshot(x, y, width, height);		
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, "png", os);
		//InputStream is = new ByteArrayInputStream(os.toByteArray());
				
		String base64 = new Base64Encoder().encode(os.toByteArray());
		response.setValue(base64);		
		response.setSessionId("dummyId");
		return ResultType.SUCCESS;
	}
			
	public BufferedImage call(Map<String,?> parameterMap){
		Response response = (Response) remote.getExecutionMethod().execute(getName(), parameterMap);
		return decodeResult((String) response.getValue());
	}		
	
	protected BufferedImage decodeResult(String valueAsString){
		byte[] bytes = new Base64Encoder().decode(valueAsString);
		InputStream is = new ByteArrayInputStream(bytes);			
		try {
			return ImageIO.read(is);
		} catch (IOException e) {				
		}	
		return null;
	}

	@Override
	public String toString() {
		return "[capture]";
	}

	@Override
	public void setJsonParameters(Map<String, Object> allParameters)
			throws Exception {
		x = ((Long) allParameters.get("x")).intValue();
		y = ((Long) allParameters.get("y")).intValue();
		width = ((Long) allParameters.get("width")).intValue();
		height = ((Long) allParameters.get("height")).intValue();
	}

}
}

