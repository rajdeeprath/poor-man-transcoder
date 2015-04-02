package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IKeyFrameInterval extends IPassThru {
	public int getMinimunInterval();
	public void setMinimunInterval(int minimunInterval);
	public int getGop();
	public void setGop(int gop); 
}
