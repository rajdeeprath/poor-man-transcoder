package com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.impl;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.interfaces.InterpretStrategy;

public class DefaultInterpretStrategy implements InterpretStrategy {

	@Override
	public String interpret(ITranscoderResource media) {
		// TODO Auto-generated method stub
		return media.getSourcePath();
	}

}
