package org.sikuli.api.gui;

import org.sikuli.api.ScreenRegion;
import org.sikuli.api.TextTarget;

public class Label extends Component {

	public Label(Component parent, String label){
		super(parent, label);
	}

	protected ScreenRegion find(){		
		TextTarget labelTarget = new TextTarget(labelText);
		return parent.getScreenRegion().find(labelTarget);		
	}

}
