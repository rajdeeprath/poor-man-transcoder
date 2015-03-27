package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThru;

public class AudioSampleRate extends PassThru 
{
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
