package org.sikuli.api.remote.examples;

import java.awt.Color;
import java.net.URL;
import java.util.List;

import org.sikuli.api.ImageTarget;
import org.sikuli.api.Relative;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.examples.Images;
import org.sikuli.api.examples.ScreenSimulator;
import org.sikuli.api.remote.Remote;
import org.sikuli.api.remote.SikuliRemote;
import org.sikuli.api.remote.server.SikuliRemoteServer;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.visual.ScreenRegionCanvas;

public class LocalhostExample {

	static ScreenSimulator simulator = new ScreenSimulator(){
		public void run(){
			showImage(Images.OSXDockPreferences);
			wait(5000);
			//close();
		}
	};

	public static void main(String[] args) throws Exception {
		simulator.start();

		int port = 5000;		
				
		SikuliRemoteServer server = new SikuliRemoteServer(port);
		server.startup();
			
		URL serverUrl = new URL("http://localhost:" + port + "/sikuli");
//		URL	imageUrl = new URL("http://dl.dropbox.com/u/5104407/google.png");
		URL	labelImageUrl = new URL("http://dl.dropbox.com/u/5104407/mag.png");
		URL	thumbImageUrl = new URL("http://dl.dropbox.com/u/5104407/thumb.png");
		URL	checkboxImageUrl = new URL("http://dl.dropbox.com/u/5104407/checkbox.png");
		

		Remote remote = new SikuliRemote(serverUrl);

		ScreenRegion s = remote.getScreenRegion();
		ScreenRegion smallRegion = s.getRelativeScreenRegion(300,300,1000,400); 
		
		ScreenRegion labelRegion = smallRegion.find(new ImageTarget(labelImageUrl));
		ScreenRegionCanvas canvas = new ScreenRegionCanvas(s);
		canvas.display(10);
		
		
		ScreenRegion rightOfLabelRegion = Relative.to(labelRegion).right(300).getScreenRegion();
		ScreenRegion thumb = rightOfLabelRegion.find(new ImageTarget(thumbImageUrl));
		
		List<ScreenRegion> checkboxes = smallRegion.findAll(new ImageTarget(checkboxImageUrl));
		
		canvas.addBox(labelRegion);
		canvas.addBox(rightOfLabelRegion).withLineColor(Color.green);
		canvas.addBox(thumb).withLineColor(Color.blue).withLineWidth(5);		
		
		int no = 1;
		for (ScreenRegion c : checkboxes){			
			String labelText = String.format("(%d):%1.3f", no, labelRegion.getScore());
			canvas.addBox(c).withLineColor(Color.magenta).withLineWidth(1);
			canvas.addLabel(Relative.to(c).topLeft().left(70).getScreenLocation(), labelText).withColor(Color.blue);
			no++;
		}		
		canvas.display(10);
		
//		Mouse mouse = remote.getMouse();			
//		mouse.click(r.getCenter());
		
	}
}
