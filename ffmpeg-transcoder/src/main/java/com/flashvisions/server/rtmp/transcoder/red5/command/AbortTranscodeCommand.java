package com.flashvisions.server.rtmp.transcoder.red5.command;

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
		TranscoderContext ctx = (TranscoderContext) context;
		TranscodeSessionPool pool =  ctx.getPool();
		IConnection connnection = Red5.getConnectionLocal();
		
		String signature = (String) connnection.getAttribute("TRANSCODERSESSION");
		ISession session = pool.getSession(signature);
		session.stop();
		
		return false;
	}

}
