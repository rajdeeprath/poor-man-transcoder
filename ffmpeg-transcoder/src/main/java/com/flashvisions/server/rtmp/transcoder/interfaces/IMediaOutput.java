package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.pojo.Flag;

public interface IMediaOutput {
	public String getContainer();
	public void setContainer(String container);
	public String getSourcePath();
	public void setSourcePath(String source);
	public String getProtocol();
	public boolean isFile();
	public boolean isStreamingMedia();
	public ArrayList<Flag> getOutputFlags();
	public void setOutputFlags(ArrayList<Flag> outputFlags);
	public String getStreamName();
	public void setStreamName(String streamName);
	public boolean isTemplate();
	public Object getMediaInfo();
}
