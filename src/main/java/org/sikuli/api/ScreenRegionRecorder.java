package org.sikuli.api;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.media.MediaLocator;
import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;

public class ScreenRegionRecorder{

	private Logger logger = LoggerFactory.getLogger(getClass());

	volatile boolean stopped = false;
	public int captureInterval = 200;
	public File store;

	final private File output;
	final private ScreenRegion screenRegion;
	public ScreenRegionRecorder(ScreenRegion screenRegion, File output) {
		this.output = output;
		this.screenRegion = screenRegion;
	}

	void recordFrame() throws IOException{
		BufferedImage image = screenRegion.capture();
		ImageIO.write(image, "jpeg", new File(store, System.currentTimeMillis() + ".jpeg"));
	}

	// wait for all pending tasks to complete
	public static void awaitTermination(){
		// wait for all capturing threads to finish
		for (Thread t : capturingThreads){
			try {
				t.join();
			} catch (InterruptedException e) {
			}
		}
		// wait for all movie writing tasks to finish
		makeMovieExecutor.shutdown();
		try {
			makeMovieExecutor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
		}
	}

	
	// CONCURRENCY DESIGN REQUIREMENTS
	//
	// Only one recording session per recorder
	// Multiple recorders could run concurrently
	// A singled shared execution thread for converting captured frames into videos
	// start() can be called only once
	//// A new instance of recorder is needed to start a new recording session
	
	private Thread capturingThread = null;
	
	volatile boolean recording = false; 
	
	synchronized public void stop(){
		stopped = true;
	}
	
	class CapturingThread extends Thread {
		
		public CapturingThread(){
			setName("screen-capture-thread");
		}
		
		@Override
		public void run() {
			logger.trace("started recording in screen region " + screenRegion);
			recording = true;
			try {
				while (!stopped) {
					recordFrame();
					logger.trace("captured a frame");
					Thread.sleep(captureInterval);
				}
			} catch (IOException e) {
				logger.debug("recording failed due to IO error: " + e);
			} catch (InterruptedException e) {
				logger.debug("recording failed due to interruption: " + e);
			}
			logger.trace("stopped recording");
			recording = false;
			stopped = false;
			submitMakeMovieJob();
		}
	};
	
	synchronized public void start(int duration){
		if (capturingThread != null && capturingThread.isAlive())
			return;

		store = Files.createTempDir();
		logger.trace("created a temporary directory to store captured frames at " + store);
		capturingThread = new CapturingThread();
		capturingThreads.add(capturingThread);
		capturingThread.start();

		Timer stopLaterThread = new Timer(duration, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop();
			}			
		});
		stopLaterThread.start();
	}

	private static ExecutorService makeMovieExecutor = Executors.newFixedThreadPool(1);
	private static List<Thread> capturingThreads = Collections.synchronizedList(new ArrayList<Thread>()); 
	synchronized private void submitMakeMovieJob() {
		Runnable job = new Runnable(){
			public void run(){
				logger.trace("making the movie ...");
				JpegImagesToMovie imageToMovie = new JpegImagesToMovie();
				Vector<String> imgLst = new Vector<String>();
				//File f = new File(store);
				File[] fileLst = store.listFiles();
				for (int i = 0; i < fileLst.length; i++) {
					imgLst.add(fileLst[i].getAbsolutePath());
				}
				logger.trace("writing " + imgLst.size() + " frames to file " + output);
				// Generate the output media locators.
				MediaLocator oml;
				if ((oml = imageToMovie.createMediaLocator(output.toString())) == null) {
					logger.debug("Cannot build media locator from: " + output);
				}
				
				if (imageToMovie.doIt(screenRegion.getBounds().width, 
						screenRegion.getBounds().height, (1000 / captureInterval), imgLst, oml) == false){
					logger.debug("Failed to execute JpegImagesToMovie");
				}
				logger.trace("movie is written to " + output);
			}
		};

		makeMovieExecutor.execute(job);
	}
}







