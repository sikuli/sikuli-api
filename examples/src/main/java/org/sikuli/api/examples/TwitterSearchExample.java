package org.sikuli.api.examples;
import java.net.MalformedURLException;
import java.net.URL;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.Key;

import static org.sikuli.api.API.*;

public class TwitterSearchExample {
	
	static Mouse mouse = new Mouse();
	static Keyboard keyboard = new Keyboard();

	public static void main(String[] args) throws MalformedURLException {
		
		browse(new URL("http://www.twitter.com/search"));

		ScreenRegion s = new DesktopScreenRegion();
				
		Target searchButtonImageTarget = new ImageTarget(Images.TwitterSearchButton);
				
		ScreenRegion searchButton = s.wait(searchButtonImageTarget, 5000);
		
		// Pause one second to allow search results to load
		pause(1000);				
		
		keyboard.type("sikuli");
		
		ScreenLocation outside = Relative.to(searchButton).right(50).center().getScreenLocation();
		mouse.click(outside);
				
		mouse.click(searchButton.getCenter());
		
		// Pause one second to allow search results to load
		pause(1000);
					
		// Scroll-down until the "View All Tweets Link" becomes visible
		Target viwAllTweetsLinkImageTarget = new ImageTarget(Images.TwitterViewAllTweetsLink);
		while (s.find(viwAllTweetsLinkImageTarget) == null) {
			keyboard.type(Key.PAGE_DOWN);					
		}
		
		// Find and click on the link
		ScreenRegion viwAllTweetsLink = s.find(viwAllTweetsLinkImageTarget);
		mouse.click(viwAllTweetsLink.getCenter());
			
	}
}