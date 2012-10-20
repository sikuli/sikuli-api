package org.sikuli.api.gui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FindTextFieldTest_OSX extends BaseTest{
	
	@Before
	public void setUp() throws IOException{
		setUpTestScreen("NetworkPreferencesCreateNew.png");
		gui = GUI.createOSXLion(screenRegion);		
	}
	
	@Test
	public void testFindCancelButton(){		
		Button button = gui.button("Cancel");
		assertThat("button", button, notNullValue());		
		assertThat("button's visibility", button.isVisible(), is(true));
		button.showBorder();
	}

	@Test
	public void testFindNetworkNameTextField(){		
		TextField textField = gui.textField("Network Name");
		assertThat("textfield", textField, notNullValue());
		assertThat("textfield's visibility", textField.isVisible(), is(true));
		textField.showBorder();
	}
	
	@Test
	public void testNetworkNameTextFieldHasText(){		
		TextField textField = gui.textField("Network Name");		
		assertThat("textfield's text has <NETGEAR>", textField.hasText("NETGEAR"), is(false));
		assertThat("textfield's text has <air>", textField.hasText("air"), is(true));
		textField.showBorder();
	}


	@Test
	public void testFindPasswordTextField(){		
		TextField textField = gui.textField("       Password");
		assertThat("textfield", textField, notNullValue());		
		assertThat("textfield's visibility", textField.isVisible(), is(true));
		textField.showBorder();
	}

	@Test
	public void testFindConfirmPasswordTextField(){		
		TextField textField = gui.textField("Confirm Password");
		assertThat("textfield", textField, notNullValue());		
		assertThat("textfield's visibility", textField.isVisible(), is(true));
		textField.showBorder();
	}

//	@Test
//	public void testFindNonExistentTextField(){		
//		TextField textField = gui.textField("No such text field");
//		assertThat("textfield", textField, notNullValue());		
//		assertThat("textfield's visibility", textField.isVisible(), is(false));
//		System.out.println("done");
//	}

	
}
