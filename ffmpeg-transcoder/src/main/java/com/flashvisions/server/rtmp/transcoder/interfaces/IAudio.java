package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.pojo.Codec.Implementation;

public interface IAudio extends IMutable {

	public ICodec getCodec();
	public void setCodec(ICodec codec);
	public Implementation getCodecImplementation();
	public void setCodecImplementation(Implementation codecImplementation);
	public IAudioBitrate getBitrate();
	public void setBitrate(IAudioBitrate bitrate);
	public IAudioSampleRate getSamplerate();
	public void setSamplerate(IAudioSampleRate samplerate);
	public IAudioChannel getChannel();
	public void setChannel(IAudioChannel channels);
	public ArrayList<IParam> getExtraParams();
	public void setExtraParams(ArrayList<IParam> extraParams);
}
