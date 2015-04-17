package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.pojo.Session.Event;


public interface ISession extends IDisposable, TranscodeSessionProcessCallback, TranscodeSessionResultCallback, TranscodeSessionDataCallback {

	public long getId();
	
	public ITranscoderResource getInputSource();	
	public ArrayList<ITranscoderResource> getOutputs();
	public ITranscode getTranscodeConfig();
	
	public void registerObserver(ISessionObserver observer);
	public void removeObserver(ISessionObserver observer);
	public void notifyObservers(Event event, Object data);
	
	public void start();
	public boolean stop();
	public boolean isRunning();
}