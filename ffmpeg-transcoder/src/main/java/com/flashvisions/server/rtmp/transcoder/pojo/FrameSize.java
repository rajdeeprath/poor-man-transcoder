package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;

import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameSize;

public class FrameSize extends PassThru implements IFrameSize, Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3073630608867263367L;
	private int width = 0;
	private int height = 0;
	
	public FrameSize(){
		
	}
	
	public FrameSize(boolean sameAsSource){
		this.setSameAsSource(sameAsSource);
		this.width = -1;
		this.height = -1;
	}
	
	public FrameSize(int width, int height){
		this.width = width;
		this.height = height;
	}

	public int getWidth() 
	{
		// TODO Auto-generated method stub
		return width;
	}

	public void setWidth(int width) 
	{
		// TODO Auto-generated method stub
		this.width = width;
	}

	public int getHeight() 
	{
		// TODO Auto-generated method stub
		return height;
	}

	public void setHeight(int height) 
	{
		// TODO Auto-generated method stub
		this.height = height;
	}

}
