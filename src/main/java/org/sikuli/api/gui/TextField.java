package org.sikuli.api.gui;

import java.util.List;

import org.sikuli.api.API;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.StyledRectangleTarget;
import org.sikuli.api.TextTarget;

public class TextField extends Component {

	private ImageTarget arrows;
	private StyledRectangleTarget textFieldRectangleTarget;
	private StyledRectangleTarget selectedTextFieldRectangleTarget;

	public TextField(Component parent, String label){
		super(parent, label);

		textFieldRectangleTarget = new StyledRectangleTarget(getExample("textfield/enabled.png"));
		selectedTextFieldRectangleTarget = new StyledRectangleTarget(getExample("textfield/selected.png"));
		//arrows = new ImageTarget(getClass().getResource("arrows.png"));

	}

	public TextField enterText(String string){
		ScreenRegion screenRegion = requireVisible();
		getMouse().click(screenRegion.getCenter());
		getKeyboard().paste(string);
		return this;
	}

	public TextField deleteText(){
		throw new UnsupportedOperationException();
		//return this;
	}

	public boolean hasText(String text){
		ScreenRegion screenRegion = requireVisible();

		// this forces the text not to match to a substring, reducing
		// the chance of false positives.
		String spacePaddedText = " " + text + " "; 
		ScreenRegion t = screenRegion.find(new TextTarget(spacePaddedText));
		if (t != null){
			boolean isVerticallyAligned = Math.abs(t.getCenter().y - 
					screenRegion.getCenter().y) < 5;
			//t.box(1000);
			return isVerticallyAligned;
		}
		return t != null;
	}

	private ScreenRegion textFieldFindHelper(String string, StyledRectangleTarget example){
		List<ScreenRegion> textFields = parent.getScreenRegion().findAll(example);
		return findScreenRegionWithLabelLeft(textFields, labelText);
	}

	protected ScreenRegion find(){
		ScreenRegion ret = textFieldFindHelper(labelText, textFieldRectangleTarget);
		if (ret == null){
			ret = textFieldFindHelper(labelText, selectedTextFieldRectangleTarget);
		}
		return ret;
	}

}