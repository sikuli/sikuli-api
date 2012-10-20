package org.sikuli.api.gui;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.sikuli.api.TestImage;

public class ClickNonExistentExceptionTest extends BaseTest {
	
	@Before
	public void setUp() {
		setUpTestScreen("NetworkPreferences2.png");
		gui = GUI.createOSXLion(screenRegion);		
	}
	
	@Test(expected=IllegalStateException.class)
	public void testClickNonExistentImageIcon() throws IOException {		
		ImageIcon icon = gui.imageIcon(TestImage.class.getResource("chicken.png"));
		icon.click();
	}

	@Test(expected=IllegalStateException.class)
	public void testClickNonExistentButton() {		
		Button button = gui.button("No such button");
		button.click();
	}

	@Test(expected=IllegalStateException.class)
	public void testClickNonExistentTextField() {		
		TextField textField = gui.textField("No such text field");
		textField.click();
	}

	@Test(expected=IllegalStateException.class)
	public void testClickNonExistentCheckBox()  {		
		CheckBox checkBox = gui.checkBox("No such checkbox");
		checkBox.click();
	}
	
	@Test(expected=IllegalStateException.class)
	public void testClickNonExistentComboBox() {		
		ComboBox comboBox = gui.comboBox("No such combobox");
		comboBox.click();
	}
	
	@Test(expected=IllegalStateException.class)
	public void textClickNonExistentRadioButton() {		
		RadioButton radioButton = gui.radioButton("No such radio button");
		radioButton.click();
	}

}
