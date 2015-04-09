package com.flashvisions.server.rtmp.transcoder.pojo;


import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;

public class Property implements IProperty {

	private String data;
	
	public Property(String data) {
		this.data = data;
	}
	
	public Property() {
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public IProperty clone() {
		// TODO Auto-generated method stub
		return (IProperty) new Property(this.data);
	}
	
	
}
