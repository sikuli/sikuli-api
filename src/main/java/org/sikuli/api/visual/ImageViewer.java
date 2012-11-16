package org.sikuli.api.visual;

import java.awt.HeadlessException;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolox.pswing.PSwingCanvas;

public class ImageViewer extends JFrame {

	private PImage imageNode;
	private PSwingCanvas canvas;
	private BufferedImage currentImage;
	double scale = 0.5f;

	public ImageViewer() throws HeadlessException {
		super();
	
		
		
		canvas = new PSwingCanvas();
		imageNode = new PImage();		
		imageNode.setScale(scale);
		canvas.getLayer().addChild(imageNode);		
		add(canvas);
		
		//double scale = image.getScale();
//		/updateImage(image);	
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	
	public void updateImage(BufferedImage newImage){		
		currentImage = newImage;
		imageNode.setImage(newImage);		
		setSize((int) (scale * currentImage.getWidth()), (int) (scale * currentImage.getHeight()));
		setLocationRelativeTo(null);
		canvas.invalidate();
	}
	
	

}
