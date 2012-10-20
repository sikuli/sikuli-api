package org.sikuli.api;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.Timer;

import org.sikuli.api.robot.DesktopScreen;
import org.sikuli.core.cv.ImageMask;
import org.sikuli.ocr.DigitRecognizer;
import org.sikuli.ocr.RecognizedDigit;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ScreenRegion {

	public int x;
	public int y;
	public int width;
	public int height;
	private Screen screen;	
	private BufferedImage lastCapturedImage;
	private double score;
	private boolean fullScreen;
	private Map<Target, Object> states = new ConcurrentHashMap<Target, Object>();
	private ImageMask mask = null;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	
	/**
	 * Gets the score of this ScreenRegion, which is set if this screen region
	 * was returned as the result of a find command. The score should be between
	 * 0 and 1 where 1 is the best.
	 * 
	 * @return the score
	 */
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	protected Map<Target, Object> getStates() {
		return states;
	}

	/**
	 * Defines a new state of this screen region as represented by the presence
	 * of the given target 
	 * 
	 * @param target	the target whose presence indicates this state
	 * @param state	the state, which can be any object
	 */
	public void addState(Target target, Object state) {
		states.put(target,  state);
	}

	/**
	 * Removes a particular state represented by the given target from this screen region
	 * 
	 * @param target the representative target of the state to remove
	 */
	public void removeState(Target target) {
		states.remove(target);
	}

	public Object getState() {

		Target topStateTarget = null;
		double topScore = 0;

		//Map<Target, Object> states = screenRegion.getStates();		
		Set<Target> keySet = states.keySet();		
		for (Target target : keySet){	
			//System.out.println(this + ">" + target);			
			ScreenRegion match = _find(target);
			//System.out.println("m:" + match);
			if (match != null && match.getScore() > topScore){
				topStateTarget = target;
				topScore = match.getScore();
			}			
		}
		if (topStateTarget != null){
			return states.get(topStateTarget);
		}else{
			return null;
		}
	}

	public ScreenRegion(ScreenRegion parent, int x, int y, int width, int height) {
		this.fullScreen = false;
		this.x = parent.x + x;
		this.y = parent.y + y;
		this.width = width;
		this.height = height;
		setScreen(parent.screen);
	}

	/**
	 * Creates a ScreenRegion in full screen on the default screen
	 */
	public ScreenRegion(){
		this.fullScreen = true;
		setScreen(new DesktopScreen(0));
	}

	/**
	 * Creates a ScreenRegion of the given x, y, width, height on the default screen 
	 * 
	 * @param x	x 
	 * @param y	y
	 * @param width	width
	 * @param height	height
	 */	
	public ScreenRegion(int x, int y, int width, int height) {
		this.fullScreen = false;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setScreen(new DesktopScreen(0));
	}


	/**
	 * Gets a new screen region of the given width to the right of this screen region. This new
	 * screen region would have the same y position and the same height.
	 * 
	 * @param w	the width of the new screen region
	 * @return	the new screen region
	 */
	public ScreenRegion getRight(int w){
		return new ScreenRegion(this, width, 0, w, height);
	}

	/**
	 * Gets a new screen region of the given width to the left of this screen region. This new
	 * screen region would have the same y position and the same height.
	 * 
	 * @param w	the width of the new screen region
	 * @return	the new screen region
	 */
	public ScreenRegion getLeft(int w){
		return new ScreenRegion(this, -w, 0, w, height);
	}

	/**
	 * Gets a new screen region of the given height above this screen region. This new
	 * screen region would have the same x position and the same width.
	 * 
	 * @param h	the width of the new screen region
	 * @return	the new screen region
	 */
	public ScreenRegion getAbove(int h){
		return new ScreenRegion(this, 0, -h, width, h);
	}

	/**
	 * Gets a new screen region of the given height above this screen region. This new
	 * screen region would have the same x position and the same width.
	 * 
	 * @param h	the width of the new screen region
	 * @return	the new screen region
	 */
	public ScreenRegion getBelow(int h){
		return new ScreenRegion(this, 0, height, width, h);
	}


	public String toString(){
		return String.format("[%d,%d,%d,%d,%1.3f]",x,y,width,height,score);
	}


	/**
	 * Finds all the instances of the target on the screen immediately
	 * 
	 * @param target	the target to find
	 * @return a list of ScreenRegions, each of which corresponds to a found target, or an empty list
	 * if no such target can be found
	 */
	public List<ScreenRegion> findAll(Target target){
		List<ScreenRegion> rs = target.doFindAll(this);
		APILogger.getLogger().findAllPerformed(this,target,rs);		
		return rs;
	}

	/**
	 * Finds a target on the screen immediately
	 * 
	 * @param target	the target to find on the current screen
	 * @return	the screen region occupied by the found target or <code>null</code> if the 
	 * can not be found now
	 */
	public ScreenRegion find(Target target){
		ScreenRegion result = _find(target);		
		APILogger.getLogger().findPerformed(this, target, result);
		return result;
	}	

	private ScreenRegion _find(Target target){
		// temporary override the limit to ONE
		int oldLimit = target.getLimit();
		target.setLimit(1);
		List<ScreenRegion> results = target.doFindAll(this);
		target.setLimit(oldLimit);

		ScreenRegion result;
		if (results.size() == 0){
			result = null;
		}else{
			result = results.get(0);
		}
		return result;
	}


	class RepeatFind{

		private volatile boolean timeout = false;	

		private Target target;
		private int duration;

		private ScreenRegion r = null;

		RepeatFind(Target target, int duration){		
			this.target = target;
			this.duration = duration;
		}

		public ScreenRegion run(){
			Timer t = new Timer(duration, new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					timeout = true;
				}				
			});
			t.start();

			while (r == null && !timeout){
				r = _find(target);	            
			}
			return r;
		}
	}

	/**
	 * Blocks and waits until a target is found in this screen region within a given time period
	 * 
	 * @param target the target to wait
	 * @param mills the maximum time to wait in milliseconds 
	 * @return a ScreenRegion object representing the region occupied by the found target,
	 * or null if the target can not be found within the given time  
	 */
	public ScreenRegion wait(final Target target, int mills){
		RepeatFind ru = new RepeatFind(target, mills);
		ScreenRegion result = ru.run();
		APILogger.getLogger().waitPerformed(this, target, mills, result);
		return result;
	}
	
	/**
	 * Grows this screen region above, left, below, and right 
	 * 
	 * @param above	the amount to grow above
	 * @param below	the amount to grow below
	 * @param left	the amount to grow left
	 * @param right	the amount to grow above
	 * @return	this ScreenRegion that has grown larger
	 */
	public ScreenRegion grow(int above, int right, int below, int left) {
		x -= left;
		y -= above;
		height = height + above + below;
		width = width + left + right;
		return this;
	}

	/**
	 * Gets a screen region on the same screen corresponding to the full screen
	 * 
	 * @return	a ScreenRegion corresponding to the full screen of the same screen
	 */
	public ScreenRegion getFullScreen() {
		ScreenRegion r = new ScreenRegion();
		r.setScreen(screen);
		return r;
	}

	/**
	 * Captures and returns a screenshot of this screen region
	 * 
	 * @return	a BufferedImage containing the screenshot. The type of the image is TYPE_3BYTE_BGR
	 */
	public BufferedImage capture() {
		lastCapturedImage = screen.getScreenshot(x, y, width, height);
		//lastCapturedImage = applyMask(lastCapturedImage);
		return lastCapturedImage;
	}

	private BufferedImage applyMask(BufferedImage input){
		if (mask != null){
			return mask.createMaskedImage(input);
		}
		return input;
	}

	/**
	 * Gets the screenshot last captured in this screen region
	 * 
	 * @return a BufferedImage that holds the screenshot last captured in this screen region
	 */
	public BufferedImage getLastCapturedImage(){
		if (lastCapturedImage == null)
			lastCapturedImage = capture();
		return lastCapturedImage;
	}

	/**
	 * Gets the center of this screen region 
	 * 
	 * @return a Location object corresponding to the center of the screen region
	 */
	public ScreenLocation getCenter() {
		return new ScreenLocation(x + width/2, y + height/2, screen);
	}

	/**
	 * Gets the location of this screen region, which is the top-left corner of 
	 * this screen region
	 * 
	 * @return the screen location of this screen region
	 */
	public ScreenLocation getLocation(){
		return getTopLeft();
	}

	/**
	 * Gets the top left corner of this screen region
	 * 
	 * @return	a Location object corresponding to the top left corner of the region
	 */
	public ScreenLocation getTopLeft() {
		return new ScreenLocation(x, y, screen);
	}



	/**
	 * Gets the bottom right corner of this screen region
	 * 
	 * @return a Location object corresponding to the bottom right corner of the region
	 */
	public ScreenLocation getBottomRight() {
		return new ScreenLocation(x + width, y + height, screen);
	}


	/**
	 * Adds a listener for a given target
	 * 
	 * @param target	the target to listener its events for
	 * @param listener	the listener to handle the events associated with the target
	 */
	public void addTargetEventListener(Target target, TargetEventListener listener) {	
		VisualEventManager.getSingleton().addTargetEventListener(this,  target, listener);
	}
	/**
	 * Removes a particular listener for a particular target
	 * 
	 * @param target	the target from which the given listener should be removed
	 * @param listener	the listener to remove from the given target
	 */
	public void removeTargetEventListener(Target target, TargetEventListener listener) {
		VisualEventManager.getSingleton().removeTargetEventListener(this,  target, listener);		
	}

	public ScreenRegion snapshot(){

		
		Dimension size = screen.getSize();
		final BufferedImage fullScreenshot = screen.getScreenshot(0, 0, size.width, size.height);
		ScreenRegion r = new ScreenRegion(this, 0, 0, width, height);
		r.setScreen(new Screen(){

			@Override
			public BufferedImage getScreenshot(int x, int y, int width, int height) {
				// need to crop this based on x, y, width, height
				return fullScreenshot;
			}

			@Override
			public Dimension getSize() {
				return screen.getSize();
			}
			
		});
		return r;
	}


	/**
	 * Records a video within this screen region for a certain duration (in milliseconds). 
	 * The video is written to a given output file. This method is non-blocking.
	 * Only one recording can take place for the same ScreenRegion object. 
	 * 
	 * @param output	the file to write the movied to	(must carries a .mov extension)
	 * @param duration	the duration to record (in milliseconds)
	 */
	public void record(File output, int duration) {		
		ScreenRegionRecorder recorder = new ScreenRegionRecorder(this,output);
		recorder.start(duration);
	}

	/**
	 * Adds a listener to handle the state changes within this screen region
	 * 
	 * @param listener	the listener to handle state changes in this screen region
	 */
	public void addStateChangeEventListener(StateChangeListener listener) {
		VisualEventManager.getSingleton().addStateChangeEventListener(this, listener);		
	}

	public ImageMask getMask() {
		return mask;
	}

	public void setMask(ImageMask mask) {
		this.mask = mask;
	}


	// x, y are relative to the upper-left corner of this ScreenRegion
	// no boundary check is built in yet
	List<Rectangle> rois = Lists.newArrayList(); 
	public void addROI(int x, int y, int width, int height){
		rois.add(new Rectangle(x,y,width,height));
	}

	public List<Rectangle> getROIs(){		
		return ImmutableList.copyOf(rois);
	}

	public boolean contains(ScreenRegion r) {
		return (new Rectangle(x,y,width,height)).contains(new Rectangle(r.x,r.y,r.width,r.height));
	}

	public Integer extractInteger() {
		List<RecognizedDigit> digits = DigitRecognizer.recognize(capture());
		Collections.sort(digits, new Comparator<RecognizedDigit>(){
			@Override
			public int compare(RecognizedDigit d1, RecognizedDigit d2) {
				return d1.x -d2.x;
			}			
		});
		String numberString = "";
		for (RecognizedDigit d : digits){
			numberString += d.digit;
		}
		return Integer.parseInt(numberString);
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
		if (fullScreen){
			Dimension b = screen.getSize();
			x = 0;
			y = 0;
			width = b.width;
			height = b.height;
		}
	}




}
