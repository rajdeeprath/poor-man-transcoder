package com.flashvisions.server.rtmp.transcoder.pojo.video;

import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruProperty;

public class VideoBitrate extends PassThruProperty implements IVideoBitrate 
{
	private int minimum;
	private int maximum;
	private int average;
	private int deviceBuffer;
	
	
	public VideoBitrate(){
		
	}
	
	public VideoBitrate(boolean sameAsSource){
		this.setSameAsSource(sameAsSource);
		
		this.minimum = -1;
		this.maximum = -1;
		this.deviceBuffer = -1;
	}
	
	public VideoBitrate(int average, int minimum, int maximum, int deviceBuffer){
		this.average = average;
		this.minimum = minimum;
		this.maximum = maximum;
		this.deviceBuffer = deviceBuffer;
	}
	
	public int getMinimum() {
		return minimum;
	}
	
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
	
	public int getMaximum() {
		return maximum;
	}
	
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
	
	public int getDeviceBuffer() {
		return deviceBuffer;
	}
	
	public void setDeviceBuffer(int deviceBuffer) {
		this.deviceBuffer = deviceBuffer;
	}

	@Override
	public int getAverage() {
		// TODO Auto-generated method stub
		return average;
	}

	@Override
	public void setAverage(int average) {
		// TODO Auto-generated method stub
		this.average = average;
	}
}
