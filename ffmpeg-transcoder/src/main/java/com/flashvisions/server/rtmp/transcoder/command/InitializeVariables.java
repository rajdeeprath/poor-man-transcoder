package com.flashvisions.server.rtmp.transcoder.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;


public class InitializeVariables implements Command {

	private static Logger logger = LoggerFactory.getLogger(InitializeVariables.class);
	
	@Override
	public boolean execute(Context context) throws Exception {
		
		try
		{
			logger.info("Initialize Variables");
		}
		catch(Exception e)
		{
			logger.error("Transcoder initialization failed " + e.getMessage());
			throw new TranscoderException(e);
		}
		
		return false;
	}
}
