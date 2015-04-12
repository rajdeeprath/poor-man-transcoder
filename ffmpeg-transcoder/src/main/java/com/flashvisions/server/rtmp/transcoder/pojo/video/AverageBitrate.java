package com.flashvisions.server.rtmp.transcoder.pojo.video;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAverageBitrate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.pojo.Parameter;

public class AverageBitrate extends Parameter implements IAverageBitrate {

	private static final String key = "-b:v"; 
	
	@NotNull
	@Range(min = 0, max = 5000, message = "{com.flashvisions.server.rtmp.transcoder.validation.video.bitrate.invalid.average}")
	private Object value;
	
	
	public AverageBitrate(){
		
	}
	
	public AverageBitrate(Object value){
		this.value = value;
	}
	
	public AverageBitrate(AverageBitrate object){
		this.value = object.value;
	}
	
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return AverageBitrate.key;
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
		return new AverageBitrate(this.value);
	}


}
