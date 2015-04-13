package com.flashvisions.server.rtmp.transcoder.interfaces;

import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.interfaces.InterpretStrategy;

public interface ITranscoderResource extends IMedia {
	public String describe();
	public InterpretStrategy getStrategy();
	public void setStrategy(InterpretStrategy strategy);
}
