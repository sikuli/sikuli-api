package org.sikuli.api.gui;

import java.util.List;

import org.sikuli.api.ScreenRegion;
import org.sikuli.api.StyledRectangleTarget;


public class Button extends Component {

	public Button(Component parent, String label){
		super(parent, label);
		buttonTarget = new StyledRectangleTarget(getExample("button/enabled.png"));
	}

	StyledRectangleTarget buttonTarget;

	protected ScreenRegion find(){
		ScreenRegion screenRegion = parent.getScreenRegion();
		List<ScreenRegion> rs = screenRegion.findAll(buttonTarget);
		return findScreenRegionWithLabel(rs, labelText);
	}

}


