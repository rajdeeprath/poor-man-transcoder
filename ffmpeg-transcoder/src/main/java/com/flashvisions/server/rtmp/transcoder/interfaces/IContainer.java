package com.flashvisions.server.rtmp.transcoder.interfaces;

import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.ContainerType;

public interface IContainer {
	public ContainerType getType();
	public void setType(ContainerType type);
}
