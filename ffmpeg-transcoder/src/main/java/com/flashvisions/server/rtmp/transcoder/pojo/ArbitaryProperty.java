package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.interfaces.IArbitaryProperty;

public class ArbitaryProperty implements IArbitaryProperty {

	private String key;
	private String value;
	
	public ArbitaryProperty() {
		
	}
	
	public ArbitaryProperty(String key, String value) {
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
	
	
}
