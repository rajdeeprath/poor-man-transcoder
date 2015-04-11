package com.flashvisions.server.rtmp.transcoder.data.factory;

import com.flashvisions.server.rtmp.transcoder.interfaces.ILibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.librtmp.Red5LibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.librtmp.WowzaLibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Server;

public class LibRtmpConfigurationFactory {
	
	public static ILibRtmpConfig getLibRtmpConfiguration(Server type)
	{
		switch(type)
		{
			case RED5:
			return new Red5LibRtmpConfig();
				
			case WOWZA:
			return new WowzaLibRtmpConfig();
			
			case FMS:
			break;
		}
		
		return null;
	}
}
