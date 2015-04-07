package com.flashvisions.server.rtmp.transcoder.interfaces;


public interface IEncode extends IMutable {

	public String getName();
	public void setName(String name);
	public IAudio getAudioConfig();
	public void setAudioConfig(IAudio config);
	public IVideo getVideoConfig();
	public void setVideoConfig(IVideo config);
	public ITranscodeOutput getOutput();
	public void setOutput(ITranscodeOutput output);
}
