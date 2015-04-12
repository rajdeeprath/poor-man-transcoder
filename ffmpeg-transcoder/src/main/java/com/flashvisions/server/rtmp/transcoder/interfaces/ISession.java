package com.flashvisions.server.rtmp.transcoder.interfaces;


public interface ISession extends IDisposable {

	public long getId();
	
	public IMediaInput getInputSource();	
	public ITranscode getTranscodeConfig();
	
	public void start();
	public boolean stop();
	public boolean isRunning();
}