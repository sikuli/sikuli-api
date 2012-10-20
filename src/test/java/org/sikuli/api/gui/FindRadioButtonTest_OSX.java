package org.sikuli.api.gui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class FindRadioButtonTest_OSX extends BaseTest {
	
	@Before
	public void setUp() throws IOException{
		setUpTestScreen("DockPreferences.png");
		gui = GUI.createOSXLion(screenRegion);		
	}
	
	@Test
	public void testFindLeftRadioButtonTest(){		
		RadioButton button = gui.radioButton("Left");
		assertThat("radio button", button, notNullValue());	
		assertThat("radio button's visibility", button.isVisible(), is(true));
		assertThat("radio button's state", button.isChecked(), is(false));
		button.showBorder();
	}

	@Test
	public void testFindRightRadioButtonTest(){		
		RadioButton button = gui.radioButton("Right");
		assertThat("radio button", button, notNullValue());	
		assertThat("radio button's visibility", button.isVisible(), is(true));
		assertThat("radio button's state", button.isChecked(), is(false));
		button.showBorder();
	}

	@Test
	public void testFindBottomRadioButtonTest(){		
		RadioButton button = gui.radioButton("Bottom");
		assertThat("radio button", button, notNullValue());	
		assertThat("radio button's visibility", button.isVisible(), is(true));
		assertThat("radio button's state", button.isChecked(), is(true));
		button.showBorder();
	}

}
