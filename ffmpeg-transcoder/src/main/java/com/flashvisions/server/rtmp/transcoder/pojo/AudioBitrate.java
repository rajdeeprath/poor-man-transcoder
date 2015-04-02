package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThru;

public class AudioBitrate extends PassThru implements IAudioBitrate, Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8520782797618220444L;
	private int bitrate;
	
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
