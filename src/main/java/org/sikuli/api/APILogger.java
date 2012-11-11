package org.sikuli.api;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.sikuli.core.draw.ImageRenderer;
import org.sikuli.core.draw.PiccoloImageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.nodes.PShadow;

public class APILogger {

	//apiLogger = 
	private static APILogger logger  = new DefaultLogger();
	public static APILogger getLogger(){
		return logger;
	}
	
	public static void setLogger(APILogger _logger){
		APILogger.logger = _logger;
	}

	public void findPerformed(ScreenRegion screenRegion, Target target, ScreenRegion result){
	}

	public void findAllPerformed(ScreenRegion screenRegion, Target target, List<ScreenRegion> result){
	}

	public void waitPerformed(ScreenRegion screenRegion, Target target, int duration, ScreenRegion result){
	}

	public void clickPerformed(ScreenLocation location){
	}

	public void rightClickPerformed(ScreenLocation location){
	}
	
	public void doubleClickPerformed(ScreenLocation location){
	}
	
	public void dragPerformed(ScreenLocation location){		
	}

	public void dropPerformed(ScreenLocation location){		
	}

	public void typePerformed(String text){
	}

	public void pastePerformed(String text){
	}	
	

	static public APILogger createStdoutLogger(){
		return new StdoutLogger();
	}
	
	static public APILogger createVisualLogger(ScreenRegion screenRegion, File outputDir){
		return new ScreenRegionImageLogger(screenRegion, outputDir);
	}

	
}

class StdoutLogger extends DefaultLogger {
	void out(String str){
		System.out.println(str);
	}
}

class ScreenRegionImageLogger extends APILogger {
//	void out(String str){
//		System.out.println(str);
//	}
	final private ScreenRegion loggedScreenRegion;
	final private File outputDir;
	ScreenRegionImageLogger(ScreenRegion region, File outputDir){
		this.loggedScreenRegion = region;
		this.outputDir = outputDir;
		if (!outputDir.exists())
			outputDir.mkdir();
	}
	
	void writeLogImage(BufferedImage image, String postfix){
		try {
			String filename = System.currentTimeMillis() + "-" + postfix + ".png";
			ImageIO.write(image, "png", new File(outputDir, filename));
		} catch (IOException e) {
		}
	}
	
	void logMouseAction(final String actionName, final ScreenLocation location){
		BufferedImage img = loggedScreenRegion.capture();
		ImageRenderer ir = new LogImageRenderer(img){

			@Override
			protected void addContent(PLayer layer) {
				PPath c = PPath.createEllipse(0, 0, 10,10);
				c.setPaint(Color.red);
				c.setOffset(location.x - loggedScreenRegion.getBounds().getX() - 5, location.y - loggedScreenRegion.getBounds().getY() - 5);
				layer.addChild(c);
				
				addTextLabel(layer, actionName,location.x - loggedScreenRegion.getBounds().x - 20, location.y - loggedScreenRegion.getBounds().y - 40);
				addNodeWithShadow(layer, c);
			}			
		};
		writeLogImage(ir.render(), actionName);		
	}
	
	
	// base class to provide a set of uniform drawing utility functions
	abstract class LogImageRenderer extends PiccoloImageRenderer{
		
		public LogImageRenderer(BufferedImage input) {
			super(input);
		}

		void addTextLabel(PNode parent, String txt, int x, int y){
			PText t = new PText(txt);
			t.setScale(1.5f);
			t.setPaint(Color.yellow);
			t.setOffset(x,y);
			addNodeWithShadow(parent, t);		
		}

		void addRectangle(PNode parent, int x, int y, int width, int height, Color color){
			PPath r = PPath.createRectangle(x, y, width, height);
			r.setStrokePaint(color);
			r.setStroke(new BasicStroke(2f));
			r.setPaint(null);	
			
			addNodeWithShadow(parent, r);
		}
	};
	
	private void logFindHelper(final String actionName, final ScreenRegion screenRegion, final Target target,
			final List<ScreenRegion> results){
		BufferedImage img = loggedScreenRegion.getLastCapturedImage();
		ImageRenderer ir = new LogImageRenderer(img){
			
			@Override
			protected void addContent(PLayer layer) {
				addTextLabel(layer, actionName, 3, 3);		
				
				// draw the target image
				BufferedImage targetImage = target.toImage();
				if (targetImage != null){
					PImage im = new PImage(targetImage);
					im.setOffset(3, 30);
					im.setPaint(Color.black);
					addNodeWithShadow(layer, im);
				}
				
				// draw a rectangle around the screen region in which find was performed
				addRectangle(layer, screenRegion.getBounds().x - loggedScreenRegion.getBounds().x, 
						screenRegion.getBounds().y - loggedScreenRegion.getBounds().y, screenRegion.getBounds().width, screenRegion.getBounds().height,
						Color.green);
				
				// draw a rectangle around the found target
				for (ScreenRegion result : results){
					addRectangle(layer, result.getBounds().x - loggedScreenRegion.getBounds().x, 
							result.getBounds().y - loggedScreenRegion.getBounds().y, result.getBounds().width, result.getBounds().height,
							Color.red);
				}
				
			}	
		};
		writeLogImage(ir.render(), actionName);	
	}
	
	
	@Override
	public void findAllPerformed(final ScreenRegion screenRegion, final Target target,
			final List<ScreenRegion> results) {
		logFindHelper("FindAll", screenRegion, target, results);		
	}

	@Override
	public void findPerformed(final ScreenRegion screenRegion, final Target target,
			final ScreenRegion result) {
		List<ScreenRegion> results = Lists.newArrayList();
		if (result != null){
			results.add(result);
		}			
		logFindHelper("Find", screenRegion, target, results);
	}

	@Override
	public void clickPerformed(ScreenLocation location){
		logMouseAction("Click", location);		
	}
	
	@Override
	public void rightClickPerformed(ScreenLocation location){
		logMouseAction("RightClick", location);		
	}

	@Override
	public void doubleClickPerformed(ScreenLocation location){
		logMouseAction("DoubleClick", location);		
	}

	@Override
	public void dragPerformed(ScreenLocation location) {
		logMouseAction("Drag", location);
	}

	@Override
	public void dropPerformed(ScreenLocation location) {
		logMouseAction("Drop", location);
	}

}


//class PrintStreamLogger extends DefaultLogger {
//	PrintStream stream;
//	public PrintStreamLogger(PrintStream outstream){
//		this.stream = outstream;
//	}
//	
//	void out(String str){
//		stream.println(str);
//	}
//}



class DefaultLogger extends APILogger {
	static Logger apiLogger = LoggerFactory.getLogger("org.sikuli.api");

	String now(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(new Date());
	}
	
	void out(String str){
		apiLogger.trace(str);
	}
	
	@Override
	public void typePerformed(String text){
		out(now() + "\t" + "Type " + text);
	}
	
	@Override
	public void clickPerformed(ScreenLocation location){
		out(now() + "\t" + "Click at " + location);
	}
	
	
	@Override
	public void rightClickPerformed(ScreenLocation location){
		out(now() + "\t" + "Right click at " + location);
	}

	@Override
	public void doubleClickPerformed(ScreenLocation location){
		out(now() + "\t" + "Double click at " + location);
	}

	@Override
	public void findPerformed(ScreenRegion screenRegion, Target target, ScreenRegion result){
		out(now() + "\t" + "Find " + target + ((result != null) ? " at " + result : " not found"));
	}

	@Override
	public void findAllPerformed(ScreenRegion screenRegion, Target target, List<ScreenRegion> result){
		out(now() + "\t" + "FindAll " + target + "\t" + result.size() + " matches found");
		for (int i = 0; i < result.size(); ++i){
			out("\t\t" + (i+1) + ":" + result.get(i));
		}
	}

	@Override
	public void waitPerformed(ScreenRegion screenRegion, Target target, int duration, ScreenRegion result){
		out(now() + "\t" + "Wait " + target + ((result != null) ? " at " + result : " not found"));
	}
}
