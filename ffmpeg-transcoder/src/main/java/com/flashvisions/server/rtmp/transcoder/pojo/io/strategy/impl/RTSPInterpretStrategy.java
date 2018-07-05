package com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.impl;

import com.flashvisions.server.rtmp.transcoder.data.factory.LibRtmpConfigurationFactory;
import com.flashvisions.server.rtmp.transcoder.interfaces.ILibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Server;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.interfaces.InterpretStrategy;
import com.flashvisions.server.rtmp.transcoder.system.Globals;

public class RTSPInterpretStrategy implements InterpretStrategy {

	@Override
	public String interpret(ITranscoderResource resource) {
		// TODO Auto-generated method stub
		return resource.getSourcePath();
	}

}
