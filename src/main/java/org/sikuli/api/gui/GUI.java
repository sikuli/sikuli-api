package org.sikuli.api.gui;

import java.io.File;
import java.net.URL;

import org.sikuli.api.ScreenRegion;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;

public class GUI extends Component {
	
	static public GUI createOSXLion(ScreenRegion screenRegion){
		return new GUI(screenRegion, "/org/sikuli/api/gui/lion"); 
	}

	private String stylePath;
	
	public GUI(ScreenRegion screenRegion, String stylePath){
		super(null, screenRegion);
		this.stylePath = stylePath;
	}
	
	public GUI(String stylePath){
		super(null, new ScreenRegion());
		this.stylePath = stylePath;
	}
			
	public String getStylePath(){
		return stylePath;
	}
		
	public RadioButton radioButton(String string) {
		return new RadioButton(this, string);
	}
	
	public CheckBox checkBox(String string) {
		return new CheckBox(this, string);
	}

	public ImageIcon imageIcon(File imageFile) {
		return new ImageIcon(this, imageFile);
	}
	
	public ImageIcon imageIcon(URL imageurl) {
		return new ImageIcon(this, imageurl);
	}

	public ComboBox comboBox(String string){
		return new ComboBox(this, string);
	}
	
	public Button button(String string) {				
		return new Button(this, string);
	}
	
	public TextField textField(String string) {
		return new TextField(this, string);
	}
	
	public Label label(String string) {
		return new Label(this, string);
	}
	
	public GUI region(int x, int y, int width, int height){
		return new GUI(new ScreenRegion(getScreenRegion(), x, y, width, height), "/org/sikuli/api/gui/lion");
	}
	
	@Override
	public Mouse getMouse(){
		return new Mouse();
	}
	
	@Override
	public Keyboard getKeyboard(){
		return new Keyboard();
	}


}