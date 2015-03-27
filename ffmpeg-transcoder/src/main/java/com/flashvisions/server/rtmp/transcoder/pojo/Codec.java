package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.pojo.base.SourceProperty;

public class Codec extends SourceProperty implements ICodec {
	
	private String name;
	private Implementation implementation;
	
	public static class Implementation{
		
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
