package org.sikuli.api.audio;

import java.net.URL;
/**
 * The Speaker interface provides definitions for the objects that represent a Speaker that plays
 * audio clips.
 *
 */
public interface Speaker {
	/**
	 * Plays audio clip (in .wav format) at the specified URL.
	 * This is a blocking method that plays the audio clip specified by the URL argument.
	 * 
	 * @param url an absolute URL giving the location of the audio clip.
	 */
	public void play(URL url);
}
