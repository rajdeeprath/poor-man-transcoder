package com.flashvisions.server.rtmp.transcoder.interfaces;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;

public interface AnalyzeInputSessionResultCallback {

	public void onAnalyzeProcessComplete(int exitValue);
	public void onAnalyzeProcessFailed(ExecuteException e, ExecuteWatchdog watchdog);
}
