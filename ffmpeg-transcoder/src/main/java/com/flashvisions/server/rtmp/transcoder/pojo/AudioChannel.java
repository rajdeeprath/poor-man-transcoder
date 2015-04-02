package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioChannel;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThru;

public class AudioChannel extends PassThru implements IAudioChannel, Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5877868764824497629L;
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
