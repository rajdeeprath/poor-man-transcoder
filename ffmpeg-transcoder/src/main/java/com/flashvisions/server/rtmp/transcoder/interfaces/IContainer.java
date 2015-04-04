package com.flashvisions.server.rtmp.transcoder.interfaces;

import com.flashvisions.server.rtmp.transcoder.pojo.Container.Type;

public interface IContainer {

	public String getName();
	public void setName(String name);
	public Type getScheme();
}
