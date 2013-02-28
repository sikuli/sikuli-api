package org.sikuli.api.examples;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class MouseExample {
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showComponent(new TwoImageButtons());
			wait(10000);
			close();
		}
	};	

	static class TwoImageButtons extends JPanel {
		TwoImageButtons(){
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			add(new ButtonEventViewer(Images.Dog));
			add(new ButtonEventViewer(Images.Cat));
		}
	}

	static class ButtonEventViewer extends JPanel {

		DefaultListModel eventListModel = new DefaultListModel();
		JList eventList;

		ButtonEventViewer(URL image){
			setLayout(new FlowLayout());
			add(new ImageButton(image));

			eventList = new JList(eventListModel);
			JScrollPane scrollpane = new JScrollPane(eventList);
			scrollpane.setPreferredSize(new Dimension(400,150));
			add(scrollpane);
			
		}


		class ImageButton extends JButton implements MouseListener {

			ImageButton(URL image){				
				this.setIcon(new ImageIcon(image));
				this.addMouseListener(this);			
			}

			private void log(MouseEvent e, String event){

				String button = "";
				if (e.getButton() == MouseEvent.BUTTON1){
					button = "left";
				}else if (e.getButton() == MouseEvent.BUTTON3){
					button = "right";
				}

				String eventText = event + " " + button;
				eventListModel.addElement(eventText);
				eventList.ensureIndexIsVisible(eventListModel.getSize()-1);
			}


			@Override
			public void mouseClicked(MouseEvent arg0) {
				log(arg0, "clicked");
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				log(arg0, "entered");
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				log(arg0, "exited");
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
	}




	public static void main(String[] args) {
		simulator.start();

		ScreenRegion s = new DesktopScreenRegion();	

		ScreenRegion dog = s.find(new ImageTarget(Images.Dog));
		ScreenRegion cat = s.find(new ImageTarget(Images.Cat));
		
		mouse.click(dog.getCenter());
		mouse.click(cat.getCenter());

		mouse.rightClick(dog.getCenter());
		mouse.rightClick(cat.getCenter());

		mouse.hover(dog.getCenter());
		mouse.hover(cat.getCenter());
		mouse.hover(dog.getCenter());
		
		mouse.hover(cat.getCenter());		
		mouse.press();		
		mouse.release();
		
		mouse.hover(dog.getCenter());		
		mouse.rightPress();		
		mouse.rightRelease();
	}

}
