package com.flashvisions.server.rtmp.transcoder.interfaces;


public interface ISession extends IDisposable {

	public int getIdentifier();
	
	public Process getProcess();
	
	/* Important!!! not to be used by user */
	public void setProcess(Process proc);
	
	public IMediaInput getInputSource();
	public void setInputSource(IMediaInput input);
	
	public ITranscodeConfig getTranscodeConfig();
	public void setTranscodeConfig(ITranscodeConfig config);
	
	public void start();
	public boolean stop();
	public boolean isRunning();
}