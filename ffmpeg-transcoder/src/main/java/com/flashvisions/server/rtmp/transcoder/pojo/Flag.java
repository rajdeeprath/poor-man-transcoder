package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;

import com.flashvisions.server.rtmp.transcoder.interfaces.IFlag;

public class Flag implements Serializable, IFlag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7482351439593873349L;
	private String data;
	
	public Flag(String data) {
		this.data = data;
	}
	
	public Flag() {
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
