package com.flashvisions.server.rtmp.transcoder.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.context.TranscoderContext;
import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;
import com.flashvisions.server.rtmp.transcoder.pool.TranscodeSessionPool;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Server;


public class InitializeVariables implements Command {

	private static Logger logger = LoggerFactory.getLogger(InitializeVariables.class);
	
	@Override
	public boolean execute(Context context) throws Exception {
		// TODO Auto-generated method stub
		
		logger.info("Initialize Variables");
		
		try
		{
			TranscoderContext ctx = (TranscoderContext) context;
			TranscodeSessionPool pool = new TranscodeSessionPool(Server.valueOf(ctx.getOperatingMediaServer().toUpperCase()));
			ctx.setPool(pool);
		}
		catch(Exception e)
		{
			logger.error("Transcoder initialization failed " + e.getMessage());
			throw new TranscoderException(e);
		}
		
		return false;
	}

}
