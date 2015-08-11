package com.flashvisions.server.rtmp.transcoder.pojo.video;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.flashvisions.server.rtmp.transcoder.ffmpeg.Flags;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameSize;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruObject;

public class FrameSize extends PassThruObject implements IFrameSize  {
	
	private static final String key = Flags.VIDEO_FRAMESIZE; 
	
	
	@NotNull
	@Range(min = 1, max = 3840, message = "")
	private Object width = 0;
	
	@NotNull
	@Range(min = 1, max = 2040, message = "")
	private Object height = 0;
	
	@Size(min = 3, max = 9)
	private Object value;
	
	
	public FrameSize(){
		
	}
	
	public FrameSize(IFrameSize object){
		this.setSameAsSource(object.getSameAsSource());
		this.width = object.getWidth();
		this.height = object.getHeight();
	}
	
	public FrameSize(boolean sameAsSource){
		this.setSameAsSource(sameAsSource);
		this.width = 0;
		this.height = 0;
	}
	
	public FrameSize(Object width, Object height){
		this.width = width;
		this.height = height;
	}

	public Object getWidth() 
	{
		// TODO Auto-generated method stub
		return width;
	}

	public void setWidth(Object width) 
	{
		// TODO Auto-generated method stub
		this.width = width;
	}

	public Object getHeight() 
	{
		// TODO Auto-generated method stub
		return height;
	}

	public void setHeight(Object height) 
	{
		// TODO Auto-generated method stub
		this.height = height;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return FrameSize.key;
	}

	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		// NO OP
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return this.width+"x"+this.height;
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		// NO OP
	}

	@Override
	public IParameter clone() {
		// TODO Auto-generated method stub
		return new FrameSize(this.width, this.height);
	}
}
