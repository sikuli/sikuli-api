package org.sikuli.api.gui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class FindComboBoxTest_OSX extends BaseTest {
	
	@Before
	public void setUp() throws IOException{
		setUpTestScreen("NetworkPreferences2.png");
		gui = GUI.createOSXLion(screenRegion);		
	}
	
	@Test
	public void testFindLocationComboBox(){		
		ComboBox combo = gui.comboBox("Location");
		assertThat("combo", combo, notNullValue());
		assertThat("combobox's visibility", combo.isVisible(), is(true));
		combo.showBorder();
	}	
	
	@Test
	public void testFindNetworkNameComboBox(){		
		ComboBox combo = gui.comboBox("Network Name");
		assertThat("combo", combo, notNullValue());
		assertThat("combobox's visibility", combo.isVisible(), is(true));
		combo.showBorder();
	}	
	
	@Test
	public void testLocationComboBoxHasText(){		
		ComboBox combo = gui.comboBox("Location");
		assertThat("combobox's text is <Manual>", combo.hasText("Manual"), is(false));
		assertThat("combobox's text is <Automatic>", combo.hasText("Automatic"), is(true));
	}
	
	@Test
	public void testLocationComboBoxDoesNotHaveText(){		
		ComboBox combo = gui.comboBox("Location");
		assertThat("combobox's text is <Manual>", combo.hasText("Manual"), is(false));
	}

	
	@Test
	public void testNetworkNameComboBoxHasText(){		
		ComboBox combo = gui.comboBox("Network Name");
		assertThat("combobox's text is <konju>", combo.hasText("konju"), is(true));
		//combo.box();
	}
	
	@Test
	public void testNetworkNameComboBoxDoesNotHaveText(){		
		ComboBox combo = gui.comboBox("Network Name");
		assertThat("combobox's text is <princess>", combo.hasText("princess"), is(false));
	}


}
