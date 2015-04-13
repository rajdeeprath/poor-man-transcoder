package com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.impl;

import com.flashvisions.server.rtmp.transcoder.data.factory.LibRtmpConfigurationFactory;
import com.flashvisions.server.rtmp.transcoder.interfaces.ILibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMedia;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Server;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.interfaces.InterpretStrategy;
import com.flashvisions.server.rtmp.transcoder.system.Globals;

public class RTMPInterpretStrategy implements InterpretStrategy {

	@Override
	public String interpret(IMedia media) {
		ILibRtmpConfig rtmpInterpretor = null;
		// TODO Auto-generated method stub 
		rtmpInterpretor = LibRtmpConfigurationFactory.getLibRtmpConfiguration(Server.valueOf(Globals.getEnv(Globals.Vars.OPERATING_SERVER).toUpperCase()));
		rtmpInterpretor.parseRtmp(media);
		return rtmpInterpretor.toString();
	}

}
