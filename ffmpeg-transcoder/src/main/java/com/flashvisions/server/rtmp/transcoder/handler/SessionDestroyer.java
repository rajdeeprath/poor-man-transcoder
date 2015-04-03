package com.flashvisions.server.rtmp.transcoder.handler;

import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionDestroyer extends ShutdownHookProcessDestroyer {

	private static Logger logger = LoggerFactory.getLogger(SessionDestroyer.class);
	
	@Override
	public boolean add(Process process) {
		// TODO Auto-generated method stub
		logger.info("adding process " + process.toString());
		return super.add(process);
	}

	@Override
	public boolean isAddedAsShutdownHook() {
		// TODO Auto-generated method stub
		return super.isAddedAsShutdownHook();
	}

	@Override
	public boolean remove(Process process) {
		// TODO Auto-generated method stub
		logger.info("removing process " + process.toString());
		return super.remove(process);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return super.size();
	}

}
