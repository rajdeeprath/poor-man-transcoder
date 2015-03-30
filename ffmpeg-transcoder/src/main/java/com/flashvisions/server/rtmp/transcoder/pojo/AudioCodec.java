package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;

public class AudioCodec extends Codec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6184248445560383627L;

	public AudioCodec(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public AudioCodec(String name, Codec.Implementation implementation){
		super(name, implementation);
	}
	
	public AudioCodec(String name, String implementation){
		super(name, implementation);
	}
}
