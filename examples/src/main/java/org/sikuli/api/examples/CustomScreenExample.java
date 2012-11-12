package org.sikuli.api.examples;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.CellRendererPane;
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

public class CustomScreenExample {


	static class MyApp extends JFrame {

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

	}

	static class MyMouse {

		JFrame f;
		MyMouse(JFrame f){
			this.f = f;
		}

		void click(ScreenLocation location){
			Component comp = f.findComponentAt(location.getX(), location.getY());	
			if (comp instanceof JButton){
				((JButton) comp).doClick();
			}
		}
	}

	static class MyScreen implements Screen {

		JFrame f;
		MyScreen(JFrame f){
			this.f = f;
		}

		@Override
		public BufferedImage getScreenshot(int x, int y, int width, int height) {
			BufferedImage componentImage = getComponentImage(f.getContentPane());
			return crop(componentImage, x, y, width, height);				 
		}

		@Override
		public Dimension getSize() {
			return f.getContentPane().getSize();
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
	};

	static class MyPainter {

		JFrame f;
		MyPainter(JFrame f){
			this.f = f;
		}

		void rectangle(ScreenRegion r){
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
	}

	public static void main(String[] args) {

		final MyApp f = new MyApp();		
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);

		MyScreen myScreen = new MyScreen(f);
		MyMouse myMouse = new MyMouse(f);
		MyPainter myPainter = new MyPainter(f);

		ScreenRegion s = new DesktopScreenRegion();
		s.setScreen(myScreen);

		for (int i=0; i < 10; i++){

			f.moveButtonsRandomly();

			ScreenRegion dogRegion = s.find(new ImageTarget(Images.Dog));
			ScreenRegion catRegion = s.find(new ImageTarget(Images.Cat));

			myMouse.click(dogRegion.getCenter());
			myMouse.click(catRegion.getCenter());

			myPainter.rectangle(dogRegion);
			myPainter.rectangle(catRegion);

			API.pause(2000);
		}

	}
}



