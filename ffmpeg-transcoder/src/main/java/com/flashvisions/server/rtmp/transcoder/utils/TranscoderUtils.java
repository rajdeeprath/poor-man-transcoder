package com.flashvisions.server.rtmp.transcoder.utils;

import com.flashvisions.server.rtmp.transcoder.system.Server;

public class TranscoderUtils {

	public static boolean isValidMediaServer(String server) {

	    for (Server c : Server.values()) {
	        if (c.name().equals(server.toUpperCase())) {
	            return true;
	        }
	    }

	    return false;
	}
}
