package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface ISessionHandler {
	public void onReady(ISession session);
	public void onStart(ISession session, Process proc);
	public void onStop(ISession session, Process proc);
	public void onError(ISession session, Process proc);
	public void onProgress(ISession session, Process proc);
}
