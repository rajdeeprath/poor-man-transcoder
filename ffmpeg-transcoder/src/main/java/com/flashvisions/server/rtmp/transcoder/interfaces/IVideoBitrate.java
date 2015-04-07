package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IVideoBitrate extends IPassThruObject {

	public int getAverage();
	public void setAverage(int average);
	public int getMinimum();
	public void setMinimum(int minimum);
	public int getMaximum();
	public void setMaximum(int maximum);
	public int getDeviceBuffer();
	public void setDeviceBuffer(int deviceBuffer);
}
