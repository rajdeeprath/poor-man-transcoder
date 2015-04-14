package com.flashvisions.server.rtmp.transcoder.interfaces;

import com.flashvisions.server.rtmp.transcoder.pojo.Session.Event;


public interface ISession extends IDisposable {

	public long getId();
	
	public ITranscoderResource getInputSource();	
	public ITranscode getTranscodeConfig();
	
	public void registerObserver(ISessionObserver observer);
	public void removeObserver(ISessionObserver observer);
	public void notifyObservers(Event event, Object data);
	
	public void start();
	public boolean stop();
	public boolean isRunning();
}