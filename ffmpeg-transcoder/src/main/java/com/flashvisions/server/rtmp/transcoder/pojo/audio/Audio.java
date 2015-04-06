package com.flashvisions.server.rtmp.transcoder.pojo.audio;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IParam;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioBitrate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioChannel;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec.Implementation;
import com.flashvisions.server.rtmp.transcoder.pojo.base.Mutable;

public class Audio extends Mutable implements IAudio  {

	private ICodec codec;
	private Implementation codecImplementation;
	private IAudioBitrate bitrate;
	private IAudioSampleRate samplerate;
	private IAudioChannel channel;
	private ArrayList<IParam> extraParams;


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

	public ArrayList<IParam> getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(ArrayList<IParam> extraParams) {
		this.extraParams = extraParams;
	}
}
