package com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.interfaces;

import com.flashvisions.server.rtmp.transcoder.interfaces.IMedia;

public interface InterpretStrategy {

	public String interpret(IMedia media);
}
