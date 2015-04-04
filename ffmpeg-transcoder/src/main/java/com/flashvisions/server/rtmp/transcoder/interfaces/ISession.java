package com.flashvisions.server.rtmp.transcoder.interfaces;


public interface ISession extends IDisposable {

	public int getIdentifier();
	
	public IMediaInput getInputSource();
	public void setInputSource(IMediaInput input);
	
	public ITranscodeConfig getTranscodeConfig();
	public void setTranscodeConfig(ITranscodeConfig config);
	
	public void start();
	public boolean stop();
	public boolean isRunning();
}