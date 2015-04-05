package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.pojo.Property;

public interface IMediaOutput {
	public IContainer getContainer();
	public void setContainer(IContainer container);
	public String getSourcePath();
	public void setSourcePath(String source);
	public String getProtocol();
	public boolean isFile();
	public boolean isStreamingMedia();
	public ArrayList<Property> getOutputFlags();
	public void setOutputFlags(ArrayList<Property> outputFlags);
	public String getStreamName();
	public void setStreamName(String streamName);
	public boolean isTemplate();
	public Object getMediaInfo();
}
