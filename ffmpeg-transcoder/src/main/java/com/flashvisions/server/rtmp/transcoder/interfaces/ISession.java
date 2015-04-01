package com.flashvisions.server.rtmp.transcoder.interfaces;


public interface ISession {

	public int getIdentifier();
	
	public IMediaInput getInputSource();
	public void setInputSource(IMediaInput input);
	
	public ITranscodeConfig getTranscodeConfig();
	public void setTranscodeConfig(ITranscodeConfig config);
	
	public ISessionHandler getHandler();
	public void setHandler(ISessionHandler handler);
	
	public void start();
	public boolean stop();
	public boolean isRunning();
}