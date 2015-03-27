package com.flashvisions.server.rtmp.transcoder.interfaces;

import com.flashvisions.server.rtmp.transcoder.pojo.Codec;

public interface ICodec {

	public String getName();
	public void setName(String name);
	
	public Codec.Implementation getImplementation();
	public void setImplementation(Codec.Implementation implementation);
}
