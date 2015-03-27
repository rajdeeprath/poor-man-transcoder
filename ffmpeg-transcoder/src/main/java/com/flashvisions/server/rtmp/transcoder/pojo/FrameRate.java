package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThru;

public class FrameRate extends PassThru {
	
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
