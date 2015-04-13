package com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.impl;

import com.flashvisions.server.rtmp.transcoder.interfaces.IMedia;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.interfaces.InterpretStrategy;

public class DefaultInterpretStrategy implements InterpretStrategy {

	@Override
	public String interpret(IMedia media) {
		// TODO Auto-generated method stub
		return media.getSourcePath();
	}

}
