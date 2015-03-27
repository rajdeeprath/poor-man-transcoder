package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.pojo.AudioBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.AudioChannel;
import com.flashvisions.server.rtmp.transcoder.pojo.AudioProperty;
import com.flashvisions.server.rtmp.transcoder.pojo.AudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec.Implementation;

public interface IAudio extends IMutable {

	public ICodec getCodec();
	public void setCodec(ICodec codec);
	public Implementation getCodecImplementation();
	public void setCodecImplementation(Implementation codecImplementation);
	public AudioBitrate getBitrate();
	public void setBitrate(AudioBitrate bitrate);
	public AudioSampleRate getSamplerate();
	public void setSamplerate(AudioSampleRate samplerate);
	public AudioChannel getChannel();
	public void setChannel(AudioChannel channels);
	public ArrayList<AudioProperty> getExtraParams();
	public void setExtraParams(ArrayList<AudioProperty> extraParams);
}
