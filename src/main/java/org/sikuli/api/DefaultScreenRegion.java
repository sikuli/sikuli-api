package org.sikuli.api;

import java.awt.Dimension;
import java.awt.Graphics;
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

import org.sikuli.core.cv.ImageMask;
import org.sikuli.ocr.DigitRecognizer;
import org.sikuli.ocr.RecognizedDigit;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class DefaultScreenRegion implements ScreenRegion {
	
	private Screen screen;	
	private BufferedImage lastCapturedImage;
	private double score;
	private boolean fullScreen;
	private Map<Target, Object> states = new ConcurrentHashMap<Target, Object>();
	private ImageMask mask = null;


	public DefaultScreenRegion(Screen screen) {
		super();
		this.fullScreen = true;
		setScreen(screen);
	}
	
	public DefaultScreenRegion(ScreenRegion parent, int x, int y, int width, int height) {		
		this.fullScreen = false;
		this.x = parent.getBounds().x + x;
		this.y = parent.getBounds().y + y;
		this.width = width;
		this.height = height;
		setScreen(parent.getScreen());
	}
	
	/**
	 * Creates a ScreenRegion of the given x, y, width, height on the default screen 
	 * 
	 * @param x	x 
	 * @param y	y
	 * @param width	width
	 * @param height	height
	 */	
	public DefaultScreenRegion(Screen screen, int x, int y, int width, int height) {
		this.fullScreen = false;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setScreen(screen);
	}


	
	private int x;
	private int y;
	private int width;
	private int height;
	
	
	@Override
	public Rectangle getBounds(){
		return new Rectangle(x,y,width,height);
	}
	
	@Override
	public void setBounds(Rectangle newBounds){
		x = newBounds.x;
		y = newBounds.y;
		width = newBounds.width;
		height = newBounds.height;
		
	}

	@Override
	public ScreenRegion getRelativeScreenRegion(int xoffset, int yoffset, int width, int height){
		return new DefaultScreenRegion(this, xoffset, yoffset, width, height);
	}
	
	@Override
	public ScreenLocation getRelativeScreenLocation(int xoffset, int yoffset){
		return new DefaultScreenLocation(screen, x + xoffset, y + yoffset);

	}

	
//	@Override
//	public ScreenRegion getRight(int w){
//		return new DefaultScreenRegion(this, width, 0, w, height);
//	}
//
//	@Override
//	public ScreenRegion getLeft(int w){
//		return new DefaultScreenRegion(this, -w, 0, w, height);
//	}
//
//	@Override
//	public ScreenRegion getAbove(int h){
//		return new DefaultScreenRegion(this, 0, -h, width, h);
//	}
//
//	@Override
//	public ScreenRegion getBelow(int h){
//		return new DefaultScreenRegion(this, 0, height, width, h);
//	}


	public String toString(){
		return String.format("[%d,%d,%d,%d,%1.3f]",x,y,width,height,score);
	}


	@Override
	public List<ScreenRegion> findAll(Target target){
		List<ScreenRegion> rs = target.doFindAll(this);
		return rs;
	}

	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#find(org.sikuli.api.Target)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#wait(org.sikuli.api.Target, int)
	 */
	@Override
	public ScreenRegion wait(final Target target, int mills){
		RepeatFind ru = new RepeatFind(target, mills);
		ScreenRegion result = ru.run();
		APILogger.getLogger().waitPerformed(this, target, mills, result);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#grow(int, int, int, int)
	 */
//	@Override
//	public ScreenRegion grow(int above, int right, int below, int left) {
//		x -= left;
//		y -= above;
//		height = height + above + below;
//		width = width + left + right;
//		return this;
//	}

	/**
	 * Gets a screen region on the same screen corresponding to the full screen
	 * 
	 * @return	a ScreenRegion corresponding to the full screen of the same screen
	 */
	public ScreenRegion getFullScreen() {
		ScreenRegion r = new DefaultScreenRegion(getScreen());
		return r;
	}

	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#capture()
	 */
	@Override
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

	@Override
	public ScreenLocation getCenter() {
		return new DefaultScreenLocation(screen, x + width/2, y + height/2);
	}

	@Override
	public void addTargetEventListener(Target target, TargetEventListener listener) {	
		VisualEventManager.getSingleton().addTargetEventListener(this,  target, listener);
	}

	@Override
	public void removeTargetEventListener(Target target, TargetEventListener listener) {
		VisualEventManager.getSingleton().removeTargetEventListener(this,  target, listener);		
	}
	
	
	static class StaticScreen implements Screen {
		
		static private BufferedImage crop(BufferedImage src, int x, int y, int width, int height){
		    BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		    Graphics g = dest.getGraphics();
		    g.drawImage(src, 0, 0, width, height, x, y, x + width, y + height, null);
		    g.dispose();
		    return dest;
		}
		
		final BufferedImage fullScreenshot;
		final Dimension screenSize;
		
		StaticScreen(ScreenRegion screenRegion){
			screenSize = screenRegion.getScreen().getSize();
			fullScreenshot = screenRegion.getScreen().getScreenshot(0, 0, screenSize.width, screenSize.height);
		}
		
		@Override
		public BufferedImage getScreenshot(int x, int y, int width, int height) {
			return crop(fullScreenshot, x, y, width, height);
		}

		@Override
		public Dimension getSize() {
			return screenSize;
		}
	}

	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#snapshot()
	 */
	@Override
	public ScreenRegion snapshot(){
		ScreenRegion r = new DefaultScreenRegion(new StaticScreen(this), x, y, width, height);
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

	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#addStateChangeEventListener(org.sikuli.api.StateChangeListener)
	 */
	@Override
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
	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#addROI(int, int, int, int)
	 */
	@Override
	public void addROI(int x, int y, int width, int height){
		rois.add(new Rectangle(x,y,width,height));
	}

	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#getROIs()
	 */
	@Override
	public List<Rectangle> getROIs(){		
		return ImmutableList.copyOf(rois);
	}

	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#contains(org.sikuli.api.ScreenRegion)
	 */
	//@Override
//	public boolean contains(ScreenRegion r) {
//		return (new Rectangle(x,y,width,height)).contains(getBounds());
//	}

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

	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#getScreen()
	 */
	@Override
	public Screen getScreen() {
		return screen;
	}

	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#setScreen(org.sikuli.api.Screen)
	 */
	@Override
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

	
	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#getScore()
	 */
	@Override
	public double getScore() {
		return score;
	}

	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#setScore(double)
	 */
	@Override
	public void setScore(double score) {
		this.score = score;
	}

	public Map<Target, Object> getStates() {
		return states;
	}

	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#addState(org.sikuli.api.Target, java.lang.Object)
	 */
	@Override
	public void addState(Target target, Object state) {
		states.put(target,  state);
	}

	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#removeState(org.sikuli.api.Target)
	 */
	@Override
	public void removeState(Target target) {
		states.remove(target);
	}

	/* (non-Javadoc)
	 * @see org.sikuli.api.ScreenRegion#getState()
	 */
	@Override
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


}