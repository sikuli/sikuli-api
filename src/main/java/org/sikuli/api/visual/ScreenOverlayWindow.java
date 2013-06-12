package org.sikuli.api.visual;

import javax.swing.JWindow;
import com.sun.awt.AWTUtilities;
import edu.umd.cs.piccolo.PCanvas;

public class ScreenOverlayWindow extends JWindow implements ScreenDisplayable {

	private final PCanvas canvas;
	public ScreenOverlayWindow() {
		canvas = new PCanvas();
		canvas.setBackground(null);
		canvas.setOpaque(false); 
		add(canvas);
//		setBackground(null);
		getContentPane().setBackground(null); // this line is needed to make the window transparent on Windows
		AWTUtilities.setWindowOpaque(this, false);
		setAlwaysOnTop(true);						
	}

	public PCanvas getCanvas(){
		return canvas; 
	}
	
	@Override
	public void displayOnScreen() {
		setVisible(true);
		
	}

	@Override
	public void hideFromScreen() {
		setVisible(false);
		dispose();
	}		

}