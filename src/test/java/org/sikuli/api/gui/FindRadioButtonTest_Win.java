package org.sikuli.api.gui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class FindRadioButtonTest_Win extends BaseTest {
	
	@Before
	public void setUp() throws IOException{
		setUpTestScreen("WindowsLanguageBar.png");
		gui = new GUI(screenRegion, "/org/sikuli/api/gui/win7");
	}
	
	@Test
	public void testFindFloatingOnDesktopRadioButton(){
		RadioButton radioButton  = gui.radioButton("Floating On Desktop");
		assertThat("radioButton", radioButton, notNullValue());	
		assertThat("radioButton visibility", radioButton.isVisible(), is(true));
		assertThat("radioButton state", radioButton.isChecked(), is(false));
		radioButton.showBorder();
	}
	
	@Test
	public void testDockedInTheTaskBarRadioButton(){
		RadioButton radioButton  = gui.radioButton("Docked in the taskbar");
		assertThat("radioButton", radioButton, notNullValue());	
		assertThat("radioButton visibility", radioButton.isVisible(), is(true));
		assertThat("radioButton state", radioButton.isChecked(), is(true));
		radioButton.showBorder();
	}

	@Test
	public void testHiddenRadioButton(){
		RadioButton radioButton  = gui.radioButton("Hidden");
		assertThat("radioButton", radioButton, notNullValue());	
		assertThat("radioButton visibility", radioButton.isVisible(), is(true));
		assertThat("radioButton state", radioButton.isChecked(), is(false));
		radioButton.showBorder();
	}
	

}
