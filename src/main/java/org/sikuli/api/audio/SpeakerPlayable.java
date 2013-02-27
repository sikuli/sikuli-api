package org.sikuli.api.audio;

import java.net.URL;

public class SpeakerPlayable implements Runnable{
	final private Speaker speaker = new DesktopSpeaker();
	final private URL url;
	public SpeakerPlayable(URL url){
		this.url = url;
	}

	public void run(){
		speaker.play(url);
	}
}