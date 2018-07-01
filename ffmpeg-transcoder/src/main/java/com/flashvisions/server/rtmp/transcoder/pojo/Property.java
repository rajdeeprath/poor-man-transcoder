package com.flashvisions.server.rtmp.transcoder.pojo;


import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;

public class Property implements IProperty {

	private String data;
	
	public Property(String data) {
		this.data = data;
	}
	
	public Property() {
	}

	@Override
	public String getData() {
		return data;
	}

	@Override
	public void setData(String data) {
		this.data = data;
	}

	@Override
	public IProperty clone() {
		// TODO Auto-generated method stub
		return new Property(this.data);
	}
	
	
}
