package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IAudioSampleRate extends IPassThru, Cloneable {

	public int getSamplerate();
	public void setSamplerate(int samplerate);
}
