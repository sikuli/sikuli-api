package org.sikuli.api.audio;

import java.net.URL;
/**
 * A SpeakerPlayable is used to play an audio clip by a thread.
 *
 */
public class SpeakerPlayable implements Runnable{
	final private Speaker speaker = new DesktopSpeaker();
	final private URL url;
	/**
	 * Constructs a SpeakerPlayable object at an absolute URL giving the location of the audio clip.
	 * @param url an absolute URL giving the location of the audio clip.
	 */
	public SpeakerPlayable(URL url){
		this.url = url;
	}

	public void run(){
		speaker.play(url);
	}
}