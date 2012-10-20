package org.sikuli.api.gui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FindLabelTest extends BaseTest{
	
	@Before
	public void setUp() throws IOException{
		setUpTestScreen("NetworkPreferencesCreateNew.png");
		gui = GUI.createOSXLion(screenRegion);		
	}
	
	@Test
	public void testFindNetworkNameLabel(){		
		Label label = gui.label("Network Name");
		assertThat("label", label, notNullValue());
		assertThat("label's visibility", label.isVisible(), is(true));
		label.showBorder();
	}


}
