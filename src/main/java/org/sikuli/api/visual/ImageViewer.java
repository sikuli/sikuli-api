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
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(50,50);
		//setLocationRelativeTo(null);
	}
	
	
	public void updateImage(BufferedImage newImage){		
		currentImage = newImage;
		imageNode.setImage(newImage);		
		canvas.invalidate();
		
		setSize((int) (scale * currentImage.getWidth()), (int) (scale * currentImage.getHeight()));
//		setSize((int) imageNode.getWidth(), (int) imageNode.getHeight());
//		canvas.setBounds(0,0, (int) imageNode.getWidth(), (int) imageNode.getHeight());
//		pack();
		//setLocationRelativeTo(null);		
		
	}
	
	

}
