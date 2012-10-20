package org.sikuli.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import com.google.common.collect.Lists;


public class TextTargetTest extends BaseTest {

	private ScreenRegion screen;
	
	@Test
	public void testOSXNetworkPreferences() throws IOException {
		screen = createTestScreenRegionFrom("NetworkPreferences2.png");

		List<String> strings = Lists.newArrayList();
		strings.add("Network Name");
		strings.add("192.168.1.100");
		strings.add("Status");
		strings.add("Location");
		strings.add("Network");
		strings.add("Connect");
		strings.add("Known networks will be joined");
		strings.add("Ask to join new networks");

		for (String s : strings){
			ScreenRegion r = screen.find(new TextTarget(s));
			System.out.println(s + ":" + r);
			assertThat(s, r, notNullValue());
		}
	}

	@Test
	public void testWindowsSystemPropertiesComputerNameTab() throws IOException {
		screen = createTestScreenRegionFrom("SystemPropertiesComputerName.png");

		List<String> strings = Lists.newArrayList();
		strings.add("Computer description");
		strings.add("Workgroup");
		strings.add("Computer Name");
		strings.add("Advanced");
		strings.add("Hardware");
		strings.add("System Protection");
		strings.add("Remote");
		strings.add("Network ID");
		strings.add("Change");
		strings.add("OK");
		strings.add("Cancel");
		strings.add("Apply");

		for (String s : strings){
			ScreenRegion r = screen.find(new TextTarget(s));
			System.out.println(s + ":" + r);
			assertThat(s, r, notNullValue());
		}
	}
	
}


class ScreenRegionSimilarTo extends TypeSafeMatcher<ScreenRegion> {
	
	@Factory
	static <T> Matcher<ScreenRegion> similarTo(int x, int y, int width, int height){
		return new ScreenRegionSimilarTo(x,y,width,height);
	}


	ScreenRegion other;
	ScreenRegionSimilarTo(ScreenRegion other){
		this.other = other;
	}
	
	ScreenRegionSimilarTo(int x, int y, int width, int height){
		this.other = new ScreenRegion(x,y,width,height);
	}

	@Override
	public boolean matchesSafely(ScreenRegion self) {
		return (Math.abs(self.x - other.x) < 5 && 
				Math.abs(self.y - other.y) < 5 && 
				Math.abs(self.width - other.width) < 10 && 
				Math.abs(self.height - other.height) < 10);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("screen region should be similar to: " + other);
	}

}

