package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IAudioSampleRate extends IPassThru {

	public int getSamplerate();
	public void setSamplerate(int samplerate);
}
