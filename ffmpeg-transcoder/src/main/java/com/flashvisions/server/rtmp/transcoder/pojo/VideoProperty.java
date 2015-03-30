package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;

public class VideoProperty extends ArbitaryProperty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1523780049221233192L;

	public VideoProperty() {
		super();
	}
	
	public VideoProperty(String key, String value) {
		super(key, value);
	}
}
