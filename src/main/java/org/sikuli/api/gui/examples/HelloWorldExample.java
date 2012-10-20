package org.sikuli.api.gui.examples;

import org.sikuli.api.API;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.gui.GUI;
import org.sikuli.api.robot.Key;
import org.sikuli.core.logging.ImageExplainer;
import org.sikuli.core.logging.ImageExplainer.Level;

public class HelloWorldExample {

	public static void main(String[] args) {
		
//		ImageExplainer.getExplainer(ButtonFinder.class).setLevel(Level.ALL);
		
		GUI gui = new GUI("/org/sikuli/api/gui/lion");
		gui = gui.region(10,10,500,500);
		//gui.showBorder();
		gui.label("Packag").showBorder();
		
		//setScreenRegion(r);		
//		gui.label("Users & Groups").click();
//		gui.textField("Full Name").enterText("Tom Yeh");

		gui.pressKey(Key.CMD)
		.pressAndReleaseKeys(Key.TAB)
		.releaseKey(Key.CMD);
		
//		gui.pressKey(Key.CMD)
//		.pressAndReleaseKeys("a")
//		.releaseKey(Key.CMD);
		
//
//		
//		gui.pressKey(Key.CMD)
//		.pressAndReleaseKeys(Key.UP)
//		.releaseKey(Key.CMD);
//		
//		gui.pressKey(Key.CMD)
//		.pressAndReleaseKeys(Key.DOWN)
//		.releaseKey(Key.CMD);
//		
//		gui.rightClick();
		
//		gui.label("TestDocument").box();
//		gui.label("Dropbox").box();
		
//		gui.label("TestDocument").box().drag();
//		gui.label("Dropbox").box().drop();

		//gui.label("The methods").box();
//		gui.pressKey(Key.CMD)
//		.pressAndReleaseKeys(Key.TAB,Key.TAB,Key.TAB,Key.TAB)
//		.releaseKey(Key.CMD);
		
		
		//gui.label("The methods").click();
//		API.pause(1000);
		
		
//		.abel("The methods").pressKey(Key.CMD).click().click().
//			releaseKey(Key.CMD);
//		
//		gui.label("The methods").pressKey(Key.CMD).click().click().
//		releaseKey(Key.CMD);
	
		
//		gui.checkBox("Minimize windows into").check();
		
//		gui.radioButton("Left").check();
		//gui.radioButton("Bottom").check();
		//gui.radioButton("Right").check();
		
		//gui.comboBox("Minimize windows using").click();
		
		//gui.button("Input Sources...").click();		
		//gui.label("Input source shortcuts").box();
		//gui.textField("DHCP Client ID:").box();
		//gui.comboBox("Calendar").box();
		
	}
}
