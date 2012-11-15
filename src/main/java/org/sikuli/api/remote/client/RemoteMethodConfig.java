package org.sikuli.api.remote.client;

import java.util.List;

import com.google.common.collect.Lists;


// Single file to define available remote methods and their url mappings
public class RemoteMethodConfig {
	
	@SuppressWarnings("unchecked")
	static public List<? extends RemoteMethod<?>> getRemoteMethods(){				
		return Lists.newArrayList(
				new RemoteScreenRegion.Find(),
				new RemoteScreen.GetSize(), 
				new RemoteScreen.GetScreenshot(),
				new RemoteMouse.Click(),
				new RemoteMouse.DoubleClick(),
				new RemoteMouse.RightClick());
	}
}
