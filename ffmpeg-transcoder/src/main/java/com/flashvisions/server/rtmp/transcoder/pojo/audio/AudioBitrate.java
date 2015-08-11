package com.flashvisions.server.rtmp.transcoder.pojo.audio;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.flashvisions.server.rtmp.transcoder.ffmpeg.Flags;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioBitrate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruObject;

public class AudioBitrate extends PassThruObject implements IAudioBitrate 
{
	private static final String key = Flags.AUDIO_BITRATE;
	
	@NotNull
	@Range(min = 0, max = 1000, message = "{com.flashvisions.server.rtmp.transcoder.validation.audio.bitrate.invalid}")
	private Object value;
	
	
	/************ Copy constructor *********/
	public AudioBitrate(IAudioBitrate object){
		this.setSameAsSource(object.getSameAsSource());
		this.value = object.getValue();
	}
	
	public AudioBitrate(boolean sameAsSource){
		this.setSameAsSource(sameAsSource);
		this.value = 0;
	}
	
	public AudioBitrate(Object bitrate){
		this.value = bitrate;
	}
	
	public AudioBitrate(){
		
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return AudioBitrate.key;
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
		return new AudioBitrate(this.getValue());
	}
	
}
