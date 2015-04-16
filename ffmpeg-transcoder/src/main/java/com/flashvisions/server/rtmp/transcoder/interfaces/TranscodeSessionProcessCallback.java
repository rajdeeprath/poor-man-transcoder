package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface TranscodeSessionProcessCallback {

	public void onTranscodeProcessAdded(Process proc);
	public void onTranscodeProcessRemoved(Process proc);
}
