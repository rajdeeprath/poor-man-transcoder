package com.flashvisions.server.rtmp.transcoder.handler;

import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;

public class SessionDestroyer extends ShutdownHookProcessDestroyer {

	private static Logger logger = LoggerFactory.getLogger(SessionDestroyer.class);
	private ISession session;
	
	public SessionDestroyer(ISession session){
		this.setSession(session);
	}
	
	
	@Override
	public boolean add(Process process) {
		// TODO Auto-generated method stub
		logger.info("adding process " + process);
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


	public ISession getSession() {
		return session;
	}


	private void setSession(ISession session) {
		this.session = session;
	}

}
