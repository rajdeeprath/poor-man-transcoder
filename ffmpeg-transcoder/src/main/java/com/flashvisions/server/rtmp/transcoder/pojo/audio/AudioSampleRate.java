package com.flashvisions.server.rtmp.transcoder.pojo.audio;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruObject;

public class AudioSampleRate extends PassThruObject implements IAudioSampleRate
{
	private static final String key = "-ar";
	private Object value;
	
	/*************** Copy constructor ************/
	public AudioSampleRate(IAudioSampleRate object) {
		this.setSameAsSource(object.getSameAsSource());
		this.value = object.getValue();
	}

	public AudioSampleRate(boolean sameAsSource, Object samplerate) {
		this.setSameAsSource(sameAsSource);
		this.value = samplerate;
	}

	public AudioSampleRate(boolean sameAsSource) {
		this.setSameAsSource(sameAsSource);
		this.value = -10;
	}
	
	public AudioSampleRate(Object samplerate) {
		this.value = samplerate;
	}


	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return AudioSampleRate.key;
	}

	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		// NO OP
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		this.value = value;
	}

	@Override
	public IParameter clone() {
		// TODO Auto-generated method stub
		return null;
	}
}	
