package org.sikuli.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.sikuli.api.Target.Ordering;

public class ImageTargetTest extends BaseTest {

	ScreenRegion testScreenRegion;
	
	@Before
	public void setUp() throws IOException {
		testScreenRegion = createTestScreenRegionFrom("macdesktop.png");
	}
	
	@Test
	public void testSingleImageTargetIsFound() {
		Target t = new ImageTarget(getClass().getResource("dock.png"));
		t.setLimit(1);
		List<ScreenRegion> results = t.doFindAll(testScreenRegion);
		assertEquals(results.size(), 1);
		
		ScreenRegion m = results.get(0);
		assertThat("x", m.getBounds().x, is(876));
		assertThat("y", m.getBounds().y, is(186));
	}
	
	@Test
	public void testHigherMinScoreLessFuzzy() {
		Target t = new ImageTarget(getClass().getResource("dock.png"));
		t.setLimit(10);
		List<ScreenRegion> results = t.doFindAll(testScreenRegion);
		assertThat("number of matches found with default min score",
				results.size(), is(2));
		
		t.setMinScore(0.9);
		results = t.doFindAll(testScreenRegion);		
		assertThat("number of matches found with a higher min score",
				results.size(), is(1));
		
	}
	
	@Test
	public void testBufferedImageConstructor() {
		Target t = new ImageTarget(getClass().getResource("dock.png"));
		t.setLimit(1);
		List<ScreenRegion> results = t.doFindAll(testScreenRegion);
		assertEquals(results.size(), 1);
		
		ScreenRegion m = results.get(0);
		assertThat("x", m.getBounds().x, is(876));
		assertThat("y", m.getBounds().y, is(186));
	}
	
	@Test
	public void testFewerThanLimitImageTargetsAreFound() {		
		Target t = new ImageTarget(getClass().getResource("fileIcon.png"));
		t.setLimit(12);
		List<ScreenRegion> results = t.doFindAll(testScreenRegion);
		assertEquals(10, results.size());
	}

	@Test
	public void testFiveImageTargetsAreFound() {		
		Target t = new ImageTarget(getClass().getResource("fileIcon.png"));
		t.setLimit(5);
		List<ScreenRegion> results = t.doFindAll(testScreenRegion);
		assertEquals(5, results.size());
	}

	@Test
	public void testNoImageTargetIsFound() {		
		Target t = new ImageTarget(getClass().getResource("chicken.png"));
		List<ScreenRegion> results = t.doFindAll(testScreenRegion);
		assertEquals(0, results.size());
	}
	
	@Test
	public void testImageTargetIsNotFoundIfSmallerThanScreenRegion() {
		Target t = new ImageTarget(getClass().getResource("fileIcon.png"));
		ScreenRegion small = new DefaultScreenRegion(testScreenRegion, 0, 0, 33, 41); 
		ScreenRegion r = small.find(t);
		assertThat("find result", r, nullValue());
	}
	
	@Test
	public void testImageTargetsAreOrderedTopDown() {		
		Target t = new ImageTarget(getClass().getResource("fileIcon.png"));
		t.setOrdering(Ordering.TOP_DOWN);
		List<ScreenRegion> results = t.doFindAll(testScreenRegion);
		for (int i = 0; i < results.size() - 1; i++){
			ScreenRegion a = results.get(i);
			ScreenRegion b = results.get(i+1);
			assertThat("region(i).y <= region(i+1).y", a.getBounds().y, lessThanOrEqualTo(b.getBounds().y));
		}
	}

	@Test
	public void testImageTargetsAreOrderedLeftRight() {		
		Target t = new ImageTarget(getClass().getResource("fileIcon.png"));
		t.setOrdering(Ordering.LEFT_RIGHT);
		List<ScreenRegion> results = t.doFindAll(testScreenRegion);
		for (int i = 0; i < results.size() - 1; i++){
			ScreenRegion a = results.get(i);
			ScreenRegion b = results.get(i+1);
			assertThat("region(i).x <= region(i+1).x", a.getBounds().x, lessThanOrEqualTo(b.getBounds().x));
		}
	}

	@Test
	public void testImageTargetsAreOrderedRightLeft() {		
		Target t = new ImageTarget(getClass().getResource("fileIcon.png"));
		t.setOrdering(Ordering.RIGHT_LEFT);
		List<ScreenRegion> results = t.doFindAll(testScreenRegion);
		for (int i = 0; i < results.size() - 1; i++){
			ScreenRegion a = results.get(i);
			ScreenRegion b = results.get(i+1);
			assertThat("region(i).x >= region(i+1).x", a.getBounds().x, greaterThanOrEqualTo(b.getBounds().x));
		}
	}

	@Test
	public void testImageTargetsAreOrderedBottomUp() throws IOException{		
		Target t = new ImageTarget(getClass().getResource("fileIcon.png"));
		t.setOrdering(Ordering.BOTTOM_UP);
		List<ScreenRegion> results = t.doFindAll(testScreenRegion);
		for (int i = 0; i < results.size() - 1; i++){
			ScreenRegion a = results.get(i);
			ScreenRegion b = results.get(i+1);
			assertThat("region(i).y >= region(i+1).y", a.getBounds().y, greaterThanOrEqualTo(b.getBounds().y));
		}
	}
	
	@Test
	public void testImageTargetNotFoundInROI(){
		Target t = new ImageTarget(getClass().getResource("fileIcon.png"));
		testScreenRegion.addROI(0, 0, 100, 500);
		List<ScreenRegion> rs = t.doFindAll(testScreenRegion);		
		assertThat("number of targets found", rs.size(), is(0));
	}
	
	@Test
	public void testImageTargetFoundInROI(){
		Target t = new ImageTarget(getClass().getResource("fileIcon.png"));
		testScreenRegion.addROI(1450, 0, 120, 300);
		List<ScreenRegion> rs = t.doFindAll(testScreenRegion);		
		assertThat("number of targets found", rs.size(), is(3));
	}

	@Test
	public void testImageTargetFoundInTwoROIs(){
		Target t = new ImageTarget(getClass().getResource("fileIcon.png"));
		testScreenRegion.addROI(1450, 0, 120, 300);
		testScreenRegion.addROI(1570, 295, 120, 300);
		List<ScreenRegion> rs = t.doFindAll(testScreenRegion);		
		assertThat("number of targets found", rs.size(), is(6));
	}
	
	
}
