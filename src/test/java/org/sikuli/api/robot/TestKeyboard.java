package org.sikuli.api.robot;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import static org.junit.Assert.*;

public class TestKeyboard {
	private CopySource cs;
	private Keyboard kb;
	@Before
	public void setup() throws InterruptedException, InvocationTargetException {
		cs = new CopySource();
		SwingUtilities.invokeAndWait(new Runnable(){
			@Override
			public void run() {
				cs.setVisible(true);
			}});
		// make sure that the JFame is completely up before we attempt any test
		while (!cs.isReady())
			Thread.sleep(100);
		kb = new DesktopKeyboard();
	}
	
	@After
	public void teardown() {
		cs.setVisible(false);
		cs.dispose();
		cs = null;
		kb = null;
	}
	
//	@Test
	public void testCopyFirst() throws InterruptedException, InvocationTargetException {
		String s = kb.copy();
		assertEquals("hello", s);
	}
	
//	@Test
	public void testCopySecond() {
		kb.type(Key.TAB);
		String s = kb.copy();
		assertEquals("world", s);
	}

//	@Test
	public void testCopyThird() {
		kb.type(Key.TAB);
		kb.type(Key.TAB);
		String s = kb.copy();
		assertEquals("test ü", s);
	}
}
