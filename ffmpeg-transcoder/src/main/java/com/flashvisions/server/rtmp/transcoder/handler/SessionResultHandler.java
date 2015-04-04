package com.flashvisions.server.rtmp.transcoder.handler;

import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;

public class SessionResultHandler extends DefaultExecuteResultHandler {

	private static Logger logger = LoggerFactory.getLogger(SessionResultHandler.class);
	private ExecuteWatchdog watchdog;
	private ISession session;
	
	public SessionResultHandler(ExecuteWatchdog watchdog, ISession session){
		this.setWatchdog(watchdog);
		this.setSession(session);
	}
	
	private void setWatchdog(ExecuteWatchdog watchdog) {
		this.watchdog = watchdog;
	}

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
		super.onProcessFailed(e);
		
		if(watchdog != null && watchdog.killedProcess())
		logger.error("Process timed out");
		else
		logger.error("Process failed : " + e.getMessage());
		
		// destroy parent session
		//this.session.dispose();
		//this.session = null;
	}
	
	public ExecuteWatchdog getWatchdog() {
		return watchdog;
	}

	public ISession getSession() {
		return session;
	}

	public void setSession(ISession session) {
		this.session = session;
	}

}
