package com.flashvisions.server.rtmp.transcoder.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BootstrapComplete implements Command {

	private static Logger logger = LoggerFactory.getLogger(BootstrapComplete.class);
	
	@Override
	public boolean execute(Context context) throws Exception {
		// TODO Auto-generated method stub
		
		logger.info("Bootstrap complete");
		logger.info("Transcoder ready");
		
		return false;
	}

}
