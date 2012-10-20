package org.sikuli.api.gui;

import java.util.List;

import org.sikuli.api.API;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.MultiStateTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;

public class CheckBox extends Component{
	public CheckBox(Component parent, String label){
		super(parent, label);

		Target checked;
		Target unchecked;
		checked = new ImageTarget(getExample("checkbox/checked.png"));
		unchecked = new ImageTarget(getExample("checkbox/unchecked.png"));
		checkboxTarget = new MultiStateTarget();
		checkboxTarget.addState(checked, "checked");
		checkboxTarget.addState(unchecked, "unchecked");
	}

	public boolean isChecked(){
		ScreenRegion screenRegion = requireVisible();
		return (String) screenRegion.getState() == "checked";
	}

	public void check() {
		ScreenRegion screenRegion = requireVisible();
		if (!isChecked()){
			getMouse().click(screenRegion.getCenter());
		}
	}

	public void uncheck() {
		ScreenRegion screenRegion = requireVisible();
		if (isChecked()){
			getMouse().click(screenRegion.getCenter());
		}
	}


	public CheckBox click(){
		return (CheckBox) super.click();
	}

	private MultiStateTarget checkboxTarget;



	protected ScreenRegion find(){
		List<ScreenRegion> checkBoxRegions = parent.getScreenRegion().findAll(checkboxTarget);
		return findScreenRegionWithLabelRight(checkBoxRegions, labelText);
	}

}