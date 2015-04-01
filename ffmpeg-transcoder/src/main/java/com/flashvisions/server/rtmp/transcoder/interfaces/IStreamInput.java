package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IStreamInput extends IMediaInput {
	public String getPublishName();
	public void setPublishName(String name);
}
