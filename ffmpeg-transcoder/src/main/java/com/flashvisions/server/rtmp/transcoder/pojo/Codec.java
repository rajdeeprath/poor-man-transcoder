package com.flashvisions.server.rtmp.transcoder.pojo;

import javax.validation.constraints.NotNull;

import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruObject;

public abstract class Codec extends PassThruObject implements ICodec {
	
	@NotNull
	private Object value;
	
	private boolean enabled = true;
	
	public Codec(){
		
	}
	
	public Codec(ICodec object){
		this.setSameAsSource(object.getSameAsSource());
		this.setEnabled(object.getEnabled());
		this.value = object.getValue();
	}

	public Codec(Object name){
		this.value = name;
	}

	@Override
	public boolean getEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		this.enabled = enabled;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		//
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		this.value = value;
	}

	@Override
	public IParameter clone() {
		// TODO Auto-generated method stub
		return null;
	}
}
