package com.flashvisions.server.rtmp.transcoder.pojo.video;

import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameSize;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruProperty;

public class FrameSize extends PassThruProperty implements IFrameSize  {
	
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
