package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;

import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;

public class Property implements Serializable, IProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7482351439593873349L;
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
}
