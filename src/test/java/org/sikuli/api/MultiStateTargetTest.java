package org.sikuli.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sikuli.api.Target.Ordering;

public class MultiStateTargetTest extends BaseTest {
	
	private MultiStateTarget applyButtonTarget;
	private MultiStateTarget checkboxTarget;

	@Before
	public void setUp() throws IOException{
		Target enabled = new ImageTarget(getClass().getResource("ApplyButtonEnabled.png"));
		Target disabled = new ImageTarget(getClass().getResource("ApplyButtonDisabled.png"));
		//		

		applyButtonTarget = new MultiStateTarget();//checkedCheckbox, unchckedCheckbox);
		applyButtonTarget.addState(enabled, "enabled");
		applyButtonTarget.addState(disabled, "disabled");
		
		
		Target checked = new ImageTarget(getClass().getResource("checkbox_checked.png"));
		Target unchecked = new ImageTarget(getClass().getResource("checkbox_unchecked.png"));

		checkboxTarget = new MultiStateTarget();
		checkboxTarget.addState(checked, "checked");
		checkboxTarget.addState(unchecked, "unchecked");
		
	}


	@Test
	public void testApplyButtonFound() throws IOException{
		ScreenRegion s = createTestScreenRegionFrom("NetworkPreferences1.png");
		DesktopScreenRegion r = s.find(applyButtonTarget);
		assertThat("number of buttons found", r, notNullValue());		
	}
	
	@Test
	public void testApplyButtonNotFound() throws IOException{

		ScreenRegion s = createTestScreenRegionFrom("macdesktop.png");		
		DesktopScreenRegion r = s.find(applyButtonTarget);
		assertThat("number of buttons found", r, nullValue());		
	}
	
	@Test
	public void testApplyButtonIsEnabled() throws IOException{
		ScreenRegion s = createTestScreenRegionFrom("NetworkPreferences3.png");		
		ScreenRegion r = s.find(applyButtonTarget);
		String state = (String) r.getState();
		assertThat("state of apply button", state, is("enabled"));
	}
	
	@Test
	public void testApplyButtonIsDisabled() throws IOException{
		ScreenRegion s = createTestScreenRegionFrom("NetworkPreferences2.png");		
		ScreenRegion r = s.find(applyButtonTarget);
		String state = (String) r.getState();
		assertThat("state of apply button", state, is("disabled"));
	}



	@Test
	public void testTwoCheckboxes() throws IOException{

		// this image contains two checkboxes, both checked
		ScreenRegion s = createTestScreenRegionFrom("NetworkPreferences1.png");				
		List<DesktopScreenRegion> rs = s.findAll(checkboxTarget);
		assertThat("number of checked checkboxes found", rs.size(), is(2));
		
		// this image contains two checkboxes, one checked, one unchecked
		s = createTestScreenRegionFrom("NetworkPreferences2.png");				
		rs = s.findAll(checkboxTarget);
		assertThat("number of checked checkboxes found", rs.size(), is(2));		

	}
	
	@Test
	public void testGetCheckboxStates() throws IOException{

		// this image contains two checkboxes, top one is unchecked, bottom one is checked
		ScreenRegion s = createTestScreenRegionFrom("NetworkPreferences2.png");			
		checkboxTarget.setOrdering(Ordering.TOP_DOWN);
		List<DesktopScreenRegion> rs = s.findAll(checkboxTarget);
		
		assertThat("number of checkboxes found", rs.size(), is(2));
		
		assertThat("top checkbox", (String) rs.get(0).getState(), equalTo("unchecked"));
		assertThat("bottom checkbox", (String) rs.get(1).getState(), equalTo("checked"));
		
	}

	@Test
	public void testFiveCheckboxes() throws IOException{

		// this image contains five checkboxes, three checked and two unchecked
		ScreenRegion s = createTestScreenRegionFrom("DockPreferences.png");				
		List<DesktopScreenRegion> rs = s.findAll(checkboxTarget);
		assertThat("number of checkboxes found", rs.size(), is(5));
		
		rs = s.findAll(new ImageTarget(getClass().getResource("checkbox_checked.png")));
		assertThat("number of checked checkboxes found", rs.size(), is(3));		

		rs = s.findAll(new ImageTarget(getClass().getResource("checkbox_unchecked.png")));
		assertThat("number of unchecked checkboxes found", rs.size(), is(2));		

	}
	
	@Test
	public void testNoStateSpecified() throws IOException {
		
		ScreenRegion s = createTestScreenRegionFrom("DockPreferences.png");
		MultiStateTarget target = new MultiStateTarget();
		assertThat("find when no state is specified returns", s.find(target), nullValue());
		
		assertThat("findAll when no state is specified returns", s.findAll(target).size(), is(0));
	}



}