package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

public interface IMediaInput {
	public IContainer getContainer();
	public void setContainer(IContainer container);
	public String getSourcePath();
	public void setSourcePath(String source);
	public void setProtocol(String protocol);
	public String getProtocol();
	public boolean isFile();
	public boolean isStreamingMedia();
	public ArrayList<IProperty> getInputFlags();
	public void setInputFlags(ArrayList<IProperty> inputFlags);
	public String getStreamName();
	public void setStreamName(String streamName);
	public Object getMediaInfo();
}
