package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IFrameRate extends IPassThru {
	public int getFramerate();
	public void setFramerate(int framerate);
}
