package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IFrameSize extends IPassThru {
	public int getWidth();
	public void setWidth(int width);
	public int getHeight();
	public void setHeight(int height);
}
