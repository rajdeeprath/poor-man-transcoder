package com.flashvisions.server.rtmp.transcoder.interfaces;

import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Format;

public interface IContainer {
	public Format getType();
	public void setType(Format type);
}
