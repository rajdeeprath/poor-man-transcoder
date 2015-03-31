package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.pojo.Flag;

public interface IDataInput {
	public String getSourcePath();
	public void setSourcePath(String source);
	public String getProtocol();
	public boolean isFile();
	public boolean isStreamingMedia();
	public ArrayList<Flag> getInputFlags();
	public void setInputFlags(ArrayList<Flag> inputFlags);
	public Object getMediaInfo();
}
