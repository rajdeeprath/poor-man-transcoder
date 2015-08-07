package com.flashvisions.server.rtmp.transcoder.vo;

public class TranscoderExecutionError {

	public Exception e;
	public String cause;
	public long timestamp;
	
	public TranscoderExecutionError()
	{
		
	}
	
	public TranscoderExecutionError(Exception e, String cause, long timestamp)
	{
		this.e = e;
		this.cause = cause;
		this.timestamp = timestamp;
	}
}
