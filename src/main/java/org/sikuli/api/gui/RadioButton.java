package org.sikuli.api.gui;

import java.util.List;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.MultiStateTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;


public class RadioButton extends CheckBox{

	private MultiStateTarget radioButtonTarget;

	public RadioButton(Component parent, String label){
		super(parent, label);

		Target checked;
		Target unchecked;
		checked = new ImageTarget(getExample("/radiobutton/checked.png"));
		unchecked = new ImageTarget(getExample("/radiobutton/unchecked.png"));
		radioButtonTarget = new MultiStateTarget();
		radioButtonTarget.addState(checked, "checked");
		radioButtonTarget.addState(unchecked, "unchecked");

	}	

	protected ScreenRegion find(){
		List<ScreenRegion> candidateRegions = parent.getScreenRegion().findAll(radioButtonTarget);

		ScreenRegion labeledRegion = findScreenRegionWithLabelRight(candidateRegions, labelText);
		if (labeledRegion == null){
			labeledRegion = findScreenRegionWithLabelBelow(candidateRegions, labelText);				
		}			

		return labeledRegion;
	}

}