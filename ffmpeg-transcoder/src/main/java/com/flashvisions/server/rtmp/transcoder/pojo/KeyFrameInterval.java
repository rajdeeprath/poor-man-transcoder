package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;

import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThru;

public class KeyFrameInterval extends PassThru implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2770042615997108385L;
	private int gop;
	private int minimunInterval;
	
	
	public KeyFrameInterval(){
		
	}
	
	public KeyFrameInterval(boolean sameAsSource){
		this.setSameAsSource(sameAsSource);
		this.gop = -1;
		this.minimunInterval = -1;
	}

	public KeyFrameInterval(int gop, int minimunInterval){
		this.gop = gop;
		this.minimunInterval = minimunInterval;
	}
	
	public int getMinimunInterval() 
	{
		return minimunInterval;
	}
	
	public void setMinimunInterval(int minimunInterval) 
	{
		this.minimunInterval = minimunInterval;
	}

	public int getGop() 
	{
		return gop;
	}

	public void setGop(int gop) 
	{
		this.gop = gop;
	}

}
