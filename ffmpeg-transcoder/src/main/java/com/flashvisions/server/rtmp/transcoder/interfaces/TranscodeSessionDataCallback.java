package com.flashvisions.server.rtmp.transcoder.interfaces;


public interface TranscodeSessionDataCallback {

	public void onTranscodeProcessStart(long timestamp);
	public void onTranscodeProcessData(Object data, long timestamp);
}
