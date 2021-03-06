package com.flashvisions.server.rtmp.transcoder.pojo.audio;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import com.flashvisions.server.rtmp.transcoder.interfaces.ICodecImplementation;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioBitrate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioChannel;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.pojo.base.MutableObject;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidAudioBitrate;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidAudioChannel;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidAudioCodec;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidAudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidCodecImplementation;

public class Audio extends MutableObject implements IAudio  {

	@NotNull
	@ValidAudioCodec
	private ICodec codec;
	
	@NotNull
	@ValidCodecImplementation
	private ICodecImplementation codecImplementation;
	
	@NotNull
	@ValidAudioBitrate
	private IAudioBitrate bitrate;
	
	@NotNull
	@ValidAudioSampleRate
	private IAudioSampleRate samplerate;
	
	@NotNull
	@ValidAudioChannel
	private IAudioChannel channel;
	
	@NotNull
	private ArrayList<IParameter> extraParams;
	
	@NotNull
	private ArrayList<IProperty> extraProperties;


	public ICodec getCodec() {
		return codec;
	}

	public void setCodec(ICodec codec) {
		this.codec = codec;
	}

	public IAudioBitrate getBitrate() {
		return bitrate;
	}

	public void setBitrate(IAudioBitrate bitrate) {
		this.bitrate = bitrate;
	}

	public IAudioSampleRate getSamplerate() {
		return samplerate;
	}

	public void setSamplerate(IAudioSampleRate samplerate) {
		this.samplerate = samplerate;
	}

	public IAudioChannel getChannel() {
		return channel;
	}

	public void setChannel(IAudioChannel channel) {
		this.channel = channel;
	}

	public ArrayList<IParameter> getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(ArrayList<IParameter> extraParams) {
		this.extraParams = extraParams;
	}

	@Override
	public ArrayList<IProperty> getExtraProperties() {
		// TODO Auto-generated method stub
		return extraProperties;
	}

	@Override
	public void setExtraProperties(ArrayList<IProperty> extraProperties) {
		// TODO Auto-generated method stub
		this.extraProperties = extraProperties;
	}

	@Override
	public ICodecImplementation getImplementation() {
		// TODO Auto-generated method stub
		return this.codecImplementation;
	}

	@Override
	public void setImplementation(ICodecImplementation implementation) {
		// TODO Auto-generated method stub
		this.codecImplementation = implementation;
	}

	
}
