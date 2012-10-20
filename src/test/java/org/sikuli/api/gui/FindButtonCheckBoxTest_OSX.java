package org.sikuli.api.gui;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FindButtonCheckBoxTest_OSX extends BaseTest {
	
	@Before
	public void setUp() throws IOException{
		setUpTestScreen("NetworkPreferences2.png");
		gui = GUI.createOSXLion(screenRegion);		
	}
	
	@Test
	public void testFindApplyButton(){		
		Button button = gui.button("Apply");
		assertThat("button", button, notNullValue());		
		assertThat("button's visibility", button.isVisible(), is(true));
		button.showBorder();
	}

	@Test
	public void testFindAssistMeButton(){		
		Button button = gui.button("Assist me...");
		assertThat("button", button, notNullValue());
		assertThat("button's visibility", button.isVisible(), is(true));
		button.showBorder();
	}

//	@Test
//	public void testFindNonExistentButton(){		
//		Button button = gui.button("No such button");
//		assertThat("button", button, notNullValue());
//		assertThat("button's visibility", button.isVisible(), is(false));
//	}

	@Test
	public void testFindAskToJoinNewNetworksCheckBox(){		
		CheckBox checkbox = gui.checkBox("Ask to join new networks");
		assertThat("checkbox", checkbox, notNullValue());	
		assertThat("checkbox's visibility", checkbox.isVisible(), is(true));
		assertThat("checkbox's state", checkbox.isChecked(), is(false));			
		checkbox.showBorder();
	}
	
	@Test
	public void testFindShowWiFiStatusInMenuBarCheckBox(){		
		CheckBox checkbox = gui.checkBox("Show Wi-Fi status in menu bar");
		assertThat("checkbox", checkbox, notNullValue());	
		assertThat("checkbox's visibility", checkbox.isVisible(), is(true));
		assertThat("checkbox's state", checkbox.isChecked(), is(true));			
		checkbox.showBorder();
	}

//	@Test
//	public void testFindNonExistentCheckBox(){		
//		CheckBox checkbox = gui.checkBox("There is no such checkbox");
//		assertThat("checkbox", checkbox, notNullValue());	
//		assertThat("checkbox's visibility", checkbox.isVisible(), is(false));
//	}


}
