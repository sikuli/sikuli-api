package org.sikuli.api.examples;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.sikuli.api.API;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.Screen;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ScreenRegion;

import com.google.common.collect.ImmutableList;

public class CustomMultiScreenExample {
	
	
	static class MyApp extends JFrame implements Screen {

		static class MyButton extends JButton {
			MyButton(String name, URL url){
				setName(name);
				setIcon(new ImageIcon(url));
				addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.out.println(getName() + " is pressed.");			
					}				
				});
			}
		}

		private MyButton catButton;
		private MyButton dogButton;

		void moveButtonsRandomly(){

			for (JButton b : ImmutableList.of(catButton, dogButton)){

				int d = 30;		
				Point loc = b.getLocation();
				int dx = (int) ((Math.random()-0.5) * d);
				int dy = (int) ((Math.random()-0.5) * d);
				b.setLocation(loc.x + dx, loc.y + dy);
			}

		}

		MyApp(){
			setLayout(null);
			catButton = new MyButton("Cat", Images.Cat);		
			dogButton = new MyButton("Dog", Images.Dog);
			catButton.setLocation(new Point(50,50));
			dogButton.setLocation(new Point(220,150));

			catButton.setSize(new Dimension(150,150));
			dogButton.setSize(new Dimension(150,150));

			add(catButton);
			add(dogButton);

			setPreferredSize(new Dimension(500,500));
			setSize(new Dimension(500,500));
		}
		
		
		
		static void rectangle(ScreenRegion r){
			final MyApp f = (MyApp) r.getScreen();			
			
			// Add a rectangle to the frame around the given screen region object
			final JPanel l = new JPanel();
			l.setSize(r.getBounds().getSize());
			l.setLocation(r.getBounds().getLocation());
			l.setBorder(BorderFactory.createLineBorder(Color.red,3));
			l.setBackground(null);
			l.setOpaque(false);
			f.add(l,0);
			f.repaint();

			// Start a timed callback that will remove the rectangle
			// after one second
			Timer t = new Timer(1000, new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					SwingUtilities.invokeLater(new Runnable(){
						@Override
						public void run() {
							f.getContentPane().remove(l);	
							f.repaint();
						}});				
				}			
			});
			t.start();
		}
		
		
		
		static void click(ScreenLocation location){
			MyApp f = (MyApp) location.getScreen();
			Component comp = f.findComponentAt(location.x, location.y);	
			if (comp instanceof JButton){
				((JButton) comp).doClick();
			}
		}


		@Override
		public BufferedImage getScreenshot(int x, int y, int width, int height) {
			BufferedImage componentImage = getComponentImage(getContentPane());
			return crop(componentImage, x, y, width, height);				 
		}

		@Override
		public Dimension getSize() {
			return getContentPane().getSize();
		}


		BufferedImage getComponentImage(Component c){
			Dimension size = c.getSize();
			BufferedImage image = new BufferedImage(size.width, size.height,
					BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g2 = image.createGraphics();				
			c.paint(g2);
			g2.dispose();
			return image;
		}

		BufferedImage crop(BufferedImage src, int x, int y, int width, int height){
			BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = dest.getGraphics();
			g.drawImage(src, 0, 0, width, height, x, y, x + width, y + height, null);
			g.dispose();
			return dest;
		}

	}

	public static void main(String[] args) {

		final MyApp f1 = new MyApp();
		final MyApp f2 = new MyApp();
		
		f1.pack();		
		f1.setVisible(true);
		f1.setLocation(10,20);
		f1.setTitle("MyApp 1");
		
		f2.pack();		
		f2.setVisible(true);
		f2.setLocation(600,20);
		f2.setTitle("MyApp 2");

		ScreenRegion s1 = new DesktopScreenRegion();
		s1.setScreen(f1);

		ScreenRegion s2 = new DesktopScreenRegion();
		s2.setScreen(f2);

		for (int i=0; i < 10; i++){
			
			f1.moveButtonsRandomly();
			f2.moveButtonsRandomly();

			ScreenRegion dogRegion = s1.find(new ImageTarget(Images.Dog));
			ScreenRegion catRegion = s2.find(new ImageTarget(Images.Cat));
						
			MyApp.click(dogRegion.getCenter());
			MyApp.click(catRegion.getCenter());
			
			MyApp.rectangle(dogRegion);
			MyApp.rectangle(catRegion);
			

			API.pause(2000);
		}

	}
}



