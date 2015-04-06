package com.flashvisions.server.rtmp.transcoder.pojo.audio;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruProperty;

public class AudioBitrate extends PassThruProperty implements IAudioBitrate 
{
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
