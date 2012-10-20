package org.sikuli.api.visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JWindow;
import javax.swing.Timer;

import com.sun.awt.AWTUtilities;

import edu.umd.cs.piccolo.PCanvas;

public class ScreenOverlayWindow extends JWindow {

	private final PCanvas canvas;
	public ScreenOverlayWindow() {
		canvas = new PCanvas();
		canvas.setBackground(null);
		add(canvas);
		setBackground(null);
		getContentPane().setBackground(null); // this line is needed to make the window transparent on Windows
		AWTUtilities.setWindowOpaque(this, false);
		setAlwaysOnTop(true);						
	}

	public PCanvas getCanvas(){
		return canvas; 
	}
	
	public void show(int duration){
		setVisible(true);
		autoClose(duration);
	}

	private void autoClose(int time){
		Timer timer = new Timer(time, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}			
		});
		timer.start();
	}		

}