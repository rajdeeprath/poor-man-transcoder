package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;

public class Parameter implements IParameter {

	private String key;
	private String value;
	
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

	public String getValue()
	{
		return value;
	}

	public void setValue(String value) 
	{
		this.value = value;
	}
	
	public IParameter clone() {
		// TODO Auto-generated method stub
		return (IParameter) new Parameter(this.key, this.value);
	}
}
