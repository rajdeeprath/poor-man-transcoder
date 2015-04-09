package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IMediaOutput {
	public IContainer getContainer();
	public void setContainer(IContainer container);
	public String getSourcePath();
	public void setSourcePath(String source);
	public String getProtocol();
	public void setProtocol(String protocol);
	public boolean isFile();
	public boolean isStreamingMedia();
	public String getStreamName();
	public void setStreamName(String streamName);
	public boolean isTemplate();
	public Object getMediaInfo();
	
}
