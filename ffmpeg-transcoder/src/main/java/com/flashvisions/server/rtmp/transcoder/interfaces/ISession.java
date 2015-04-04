package com.flashvisions.server.rtmp.transcoder.interfaces;


public interface ISession extends IDisposable {

	public int getIdentifier();
	
	public Process getProcess();
	
	/* Important!!! not to be used by user */
	public void setProcess(Process proc);
	
	public IMediaInput getInputSource();	
	public ITranscodeConfig getTranscodeConfig();
	
	public void start();
	public boolean stop();
	public boolean isRunning();
}