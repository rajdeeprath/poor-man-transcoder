package com.flashvisions.server.rtmp.transcoder.pojo.video;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.pojo.Parameter;

public class MinKeyframeInterval extends Parameter implements IParameter {

	private static final String key = "-keyint_min"; 
	
	@NotNull
    @Range(min = 0, max = 120)
	private Object value;
	
	public MinKeyframeInterval(){
		
	}
	
	public MinKeyframeInterval(MinKeyframeInterval object){
		this.value = object.getValue();
	}
	
	public MinKeyframeInterval(Object value){
		this.value = value;
	}
	
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return MinKeyframeInterval.key;
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
		return new MinKeyframeInterval(this.value);
	}

}
