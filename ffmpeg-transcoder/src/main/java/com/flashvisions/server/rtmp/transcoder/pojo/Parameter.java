package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;

public class Parameter implements IParameter {

	private String key;
	private Object value;
	
	public Parameter() {
		
	}
	
	public Parameter(String key, Object value) {
		this.key = key; this.value = value;
	}
	
	
	@Override
	public String getKey() 
	{
		return key;
	}
	
	@Override
	public void setKey(String key) 
	{
		this.key = key;
	}

	@Override
	public Object getValue()
	{
		return value;
	}

	@Override
	public void setValue(Object value) 
	{
		this.value = value;
	}
	
	@Override
	public IParameter clone() {
		// TODO Auto-generated method stub
		return new Parameter(this.key, this.value);
	}
}
