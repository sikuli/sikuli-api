package org.sikuli.api.gui;

import java.util.List;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.StyledRectangleTarget;
import org.sikuli.api.TextTarget;

import com.google.common.collect.Lists;

public class ComboBox extends Component {

	// screen region this checkbox is most recently seen
	private StyledRectangleTarget comboBoxRectangleTarget;
	private ImageTarget arrows;

	public ComboBox(Component parent, String label){
		super(parent, label);
		comboBoxRectangleTarget = new StyledRectangleTarget(getExample("combobox/enabled.png"));
		arrows = new ImageTarget(getExample("combobox/arrows.png"));
	}

	public ComboBox select(String string) {
		throw new UnsupportedOperationException();

		//		API.click(screenRegion.getCenter().getAbove(10));
		//		API.pause(1000);
		//		ScreenLocation screenLoc = screenRegion.getCenter();
		//		IRobot robot = screenLoc.getScreenDevice().getRobot();
		//		robot.smoothMove(screenLoc);
		//		robot.mousePress(InputEvent.BUTTON1_MASK);
		//		//robot.waitForIdle();
//		ScreenRegion screenRegion = find();
//		//
//		API.click(screenRegion.getCenter());
//
//		API.pause(1000);
//		//API.click(screenRegion.getCenter());
//		//		
//		ModelTextTarget selectionTarget = new ModelTextTarget(string, parent.getTextModel());
//		selectionTarget.setMinScore(0.60);
//
//		ScreenRegion belowRegion = screenRegion.getBelow(500);		
//		ScreenRegion selectionRegion = belowRegion.wait(selectionTarget,3000);
//		//		
//
//		//		ScreenLocation c = selectionRegion.getCenter();
//		//		robot.mouseMove(c.x, c.y);
//		//		
//		//		API.pause(1000);
//		//		//robot.mousePress(InputEvent.BUTTON1_MASK);
//		//		robot.mousePress(InputEvent.BUTTON1_MASK);
//		//		robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		//		//robot.mouseRelease(InputEvent.BUTTON1_MASK);
//		//API.drop(selectionRegion.getCenter());
//		API.click(selectionRegion.getCenter());

//		return this;
	}
	
	/**
	 * Checks whether this ComboBox contains the given text.
	 * 
	 * Note that when the text does not exist, this method may take
	 * substantially longer to return because it will try many things trying to
	 * find that text. Only after everything is tried, it will conclude that the
	 * text indeed does not exist and return false.
	 * 
	 * @param text
	 * @return
	 */
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


	protected ScreenRegion find(){

		List<ScreenRegion> rectangles = parent.getScreenRegion().findAll(comboBoxRectangleTarget);
		List<ScreenRegion> comboBoxes = Lists.newArrayList();
		for (ScreenRegion rectangle : rectangles){
			if (rectangle.find(arrows) != null){
				comboBoxes.add(rectangle);
			}
		}

		return findScreenRegionWithLabelLeft(comboBoxes, labelText);
	}

}