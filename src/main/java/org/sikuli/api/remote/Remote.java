package org.sikuli.api.remote;

import org.openqa.selenium.remote.ExecuteMethod;
import org.sikuli.api.Screen;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;

public interface Remote {
	ExecuteMethod getExecutionMethod();
	ScreenRegion getScreenRegion();
	Mouse getMouse();
	Keyboard getKeyboard();
	Screen getScreen();
}
