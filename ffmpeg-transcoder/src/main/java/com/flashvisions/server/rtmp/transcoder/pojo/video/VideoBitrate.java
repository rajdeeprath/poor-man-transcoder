package com.flashvisions.server.rtmp.transcoder.pojo.video;

import javax.validation.constraints.NotNull;

import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruObject;

public class VideoBitrate extends PassThruObject implements IVideoBitrate 
{
	@NotNull
	private IParameter minimum;
	
	@NotNull
	private IParameter maximum;
	
	@NotNull
	private IParameter average;
	
	@NotNull
	private IParameter deviceBuffer;
	
	
	public VideoBitrate(){
		
	}
	
	
	public VideoBitrate(IVideoBitrate object){
		this.setSameAsSource(object.getSameAsSource());
		
		this.average = (AverageBitrate) object.getAverage();
		this.minimum = (MinimumBitrate) object.getMinimum();
		this.maximum = (MaximumBitrate) object.getMaximum();
		this.deviceBuffer = (DeviceBuffer) object.getDeviceBuffer();		
	}
	
	public VideoBitrate(boolean sameAsSource){
		this.setSameAsSource(sameAsSource);
		
		this.minimum = new MinimumBitrate(0);
		this.maximum = new MaximumBitrate(0);
		this.average = new AverageBitrate(0);
		this.deviceBuffer = new DeviceBuffer(0);
	}
	
	public VideoBitrate(IParameter average, IParameter minimum, IParameter maximum, IParameter deviceBuffer){
		this.average = average;
		this.minimum = minimum;
		this.maximum = maximum;
		this.deviceBuffer = deviceBuffer;
	}
	
	public IParameter getMinimum() {
		return minimum;
	}
	
	public void setMinimum(IParameter minimum) {
		this.minimum = minimum;
	}
	
	public IParameter getMaximum() {
		return maximum;
	}
	
	public void setMaximum(IParameter maximum) {
		this.maximum = maximum;
	}
	
	public IParameter getDeviceBuffer() {
		return deviceBuffer;
	}
	
	public void setDeviceBuffer(IParameter deviceBuffer) {
		this.deviceBuffer = deviceBuffer;
	}

	@Override
	public IParameter getAverage() {
		// TODO Auto-generated method stub
		return average;
	}

	@Override
	public void setAverage(IParameter average) {
		// TODO Auto-generated method stub
		this.average = average;
	}	
	
}
