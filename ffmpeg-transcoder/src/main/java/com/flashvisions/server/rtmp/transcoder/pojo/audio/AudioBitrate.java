package com.flashvisions.server.rtmp.transcoder.pojo.audio;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruObject;

public class AudioBitrate extends PassThruObject implements IAudioBitrate 
{
	private int bitrate;
	
	/************ Copy constructor *********/
	public AudioBitrate(IAudioBitrate object){
		this.setSameAsSource(object.getSameAsSource());
		this.bitrate = object.getBitrate();
	}
	
	public AudioBitrate(boolean sameAsSource){
		this.setSameAsSource(sameAsSource);
		this.bitrate = -1;
	}
	
	public AudioBitrate(int bitrate){
		this.bitrate = bitrate;
	}
	
	public AudioBitrate(){
		
	}

	public int getBitrate() 
	{
		return bitrate;
	}

	public void setBitrate(int bitrate) 
	{
		this.bitrate = bitrate;
	}	
}
