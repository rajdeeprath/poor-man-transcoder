package com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.interfaces;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;

public interface InterpretStrategy {

	public String interpret(ITranscoderResource media);
}
