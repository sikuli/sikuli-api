package org.sikuli.api.audio;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.Player;

public class DesktopSpeaker implements Speaker{

	public static void main(String[] args) throws Exception {
		DesktopSpeaker sp = new DesktopSpeaker();
		URL url = new URL("file", null, "clickhere.wav");
		sp.play(url);
		sp.play(url);
	}

	public void play1(URL url){
		AudioClip clip = Applet.newAudioClip(url);
		clip.play();
	}

	@Override
	public void play(URL url)  {

		try {

			final Player player;
			final Object o = new Object();
			player = Manager.createRealizedPlayer(url);		
			player.addControllerListener(new ControllerListener(){
				@Override
				public void controllerUpdate(ControllerEvent e) {
					if (e.getClass() == javax.media.EndOfMediaEvent.class){
						synchronized(o){
							o.notify();
						}				
						player.stop();
						player.close();
					}
				}            	
			});

			player.start();
			synchronized(o){
				o.wait();
			}
		}catch(Exception e){
			
		}
	}
}




