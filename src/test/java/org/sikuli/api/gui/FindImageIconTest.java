package org.sikuli.api.gui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.sikuli.api.TestImage;

public class FindImageIconTest extends BaseTest {

	@Before
	public void setUp() throws IOException{
		setUpTestScreen("NetworkPreferences2.png");
		gui = GUI.createOSXLion(screenRegion);		
	}
	
	@Test
	public void testFindLockImageIcon() throws IOException{
		ImageIcon icon = gui.imageIcon(TestImage.get("lock.png"));
		assertThat("icon", icon, notNullValue());	
		assertThat("image icon's visibility", icon.isVisible(), is(true));
		icon.showBorder();
	}

	@Test
	public void testFindNonExistentImageIcon() throws IOException{		
		ImageIcon icon = gui.imageIcon(TestImage.get("chicken.png"));
		assertThat("icon", icon, notNullValue());	
		assertThat("image icon's visibility", icon.isVisible(), is(false));
	}

}
