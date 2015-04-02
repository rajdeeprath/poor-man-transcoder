package com.flashvisions.server.rtmp.transcoder.vo;

import java.io.Serializable;
import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IArbitaryProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioBitrate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioChannel;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec.Implementation;
import com.flashvisions.server.rtmp.transcoder.pojo.base.Mutable;

public class Audio extends Mutable implements IAudio, Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6959330589535619544L;
	private ICodec codec;
	private Implementation codecImplementation;
	private IAudioBitrate bitrate;
	private IAudioSampleRate samplerate;
	private IAudioChannel channel;
	private ArrayList<IArbitaryProperty> extraParams;
	

	public ICodec getCodec() {
		return codec;
	}

	public void setCodec(ICodec codec) {
		this.codec = codec;
	}

	public Implementation getCodecImplementation() {
		return codecImplementation;
	}

	public void setCodecImplementation(Implementation codecImplementation) {
		this.codecImplementation = codecImplementation;
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

	public ArrayList<IArbitaryProperty> getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(ArrayList<IArbitaryProperty> extraParams) {
		this.extraParams = extraParams;
	}
}
