package com.flashvisions.server.rtmp.transcoder.pojo.video;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruObject;

public class FrameRate extends PassThruObject implements IFrameRate {
	
	private static final String key = "-r"; 
	
	@NotNull
	@Range(min = 1, max = 60, message = "")
	private Object value;

	public FrameRate(){
	}
	
	public FrameRate(IFrameRate object){
		this.setSameAsSource(object.getSameAsSource());
		this.value = object.getValue();
	}
	
	public FrameRate(Object framerate){
		this.value = framerate;
	}
	
	public FrameRate(boolean sameAsSource){
		this.setSameAsSource(sameAsSource);
		this.value = 0;
	}


	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return FrameRate.key;
	}

	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		// NO OP
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		this.value = value;
	}

	@Override
	public IParameter clone() {
		// TODO Auto-generated method stub
		return new FrameRate(this.value);
	}
}
