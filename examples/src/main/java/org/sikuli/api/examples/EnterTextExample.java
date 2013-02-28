package org.sikuli.api.examples;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;

public class EnterTextExample {
	
	static Mouse mouse = new DesktopMouse();
	static Keyboard keyboard = new DesktopKeyboard();
	static Canvas canvas = new DesktopCanvas();
	
	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showComponent(new TextFieldPanel());
			wait(6);
			close();
		}
	};
	
	static class TextFieldPanel extends JPanel {
	
		TextFieldPanel(){
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JPanel p = new JPanel(new FlowLayout());
			add(p);
			p.add(new JLabel(new ImageIcon(Images.PersonIcon)));
			p.add(new JTextField(30));
			
			p = new JPanel(new FlowLayout());
			add(p);
			p.add(new JLabel(new ImageIcon(Images.ThumbIcon)));
			p.add(new JTextField(30));
			
			p = new JPanel(new FlowLayout());
			add(p);
			p.add(new JLabel(new ImageIcon(Images.TwitterBirdIcon)));
			p.add(new JTextField(30));
			
		
		}
	}

	public static void main(String[] args) {
		simulator.start();

		Rectangle b = simulator.getBounds(); 
		ScreenRegion s = new DesktopScreenRegion(b.x,b.y,b.width,b.height);
		
		ScreenRegion icon1 = s.find(new ImageTarget(Images.PersonIcon));;
		ScreenRegion icon2 = s.find(new ImageTarget(Images.ThumbIcon));;
		ScreenRegion icon3 = s.find(new ImageTarget(Images.TwitterBirdIcon));;

		canvas.addBox(icon1);
		canvas.addBox(icon2);
		canvas.addBox(icon3);
		canvas.display(1);
		
		mouse.click(Relative.to(icon1).center().right(30).getScreenLocation());
		keyboard.type("Hello world!!");
		
		mouse.click(Relative.to(icon2).center().right(30).getScreenLocation());
		keyboard.paste("Hello world pasted!!");
		
		mouse.click(Relative.to(icon3).center().right(30).getScreenLocation());
		keyboard.paste("Asian text: 中文 한국 ");
	}
}