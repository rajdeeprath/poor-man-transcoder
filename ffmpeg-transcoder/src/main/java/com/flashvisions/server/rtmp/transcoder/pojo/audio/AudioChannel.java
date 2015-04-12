package com.flashvisions.server.rtmp.transcoder.pojo.audio;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioChannel;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruObject;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.AudioChannelType;

public class AudioChannel extends PassThruObject implements IAudioChannel  {
	
	private static final String key = "-ac";
	private Object value;

	
	public AudioChannel(IAudioChannel object)
	{
		this.setSameAsSource(object.getSameAsSource());
		this.value = object.getValue();
	}
	
	public AudioChannel(Object type) 
	{
		this.value = ((String)type).toUpperCase();
	}
	
	public AudioChannel(boolean sameAsSource)
	{
		this.setSameAsSource(sameAsSource);
		this.value = null;
	}
	

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return AudioChannel.key;
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
		this.value = ((String)value).toUpperCase();
	}

	@Override
	public IParameter clone() {
		// TODO Auto-generated method stub
		return new AudioChannel(this.getValue());
	}

	@Override
	public Integer getChannels() {
		// TODO Auto-generated method stub
		return ((Enum<AudioChannelType>) AudioChannelType.valueOf(String.valueOf(this.getValue()))).ordinal()+1;
	}	
}
