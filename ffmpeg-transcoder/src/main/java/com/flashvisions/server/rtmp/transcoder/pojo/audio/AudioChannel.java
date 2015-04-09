package com.flashvisions.server.rtmp.transcoder.pojo.audio;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioChannel;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruObject;

public class AudioChannel extends PassThruObject implements IAudioChannel  {
	
	private int channels = 2;
	private String type;
	
	
	public static enum ChannelType {
        MONO, STEREO
    }
	
	public AudioChannel(IAudioChannel object)
	{
		this.setSameAsSource(object.getSameAsSource());
		this.type = object.getChannelType();
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
	
	public AudioChannel(boolean sameAsSource){
		this.setSameAsSource(sameAsSource);
		this.type = null;
	}
	
	public int getChannels() 
	{
		return channels;
	}

	@Override
	public String getChannelType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public void setChannelType(String type) throws IllegalAccessException {
		// TODO Auto-generated method stub
		try
		{
			switch(ChannelType.valueOf(type.toUpperCase()))
			{
				case MONO:
				channels = 1;
				break;
				
				case STEREO:
				channels = 2;
				break;
			}
		}
		catch(Exception e)
		{
			throw new IllegalAccessException("Invalid channel type");
		}
	}	
}
