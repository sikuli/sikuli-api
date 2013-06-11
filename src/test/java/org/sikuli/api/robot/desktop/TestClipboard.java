package org.sikuli.api.robot.desktop;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestClipboard {
	@Test
	public void testCopyPaste() {
		String expected = "hello world";
		Clipboard.putText(expected);
		String found = Clipboard.getText();
		assertEquals(expected, found);
	}
	
	@Test
	public void testClear() {
		String expected = "hello world";
		Clipboard.putText(expected);
		String found = Clipboard.getText();
		assertEquals(expected, found);
		
		Clipboard.clear();
		found = Clipboard.getText();
		assertNull(found);
	}
}
