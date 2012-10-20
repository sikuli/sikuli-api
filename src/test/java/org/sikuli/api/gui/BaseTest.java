package org.sikuli.api.gui;

import java.awt.Rectangle;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.TestImage;

public class BaseTest {
	
	protected ScreenSimulator simulator;
	protected ScreenRegion screenRegion;
	protected GUI gui;
	
	static {		
		BasicConfigurator.configure();
//		ImageExplainer.getExplainer(FontModelLearner.class).setLevel(ImageExplainer.Level.ALL);//STEP);		
//		Logger.getLogger(FontModelLearner.class).setLevel(Level.ALL);
	}

	public void setUpTestScreen(final String name) {		
		simulator = new ScreenSimulator(){
			public void run(){
				showImage(TestImage.get(name));
			}
		};
		simulator.setSize(800,800);
		simulator.start();
		Rectangle b = simulator.getBounds();
		screenRegion = new ScreenRegion(b.x,b.y,b.width,b.height);		
	}

	@Deprecated
	public void setUpTestScreen(final String name, final int timeout) throws IOException{		
		simulator = new ScreenSimulator(){
			public void run(){
				showImage(TestImage.get(name));
			}
		};
		simulator.setSize(800,800);
		simulator.start();
		Rectangle b = simulator.getBounds();
		screenRegion = new ScreenRegion(b.x,b.y,b.width,b.height);
	}

	
	@After
	public void tearDown() throws InterruptedException{
		simulator.wait(3000);
		simulator.close();
	}

}
