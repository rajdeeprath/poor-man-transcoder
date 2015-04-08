package com.flashvisions.server.rtmp.transcoder.interfaces;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;

public interface TranscodeSessionResultCallback {

	public void onTranscodeProcessComplete(int exitValue);
	public void onTranscodeProcessFailed(ExecuteException e, ExecuteWatchdog watchdog);
}
