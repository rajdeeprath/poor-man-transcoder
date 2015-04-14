package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface ISessionObserver {
	public void onSessionStart(Object data);
	public void onSessionComplete(Object data);
	public void onSessionFailed(Object data);
	public void onSessionData(Object data);
}
