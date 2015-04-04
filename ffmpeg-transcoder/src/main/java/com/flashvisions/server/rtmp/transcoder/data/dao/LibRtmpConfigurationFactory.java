package com.flashvisions.server.rtmp.transcoder.data.dao;

import com.flashvisions.server.rtmp.transcoder.interfaces.ILibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.server.Red5LibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.server.Server;
import com.flashvisions.server.rtmp.transcoder.server.WowzaLibRtmpConfig;

public class LibRtmpConfigurationFactory {
	
	public static ILibRtmpConfig getLibRtmpConfiguration(Server type)
	{
		switch(type)
		{
			case RED5:
			return new Red5LibRtmpConfig();
				
			case WOWZA:
			return new WowzaLibRtmpConfig();
		}
		return null;
	}
}
