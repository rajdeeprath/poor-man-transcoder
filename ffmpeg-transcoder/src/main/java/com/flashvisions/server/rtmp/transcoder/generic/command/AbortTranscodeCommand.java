package com.flashvisions.server.rtmp.transcoder.generic.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;

import com.flashvisions.server.rtmp.transcoder.context.TranscoderContext;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.pool.TranscodeSessionPool;

public class AbortTranscodeCommand implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		// TODO Auto-generated method stub

		
		return false;
	}

}
