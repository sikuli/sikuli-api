package org.sikuli.api.examples.experimental;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

import org.sikuli.api.*;
import org.sikuli.api.examples.images.Images;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PImage;


public class DragDropExample {
	private static class DragDropFrame extends JFrame {

		private PImage image;

		DragDropFrame(URL background, URL dragTarget) throws IOException {
			final PCanvas canvas = new PCanvas();
			image = new PImage();

			BufferedImage backgroundImage = ImageIO.read(background);
			PImage backgroundNode = new PImage();
			backgroundNode.setImage(backgroundImage);
			//backgroundNode.setPickable(false);
			//canvas.get
			
			BufferedImage dragTargetImage = ImageIO.read(dragTarget);
			PImage dragTargetNode = new PImage();
			dragTargetNode.setPickable(true);			
			dragTargetNode.setOffset(10,10);
			dragTargetNode.setImage(dragTargetImage);
						
			canvas.getLayer().addChild(backgroundNode);
			canvas.getLayer().addChild(dragTargetNode);						
			
			add(canvas);
			
			dragTargetNode.addInputEventListener(new PDragEventHandler(){

				@Override
				protected void startDrag(PInputEvent event) {
				//	System.out.println("Start dragging");
				}
				
			});
			
			dragTargetNode.addInputEventListener(new PBasicInputEventHandler(){

				@Override
				public void keyTyped(PInputEvent event) {
					super.keyTyped(event);
					
					System.out.println("Key typed: " + event.getKeyChar());

				}
				
				@Override
				public void keyPressed(PInputEvent event) {
					super.keyPressed(event);
					
					System.out.println("Key pressed: " + event.getKeyChar());

				}

				@Override
				public void mousePressed(PInputEvent event) {					
					super.mousePressed(event);
					
					System.out.println("Mouse pressed: button " + event.getButton());
				}

				@Override
				public void mouseReleased(PInputEvent event) {
					super.mouseReleased(event);
					System.out.println("Mouse released: button " + event.getButton());
				}
				
			});
//			
			
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(backgroundImage.getWidth(), backgroundImage.getHeight()+25);
			setVisible(true);
		}

		public void updateImage(URL url) throws IOException {		
			image.setImage(ImageIO.read(url));			
		}
		
		
		
		public void autoClose(int duration){
			new Timer(duration, new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {					
					dispose();
				}			
			}).start();	
		}
		
		
	}


	// TODO: this is not working yet
	public static void main(String[] args) throws IOException, InterruptedException {
		URL backgroundImage = Images.GoogleSearchPage;
		URL dragTargetImage = Images.OSXDockIcon;
				
		DragDropFrame imageFrame = new DragDropFrame(backgroundImage,dragTargetImage);
		imageFrame.autoClose(10000);
		
		ScreenRegion s = new DesktopScreenRegion();		
		Mouse mouse = new DesktopMouse();

		URL imageURL = Images.OSXDockIcon;                
		Target imageTarget = new ImageTarget(imageURL);
		
		ScreenRegion r = s.wait(imageTarget,1000);
		mouse.drag(r.getCenter());
		
		
		imageURL = Images.GoogleMicrophoneIcon;                
		imageTarget = new ImageTarget(imageURL);
		
		r = s.wait(imageTarget,1000);
		mouse.drop(r.getCenter());
		
		
		ScreenLocation c = mouse.getLocation();		
		mouse.drag(c);
		mouse.drop(Relative.to(c).left(200).above(50).getScreenLocation());

	}		
}
