package org.sikuli.api;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ScreenRegionTest extends BaseTest {
	
//	static {
//		ImageExplainer.getExplainer(DigitRecognizer.class).setLevel(ImageExplainer.Level.STEP);
//		ImageExplainer.getExplainer(TextMap.class).setLevel(ImageExplainer.Level.STEP);
//	}
	
	@Test
	public void testExtractInteger() throws IOException{
		DesktopScreenRegion screen = createTestScreenRegionFrom("321904.png");
		assertThat("integer", screen.extractInteger(), is(321904));
	}
	
	@Test
	public void testExtractIntegerInComboBox() throws IOException, InterruptedException{
		ScreenRegion screen = createTestScreenRegionFrom("NetworkPreferencesCreateNew.png");
		Target t = new ImageTarget(TestImage.get("channelLabel.png"));
		//ScreenRegion comboboxRegion = screen.find(t).getRight(100).grow(10,0,10,0);
		ScreenRegion labelRegion = screen.find(t);
		ScreenRegion comboboxRegion = labelRegion.getRelativeScreenRegion(labelRegion.getBounds().width, -10, 100, labelRegion.getBounds().height+20);
		assertThat("integer", ((DefaultScreenRegion) comboboxRegion).extractInteger(), is(11));
	}
	

}
