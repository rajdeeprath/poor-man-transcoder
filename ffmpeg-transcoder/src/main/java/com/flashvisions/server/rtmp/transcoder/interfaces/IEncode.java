package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.pojo.Flag;
import com.flashvisions.server.rtmp.transcoder.vo.Video;

public interface IEncode extends IMutable {

	public IAudio getAudioConfig();
	public void setAudioConfig(IAudio config);
	public Video getVideoConfig();
	public void setVideoConfig(Video config);
	public String getOutput();
	public void setOutput(String output);
	public String getName() ;
	public void setName(String name);
	public ArrayList<Flag> getOutputflags();
	public void setOutputflags(ArrayList<Flag> outputflags);
}
