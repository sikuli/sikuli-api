package org.sikuli.api.gui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class FindCheckBoxTest_Win extends BaseTest {
	
	@Before
	public void setUp() throws IOException{
		setUpTestScreen("WindowsLanguageBar.png");
		gui = new GUI(screenRegion, "/org/sikuli/api/gui/win7");
	}
	
	@Test
	public void testFindShowTextLabelsOnTheLanguaegBarCheckBox(){
		CheckBox checkBox = gui.checkBox("Show text labels on the Language bar");
		assertThat("checkBox", checkBox, notNullValue());	
		assertThat("checkBox's visibility", checkBox.isVisible(), is(true));
		assertThat("checkBox's state", checkBox.isChecked(), is(true));
		checkBox.showBorder();
	}
	
	@Test
	public void testFindShowTheLanguageBarAsTransparentCheckBox(){
		CheckBox checkBox = gui.checkBox("Show the Language bar as transparent");
		assertThat("checkBox", checkBox, notNullValue());	
		assertThat("checkBox's visibility", checkBox.isVisible(), is(true));
		assertThat("checkBox's state", checkBox.isChecked(), is(false));
		checkBox.showBorder();
	}

	@Test
	public void testFindShowAdditionalLanguageBarIconsCheckBox(){
		CheckBox checkBox = gui.checkBox("Show additional Language bar icons");
		assertThat("checkBox", checkBox, notNullValue());	
		assertThat("checkBox's visibility", checkBox.isVisible(), is(true));
		assertThat("checkBox's state", checkBox.isChecked(), is(false));
		checkBox.showBorder();
	}
	

}
