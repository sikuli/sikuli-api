package org.sikuli.api.examples;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.sikuli.api.util.ScreenRegionRecorder;

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
		try {
			frame.addImage(ImageIO.read(url));
		} catch (IOException e) {
		}
	}
	
	public void addImageButton(URL url, String name){
		try {
			frame.addImageButton(ImageIO.read(url), name);
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


	private class ImageButton extends JButton implements MouseListener {
		private final String name; 
		ImageButton(BufferedImage image, String name){
			this.setIcon(new ImageIcon(image));
			this.addMouseListener(this);
			this.name = name;
		}
		
		
		private void log(MouseEvent e, String event){
			String button = "";
			if (e.getButton() == MouseEvent.BUTTON1){
				button = "left";
			}else if (e.getButton() == MouseEvent.BUTTON3){
				button = "right";
			}
			System.out.println("[" + name + "] " +  button + " " + event );
		}
		
		@Override
		public void mouseClicked(MouseEvent arg0) {
			log(arg0, "clicked");
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			log(arg0, "pressed");			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			log(arg0, "released");			
		}
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
		
		void addImage(BufferedImage image){			
			JLabel l = new JLabel(new ImageIcon(image));
			getContentPane().removeAll();
			getContentPane().setLayout(new FlowLayout());
			getContentPane().add(l);
			pack();
		}		
		
		void addImageButton(BufferedImage image, String name){
			JButton b = new ImageButton(image, name);
			getContentPane().setLayout(new FlowLayout());
			getContentPane().add(b);
			pack();
			
//			canvas.getLayer().addChild(p);
//			p.addInputEventListener(new PBasicInputEventHandler(){
//
//				@Override
//				public void keyTyped(PInputEvent event) {
//					super.keyTyped(event);
//
//					//System.out.println("Key typed: " + event.getKeyChar());
//
//				}
//
//				@Override
//				public void keyPressed(PInputEvent event) {
//					super.keyPressed(event);
//					clickObserver.notify();
//					//System.out.println("Key pressed: " + event.getKeyChar());
//
//				}
//
//				@Override
//				public void mousePressed(PInputEvent event) {					
//					super.mousePressed(event);
////					synchronized(clickObserver){
////						clickObserver.notify();
////					}
//					System.out.println("Mouse pressed: button " + event.getButton());
//				}
//
//				@Override
//				public void mouseReleased(PInputEvent event) {
//					super.mouseReleased(event);
//					//System.out.println("Mouse released: button " + event.getButton());
//				}
//
//			});

			
		}

		ImageFrame() {
			//canvas = new PCanvas();
//			canvas = new PSwingCanvas();
//			image = new PImage();
//
////			JTextField f = new JTextField(20);
////			textField = new PSwing(f);
////			textField.setVisible(false);
////			//frame.canvas.getLayer().addChild(s);
//
//			//textField
//			
//			canvas.getLayer().addChild(image);
//			//canvas.getLayer().addChild(textField);
//			add(canvas);
			//
//			image.addInputEventListener(new PBasicInputEventHandler(){
//
//				@Override
//				public void keyTyped(PInputEvent event) {
//					super.keyTyped(event);
//
//					//System.out.println("Key typed: " + event.getKeyChar());
//
//				}
//
//				@Override
//				public void keyPressed(PInputEvent event) {
//					super.keyPressed(event);
//					clickObserver.notify();
//					//System.out.println("Key pressed: " + event.getKeyChar());
//
//				}
//
//				@Override
//				public void mousePressed(PInputEvent event) {					
//					super.mousePressed(event);
//					synchronized(clickObserver){
//						clickObserver.notify();
//					}
//					//System.out.println("Mouse pressed: button " + event.getButton());
//				}
//
//				@Override
//				public void mouseReleased(PInputEvent event) {
//					super.mouseReleased(event);
//					//System.out.println("Mouse released: button " + event.getButton());
//				}
//
//			});



			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(DEFAULT_SIZE);
			setResizable(false);
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
