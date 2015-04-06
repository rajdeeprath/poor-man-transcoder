package com.flashvisions.server.rtmp.transcoder.interfaces;


public interface ISession extends IDisposable {

	public long getId();
	public String getSignature();
	
	public Process getProcess();
	/* Important!!! not to be used by user */
	public void setProcess(Process proc);
	
	public IMediaInput getInputSource();	
	public ITranscode getTranscodeConfig();
	
	public void start();
	public boolean stop();
	public boolean isRunning();
}