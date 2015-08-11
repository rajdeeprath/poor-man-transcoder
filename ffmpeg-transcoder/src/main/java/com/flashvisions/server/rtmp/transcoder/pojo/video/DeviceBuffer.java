package com.flashvisions.server.rtmp.transcoder.pojo.video;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.flashvisions.server.rtmp.transcoder.ffmpeg.Flags;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.pojo.Parameter;

public class DeviceBuffer extends Parameter implements IParameter {

	private static final String key = Flags.VIDEO_BUFFER; 
	
	@NotNull
	@Range(min = 0, max = 5000, message = "{com.flashvisions.server.rtmp.transcoder.validation.video.bitrate.invalid.devicebuffer}")
	private Object value;
	
	
	public DeviceBuffer(){
		
	}
	
	public DeviceBuffer(Object value){
		this.value = value;
	}
	
	public DeviceBuffer(DeviceBuffer object){
		this.value = object.value;
	}
	
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return DeviceBuffer.key;
	}

	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		// NO OP
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		this.value = value;
	}

	@Override
	public IParameter clone() {
		// TODO Auto-generated method stub
		return new DeviceBuffer(this.value);
	}


}
