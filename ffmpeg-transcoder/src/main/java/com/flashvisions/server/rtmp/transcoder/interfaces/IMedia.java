package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IMedia {

	public IContainer getContainer();
	public void setContainer(IContainer container);
	public String getSourcePath();
	public void setSourcePath(String source);
	public void setProtocol(String protocol);
	public String getProtocol();
	public boolean isFile();
	public boolean isStreamingMedia();
	public String getMediaName();
	public void setMediaName(String streamName);
}
