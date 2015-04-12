package com.flashvisions.server.rtmp.transcoder.interfaces;


public interface IVideoBitrate extends IPassThruObject {

	public IParameter getAverage();
	public void setAverage(IParameter average);
	public IParameter getMinimum();
	public void setMinimum(IParameter minimum);
	public IParameter getMaximum();
	public void setMaximum(IParameter maximum);
	public IParameter getDeviceBuffer();
	public void setDeviceBuffer(IParameter deviceBuffer);
}
