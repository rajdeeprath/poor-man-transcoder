package com.flashvisions.server.rtmp.transcoder.pojo.video;

import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameRate;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruProperty;

public class FrameRate extends PassThruProperty implements IFrameRate {
	
	private int framerate;

	public FrameRate(){
	}
	
	public FrameRate(int framerate){
		this.framerate = framerate;
	}
	
	public FrameRate(boolean sameAsSource){
		this.setSameAsSource(sameAsSource);
		this.framerate = -1;
	}

	public int getFramerate() 
	{
		return framerate;
	}

	public void setFramerate(int framerate) 
	{
		this.framerate = framerate;
	} 

}
