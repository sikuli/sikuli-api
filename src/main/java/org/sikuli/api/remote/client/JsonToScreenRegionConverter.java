package org.sikuli.api.remote.client;

import java.awt.Rectangle;
import java.util.Collection;
import java.util.Map;

import org.sikuli.api.ScreenRegion;
import org.sikuli.api.remote.Remote;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class JsonToScreenRegionConverter implements Function<Object, Object> {

	final private Remote remote;
	public JsonToScreenRegionConverter(Remote remote) {
		this.remote = remote;
	}

	public Object apply(Object result) {
		if (result instanceof Collection<?>) {
			Collection<?> results = (Collection<?>) result;
			return Lists.newArrayList(Iterables.transform(results, this));
		}

		if (result instanceof Map<?, ?>) {
			Map<?, ?> resultAsMap = (Map<?, ?>) result;

			ScreenRegion element = newScreenRegion();
			resultAsMap = (Map<?, ?>) resultAsMap.get("find");
			int x = ((Long) resultAsMap.get("x")).intValue();
			int y = ((Long) resultAsMap.get("y")).intValue();
			int w = ((Long) resultAsMap.get("width")).intValue();
			int h = ((Long) resultAsMap.get("height")).intValue();
			Rectangle r = new Rectangle(x,y,w,h);			
			element.setBounds(r);
			return element;
		}


		if (result instanceof Number) {
			if (result instanceof Float || result instanceof Double) {
				return ((Number) result).doubleValue();
			}
			return ((Number) result).longValue();
		}

		return result;
	}

	protected ScreenRegion newScreenRegion(){
		RemoteScreenRegion toReturn = new RemoteScreenRegion(remote);
		//toReturn.setParent(driver);
		return toReturn;
	}
}