package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;

public class AudioProperty extends ArbitaryProperty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6851221370254723856L;

	public AudioProperty() {
		super();
	}
	
	public AudioProperty(String key, String value) {
		super(key, value);
	}
}
