package com.flashvisions.server.rtmp.transcoder.handler;

import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.ExecuteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionResultHandler extends DefaultExecuteResultHandler {

	private static Logger logger = LoggerFactory.getLogger(SessionResultHandler.class);
	
	@Override
	public ExecuteException getException() {
		// TODO Auto-generated method stub
		return super.getException();
	}

	@Override
	public int getExitValue() {
		// TODO Auto-generated method stub
		return super.getExitValue();
	}

	@Override
	public boolean hasResult() {
		// TODO Auto-generated method stub
		return super.hasResult();
	}

	@Override
	public void onProcessComplete(int exitValue) {
		// TODO Auto-generated method stub
		logger.info("Process complete exitValue : " + exitValue);
		super.onProcessComplete(exitValue);
	}

	@Override
	public void onProcessFailed(ExecuteException e) {
		// TODO Auto-generated method stub
		logger.info("Process failed exitValue : " + e.getMessage());
		super.onProcessFailed(e);
	}

}
