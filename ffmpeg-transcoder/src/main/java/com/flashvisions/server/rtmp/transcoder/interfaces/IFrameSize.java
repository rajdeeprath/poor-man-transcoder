package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IFrameSize extends IPassThru, IParameter {
	public Object getWidth();
	public void setWidth(Object width);
	public Object getHeight();
	public void setHeight(Object height);
}
