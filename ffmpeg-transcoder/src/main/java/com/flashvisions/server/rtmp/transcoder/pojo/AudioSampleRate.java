package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioSampleRate;

public class AudioSampleRate extends PassThru implements IAudioSampleRate, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8862673192135863714L;
	private int samplerate;
	
	
	public AudioSampleRate(boolean sameAsSource) {
		this.setSameAsSource(sameAsSource);
		this.samplerate = -1;
	}
	
	public AudioSampleRate(int samplerate) {
		this.samplerate = samplerate;
	}

	public int getSamplerate() 
	{
		return samplerate;
	}

	public void setSamplerate(int samplerate) 
	{
		this.samplerate = samplerate;
	}
	
}
