package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IDataInput {
	public String getSourcePath();
	public void setSourcePath(String source);
	public String getProtocol();
	public boolean isFile();
	public boolean isSttreamingMedia();
	public Object getMediaInfo();
}
