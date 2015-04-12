package com.flashvisions.server.rtmp.transcoder.pojo.video;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.pojo.Parameter;

public class MaximumBitrate extends Parameter implements IParameter {

	private static final String key = "-maxrate"; 
	
	@NotNull
	@Range(min = 0, max = 5000, message = "{com.flashvisions.server.rtmp.transcoder.validation.video.bitrate.invalid.maximum}")
	private Object value;
	
	
	public MaximumBitrate(){
		
	}
	
	public MaximumBitrate(Object value){
		this.value = value;
	}
	
	public MaximumBitrate(MaximumBitrate object){
		this.value = object.value;
	}
	
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return MaximumBitrate.key;
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
		return new MaximumBitrate(this.value);
	}


}
