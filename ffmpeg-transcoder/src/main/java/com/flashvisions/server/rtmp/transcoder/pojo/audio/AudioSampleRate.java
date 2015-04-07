package com.flashvisions.server.rtmp.transcoder.pojo.audio;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruObject;

public class AudioSampleRate extends PassThruObject implements IAudioSampleRate
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
