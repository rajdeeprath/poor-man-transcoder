package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;


public class VideoCodec extends Codec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1644227612003274254L;

	public VideoCodec(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public VideoCodec(String name, Codec.Implementation implementation){
		super(name, implementation);
	}
	
	public VideoCodec(String name, String implementation){
		super(name, implementation);
	}

}
