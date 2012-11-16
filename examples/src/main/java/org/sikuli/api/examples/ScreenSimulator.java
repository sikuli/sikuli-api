package org.sikuli.api.examples;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.sikuli.api.ScreenRegionRecorder;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolox.pswing.PSwing;
import edu.umd.cs.piccolox.pswing.PSwingCanvas;

public class ScreenSimulator {

	private final Dimension DEFAULT_SIZE = new Dimension(640,480);
	private ImageFrame frame = new ImageFrame();
	
	

	public void run(){
		
	}
	
	public void start(){
		Thread t = new Thread(){
			public void run(){
				frame.setVisible(true);
				ScreenSimulator.this.run();
			}
		};
		t.setDaemon(true);
		t.start();		
		wait(1000);
	}
	
	public void showImage(BufferedImage image){		
		frame.image.setImage(image);
		frame.setSize(image.getWidth(),image.getHeight());
		frame.repaint();
	}
	
	public void showImage(URL url) {
		BufferedImage bimage;
		try {
			bimage = ImageIO.read(url);
			frame.image.setImage(bimage);
			frame.repaint();
		} catch (IOException e) {
		}
	}
	
	public void addTextField(int x, int y) {
		//JTextField f = new JTextField(20);
		//PSwing s = new PSwing(f);
		//frame.canvas.getLayer().addChild(s);
		frame.addTextField(x,y);
	}
	
	public void wait(int duration){
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
		}		
	}
	
	Object clickObserver = new Object();
	public void waitForClick(){
		synchronized(clickObserver){
			try {
				clickObserver.wait();
			} catch (InterruptedException e) {
			}
		}
	}

	public void close(){
		frame.setVisible(false);
		frame.dispose();
		
		ScreenRegionRecorder.awaitTermination();
		System.exit(0);
	}


	private class ImageFrame extends JFrame {

		PImage image;
		PCanvas canvas;
		PSwing textField;
		//static private final Rectangle DEFAULT_BOUNDS = new Rectangle(50,50,640,480);
		
		void addTextField(int x, int y){
			JTextField f = new JTextField(20);
			PSwing p = new PSwing(f);
			p.setOffset(x, y);
			canvas.getLayer().addChild(p);
		}
		

		ImageFrame() {
			//canvas = new PCanvas();
			canvas = new PSwingCanvas();
			image = new PImage();

//			JTextField f = new JTextField(20);
//			textField = new PSwing(f);
//			textField.setVisible(false);
//			//frame.canvas.getLayer().addChild(s);

			//textField
			
			canvas.getLayer().addChild(image);
			//canvas.getLayer().addChild(textField);
			add(canvas);
			//
			image.addInputEventListener(new PBasicInputEventHandler(){

				@Override
				public void keyTyped(PInputEvent event) {
					super.keyTyped(event);

					//System.out.println("Key typed: " + event.getKeyChar());

				}

				@Override
				public void keyPressed(PInputEvent event) {
					super.keyPressed(event);
					clickObserver.notify();
					//System.out.println("Key pressed: " + event.getKeyChar());

				}

				@Override
				public void mousePressed(PInputEvent event) {					
					super.mousePressed(event);
					synchronized(clickObserver){
						clickObserver.notify();
					}
					//System.out.println("Mouse pressed: button " + event.getButton());
				}

				@Override
				public void mouseReleased(PInputEvent event) {
					super.mouseReleased(event);
					//System.out.println("Mouse released: button " + event.getButton());
				}

			});



			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(DEFAULT_SIZE);
			setLocationRelativeTo(null);
			//setVisible(true);
		}

	}

	public static void main(String[] args){

		ScreenSimulator s = new ScreenSimulator(){
			public void run(){
				showImage(Images.GoogleSearchPage);
				addTextField(20,20);
				wait(3000);
				showImage(Images.OSXDockPreferences);
				wait(3000);
				close();
			}
		};
		s.start();
	}

	public void setSize(int width, int height){
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
	}
	
	public void setLocation(int x, int y){
		frame.setLocation(x,y);
	}

	public Rectangle getBounds() {
		return frame.getBounds();		
	}

}
