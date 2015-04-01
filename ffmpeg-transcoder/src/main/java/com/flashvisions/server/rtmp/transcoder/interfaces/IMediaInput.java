package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

public interface IMediaInput {
	public String getContainer();
	public void setContainer(String container);
	public String getSourcePath();
	public void setSourcePath(String source);
	public String getProtocol();
	public boolean isFile();
	public boolean isStreamingMedia();
	public ArrayList<IFlag> getInputFlags();
	public void setInputFlags(ArrayList<IFlag> inputFlags);
	public String getStreamName();
	public void setStreamName(String streamName);
	public Object getMediaInfo();
}
