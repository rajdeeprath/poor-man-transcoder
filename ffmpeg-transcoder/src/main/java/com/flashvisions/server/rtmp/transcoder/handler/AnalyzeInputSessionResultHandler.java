package com.flashvisions.server.rtmp.transcoder.handler;

import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;

import com.flashvisions.server.rtmp.transcoder.interfaces.AnalyzeInputSessionResultCallback;

public class AnalyzeInputSessionResultHandler extends DefaultExecuteResultHandler {

	private ExecuteWatchdog watchdog;
	private AnalyzeInputSessionResultCallback callback;
	
	public AnalyzeInputSessionResultHandler(ExecuteWatchdog watchdog){
		this.setWatchdog(watchdog);
	}
	
	public AnalyzeInputSessionResultHandler(ExecuteWatchdog watchdog, AnalyzeInputSessionResultCallback callback){
		this.setWatchdog(watchdog);
		this.setCallback(callback);
	}
	
	private void setWatchdog(ExecuteWatchdog watchdog) {
		this.watchdog = watchdog;
	}

	@Override
	public ExecuteException getException() {
		// TODO Auto-generated method stub
		return super.getException();
	}

	@Override
	public int getExitValue() {
		// TODO Auto-generated method stub
		return super.getExitValue();
	}

	@Override
	public boolean hasResult() {
		// TODO Auto-generated method stub
		return super.hasResult();
	}

	@Override
	public void onProcessComplete(int exitValue) {
		// TODO Auto-generated method stub
		if(this.callback != null) 
		this.callback.onAnalyzeProcessComplete(exitValue);
		
		super.onProcessComplete(exitValue);
	}

	@Override
	public void onProcessFailed(ExecuteException e) {
		// TODO Auto-generated method stub
		if(this.callback != null) 
		this.callback.onAnalyzeProcessFailed(e, watchdog);
		
		super.onProcessFailed(e);
	}
	
	public ExecuteWatchdog getWatchdog() {
		return watchdog;
	}
	

	public AnalyzeInputSessionResultCallback getCallback() {
		return callback;
	}

	public void setCallback(AnalyzeInputSessionResultCallback callback) {
		this.callback = callback;
	}

}
