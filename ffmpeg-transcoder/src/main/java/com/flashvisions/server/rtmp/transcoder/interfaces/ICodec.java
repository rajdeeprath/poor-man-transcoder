package com.flashvisions.server.rtmp.transcoder.interfaces;

import com.flashvisions.server.rtmp.transcoder.pojo.Codec;

public interface ICodec extends IPassThru {

	public String getName();
	
	public boolean getEnabled();
	public void setEnabled(boolean enabled);
	
	public Codec.Implementation getImplementation();
	public void setImplementation(Codec.Implementation implementation);
}
