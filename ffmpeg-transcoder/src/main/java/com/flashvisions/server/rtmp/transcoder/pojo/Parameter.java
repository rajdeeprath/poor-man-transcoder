package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;

public class Parameter implements IParameter {

	private String key;
	private Object value;
	
	public Parameter() {
		
	}
	
	public Parameter(String key, String value) {
		this.key = key; this.value = value;
	}
	
	
	public String getKey() 
	{
		return key;
	}
	
	public void setKey(String key) 
	{
		this.key = key;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value) 
	{
		this.value = value;
	}
	
}
