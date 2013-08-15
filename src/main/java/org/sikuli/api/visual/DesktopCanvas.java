package org.sikuli.api.visual;

import org.sikuli.api.DesktopScreenRegion;

public class DesktopCanvas extends ScreenRegionCanvas {
	public DesktopCanvas() {
		super(new DesktopScreenRegion());
	}
	
	public DesktopCanvas(int id) {
		super(new DesktopScreenRegion(id));
	}


}
