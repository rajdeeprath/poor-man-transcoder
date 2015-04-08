package com.flashvisions.server.rtmp.transcoder.interfaces;


public interface TranscodeSessionDataCallback {

	public void onTranscodeProcessData(Object data, long timestamp);
}
