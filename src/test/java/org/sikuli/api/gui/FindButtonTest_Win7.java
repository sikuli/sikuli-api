package org.sikuli.api.gui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class FindButtonTest_Win7 extends BaseTest {
	
	@Before
	public void setUp() throws IOException{
		setUpTestScreen("SystemPropertiesComputerName.png");
		gui = new GUI(screenRegion, "/org/sikuli/api/gui/win7");
	}
	
	@Test
	public void testFindNetworkIDButton(){
		Button button = gui.button("Network ID");
		assertThat("button", button, notNullValue());		
		assertThat("button's visibility", button.isVisible(), is(true));
		button.showBorder();		
	}
	
	@Test
	public void testFindOKButton(){
		Button button = gui.button("OK");
		assertThat("button", button, notNullValue());		
		assertThat("button's visibility", button.isVisible(), is(true));
		button.showBorder();
	}
	
	@Test
	public void testFindCancelButton(){
		Button button = gui.button("Cancel");
		assertThat("button", button, notNullValue());		
		assertThat("button's visibility", button.isVisible(), is(true));
		button.showBorder();
	}

	
	@Test
	public void testFindComputerDescriptionTextField(){
		TextField textField = gui.textField("Computer description");
		assertThat("textfield", textField, notNullValue());		
		assertThat("textfield's visibility", textField.isVisible(), is(true));
		textField.showBorder();
	}

}
