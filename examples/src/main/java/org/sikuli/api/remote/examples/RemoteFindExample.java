package org.sikuli.api.remote.examples;


import java.net.URL;
import java.util.List;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.examples.Images;
import org.sikuli.api.examples.ScreenSimulator;
import org.sikuli.api.remote.Remote;
import org.sikuli.api.remote.SikuliRemote;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.visual.ScreenPainter;

public class RemoteFindExample {

	public static void main(String[] args) throws Exception {
	
		int port = 5000;	
		String host = "localhost";
							
		URL serverUrl = new URL("http://" + host + ":" + port + "/sikuli");
		

		Remote remote = new SikuliRemote(serverUrl);
		
		ScreenRegion s = remote.getScreenRegion();

		//		URL	imageUrl = new URL("http://code.google.com/images/code_logo.gif");
		//ScreenRegion r = s.find(new ImageTarget(imageUrl));
		
//		URL imageUrl = new URL("https://dl.dropbox.com/u/5104407/j.png");
//		List<ScreenRegion> rs = s.findAll(new ImageTarget(imageUrl));

		URL	imageUrl = new URL("http://code.google.com/images/code_logo.gif");
		ScreenRegion r = s.wait(new ImageTarget(imageUrl),10000);

		//ScreenRegion local = s.getRelativeScreenRegion(, yoffset, width, height)
		
//		ScreenPainter p = new ScreenPainter();
//		for (ScreenRegion r : rs){
//			p.box(r, 3000);
//		}
//		System.out.println(rs);
//		Mouse mouse = remote.getMouse();			
//		mouse.click(r.getCenter());
		

		
	}
}
