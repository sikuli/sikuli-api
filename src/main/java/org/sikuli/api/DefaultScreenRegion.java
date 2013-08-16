package org.sikuli.api;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.Timer;

import org.sikuli.api.event.StateChangeListener;
import org.sikuli.api.event.TargetEventListener;
import org.sikuli.api.event.VisualEventManager;
import org.sikuli.core.cv.ImageMask;
import org.sikuli.ocr.DigitRecognizer;
import org.sikuli.ocr.RecognizedDigit;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
/**
 * This class provides manipulation methods that can be used by the default screen region.
 *
 */
public class DefaultScreenRegion extends AbstractScreenRegion implements ScreenRegion {

	private BufferedImage lastCapturedImage;
	private Map<Target, Object> states = new ConcurrentHashMap<Target, Object>();
	private ImageMask mask = null;

	/**
	 * Constructs a new DefaultScreenRegion on the specified Screen object whose 
	 * upper-left corner is at (0, 0) in the coordinate space.
	 *  
	 * @param screen The Screen to create a region from.
	 */
	public DefaultScreenRegion(Screen screen){
		super(screen);
	}
	/**
	 * Constructs a new DefaultScreenRegion object whose Screen is the same as the parent
	 * ScreenRegion with the specified region values.
	 * 
	 * @param parent the parent of this ScreenRegion
	 * @param x The X coordinate of the upper-left corner of the rectangular screen region.
	 * @param y The Y coordinate of the upper-left corner of the rectangular screen region.
	 * @param width The width of the rectangular screen region.
	 * @param height The height of the rectangular screen region.
	 */
	public DefaultScreenRegion(ScreenRegion parent, int x, int y, int width, int height) {
		super(parent.getScreen());
		setX(parent.getBounds().x + x);
		setY(parent.getBounds().y + y);
		setWidth(width);
		setHeight(height);
	}
	/**
	 * Constructs a new DefaultScreenRegion object on the specified Screen and region values.
	 * 
	 * @param screen The Screen to create a region from.
	 * @param x The X coordinate of the upper-left corner of the rectangular screen region.
	 * @param y The Y coordinate of the upper-left corner of the rectangular screen region.
	 * @param width The width of the rectangular screen region.
	 * @param height The height of the rectangular screen region.
	 */
	public DefaultScreenRegion(Screen screen, int x, int y, int width, int height) {
		super(screen, x, y, width, height);
	}

	@Override
	public List<ScreenRegion> findAll(Target target){
		List<ScreenRegion> rs = target.doFindAll(this);
		return rs;
	}

	@Override
	public ScreenRegion find(Target target){
		ScreenRegion result = _find(target);		
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


	static private int POLL_INTERVAL = 500;

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
			t.setRepeats(false);

			while (r == null && !timeout){
				r = _find(target);		
				try {
					Thread.sleep(POLL_INTERVAL);
				} catch (InterruptedException e1) {
				}
			}
			t.stop();
			return r;
		}
	}	

	@Override
	public ScreenRegion wait(final Target target, int mills){
		RepeatFind ru = new RepeatFind(target, mills);
		ScreenRegion result = ru.run();
		return result;
	}

	@Override
	public BufferedImage capture() {
		lastCapturedImage = getScreen().getScreenshot(getX(), getY(), getWidth(), getHeight());
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
	 * Returns the last captured screenshot in this screen region.
	 * 
	 * @return a BufferedImage that holds the last captured screenshot in this screen region.
	 */
	public BufferedImage getLastCapturedImage(){
		if (lastCapturedImage == null)
			lastCapturedImage = capture();
		return lastCapturedImage;
	}
	
	@Override
	public ScreenLocation getUpperLeftCorner() {
		return new DefaultScreenLocation(getScreen(), getX(), getY());
	}
	
	@Override
	public ScreenLocation getLowerLeftCorner() {
		return new DefaultScreenLocation(getScreen(), getX(), getY() + getHeight());
	}
	
	@Override
	public ScreenLocation getUpperRightCorner() {
		return new DefaultScreenLocation(getScreen(), getX() + getWidth(), getY());
	}
	
	@Override
	public ScreenLocation getLowerRightCorner() {
		return new DefaultScreenLocation(getScreen(), getX() + getWidth(), getY() + getHeight());
	}

	@Override
	public ScreenLocation getCenter() {
		return new DefaultScreenLocation(getScreen(), getX() + getWidth()/2, getY() + getHeight()/2);
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

	@Override
	public ScreenRegion snapshot(){
		ScreenRegion r = new DefaultScreenRegion(new StaticScreen(this), getX(), getY(), getWidth(), getHeight());
		return r;
	}

	@Override
	public void addStateChangeEventListener(StateChangeListener listener) {
		VisualEventManager.getSingleton().addStateChangeEventListener(this, listener);		
	}
	/**
	 * Returns the explicit masking of this DefaultScreenRegion.
	 * 
	 * @return the explicit masking of this DefaultScreenRegion.
	 */
	public ImageMask getMask() {
		return mask;
	}
	/**
	 * Sets the masking of this DefaultScreenRegion.
	 * 
	 * @param mask the masking of this DefaultScreenRegion.
	 */
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

	/**
	 * Extracts an integer from this screen region.
	 * 
	 * @return the Integer this screen region contains.
	 */
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


	/**
	 * Returns a map of {@link Target} objects and states, which can be any object, 
	 * of this DefaultScreenRegion.
	 * 
	 * @return a map of Target objects and their states.
	 */
	public Map<Target, Object> getStates() {
		return states;
	}
	
	@Override
	public void addState(Target target, Object state) {
		states.put(target,  state);
	}

	@Override
	public void removeState(Target target) {
		states.remove(target);
	}

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