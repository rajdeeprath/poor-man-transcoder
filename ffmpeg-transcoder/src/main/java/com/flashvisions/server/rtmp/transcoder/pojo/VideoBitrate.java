package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;

import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThru;

public class VideoBitrate extends PassThru implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6254029729499034766L;
	private int minimum;
	private int maximum;
	private int deviceBuffer;
	
	
	public VideoBitrate(){
		
	}
	
	public VideoBitrate(boolean sameAsSource){
		this.setSameAsSource(sameAsSource);
		
		this.minimum = -1;
		this.maximum = -1;
		this.deviceBuffer = -1;
	}
	
	public VideoBitrate(int minimum, int maximum, int deviceBuffer){
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
}
