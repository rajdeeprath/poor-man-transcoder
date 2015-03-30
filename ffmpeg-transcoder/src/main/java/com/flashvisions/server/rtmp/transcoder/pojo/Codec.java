package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.pojo.base.SourceProperty;

public class Codec extends SourceProperty implements ICodec {
	
	private String name;
	private Implementation implementation;
	
	public static class Implementation{
		
	}
	
	public Codec(String name){
		this.name = name;
	}
	
	public Codec(String name, Implementation implementation){
		this.name = name;
		this.implementation = implementation;
	}
	
	public Codec(String name, String implementation){
		this.name = name;
		// to do validate implementation string
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Implementation getImplementation() {
		return implementation;
	}

	public void setImplementation(Codec.Implementation implementation) {
		this.implementation = implementation;
	}

}
