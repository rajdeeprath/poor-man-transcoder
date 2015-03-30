package com.flashvisions.server.rtmp.transcoder.vo;

import java.io.Serializable;
import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.pojo.AudioBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.AudioChannel;
import com.flashvisions.server.rtmp.transcoder.pojo.AudioProperty;
import com.flashvisions.server.rtmp.transcoder.pojo.AudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec.Implementation;
import com.flashvisions.server.rtmp.transcoder.pojo.base.Mutable;

public class Audio extends Mutable implements IAudio, Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6959330589535619544L;
	private ICodec codec;
	private Implementation codecImplementation;
	private AudioBitrate bitrate;
	private AudioSampleRate samplerate;
	private AudioChannel channel;
	private ArrayList<AudioProperty> extraParams;
	

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

	public AudioBitrate getBitrate() {
		return bitrate;
	}

	public void setBitrate(AudioBitrate bitrate) {
		this.bitrate = bitrate;
	}

	public AudioSampleRate getSamplerate() {
		return samplerate;
	}

	public void setSamplerate(AudioSampleRate samplerate) {
		this.samplerate = samplerate;
	}

	public AudioChannel getChannel() {
		return channel;
	}

	public void setChannel(AudioChannel channel) {
		this.channel = channel;
	}

	public ArrayList<AudioProperty> getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(ArrayList<AudioProperty> extraParams) {
		this.extraParams = extraParams;
	}
}
