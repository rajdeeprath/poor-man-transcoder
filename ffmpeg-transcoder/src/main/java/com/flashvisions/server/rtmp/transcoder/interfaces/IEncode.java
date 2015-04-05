package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

public interface IEncode extends IMutable {

	public IAudio getAudioConfig();
	public void setAudioConfig(IAudio config);
	public IVideo getVideoConfig();
	public void setVideoConfig(IVideo config);
	public IMediaOutput getOutput();
	public void setOutput(IMediaOutput output);
	public String getName() ;
	public void setName(String name);
	public ArrayList<IProperty> getOutputflags();
	public void setOutputflags(ArrayList<IProperty> outputflags);
}
