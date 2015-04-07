package com.flashvisions.server.rtmp.transcoder.pojo.audio;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioChannel;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruObject;

public class AudioChannel extends PassThruObject implements IAudioChannel  {
	
	private int channels = 2;
	private ChannelType type;
	
	
	public static enum ChannelType {
        MONO, STEREO
    }
	
	public AudioChannel(String type) throws IllegalAccessException{
		
		switch(ChannelType.valueOf(type.toUpperCase()))
		{
			case MONO:
			channels = 1;
			break;
			
			case STEREO:
			channels = 2;
			break;
			
			default:
			throw new IllegalAccessException("Invalid channel type");
		}
	}
	
	public AudioChannel(ChannelType type){
		this.type = type;
	}
	
	public AudioChannel(boolean sameAsSource){
		this.setSameAsSource(sameAsSource);
		this.type = null;
	}
	
	public int getChannels() 
	{
		return channels;
	}

	public ChannelType getChannelType() 
	{
		return type;
	}

	public void setChannelType(ChannelType type) throws IllegalAccessException 
	{
		switch(type)
		{
			case MONO:
			channels = 1;
			break;
			
			case STEREO:
			channels = 2;
			break;
			
			default:
			throw new IllegalAccessException("Invalid channel type");
		}
		
		this.type = type;
	}

}
