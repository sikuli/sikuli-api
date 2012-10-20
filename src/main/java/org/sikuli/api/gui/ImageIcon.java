package org.sikuli.api.gui;

import java.io.File;
import java.net.URL;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;


public class ImageIcon extends Component {
	
	private ImageTarget imageTarget;
	
	public ImageIcon(Component parent, File file) {
		super(parent, "icon");
		this.imageTarget = new ImageTarget(file);
	}
	
		
	public ImageIcon(GUI parent, URL imageurl) {
		super(parent, "icon");
		this.imageTarget = new ImageTarget(imageurl);
	}


	protected ScreenRegion find(){
		return parent.getScreenRegion().find(imageTarget);
	}

}


