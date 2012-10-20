package org.sikuli.api;

import java.net.URL;

public class TestImage {

	public static URL get(String string) {
		return TestImage.class.getResource(string);
	}

}
