package com.flashvisions.server.rtmp.transcoder.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISessionHandler;

public class SessionHandler implements ISessionHandler {

	private static Logger logger = LoggerFactory.getLogger(SessionHandler.class);
	
	@Override
	public void onReady(ISession session) {
		// TODO Auto-generated method stub
		logger.info("Session ready");
	}

	@Override
	public void onStart(ISession session, Process proc) {
		// TODO Auto-generated method stub
		logger.info("Session started");
	}

	@Override
	public void onStop(ISession session, Process proc) {
		// TODO Auto-generated method stub
		logger.info("Session stopped");
	}

	@Override
	public void onError(ISession session, Process proc) {
		// TODO Auto-generated method stub
		logger.info("Session error");
	}

	@Override
	public void onProgress(ISession session, Process proc) {
		// TODO Auto-generated method stub
		logger.info("Session in progress");
	}

}
