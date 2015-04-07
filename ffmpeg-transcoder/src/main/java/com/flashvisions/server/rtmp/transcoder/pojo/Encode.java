package com.flashvisions.server.rtmp.transcoder.pojo;


import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncode;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.pojo.base.MutableObject;

public class Encode extends MutableObject implements IEncode  {

	private String name;
	
	private IVideo vConfig;
	private IAudio aConfig;
	
	private ITranscodeOutput output;
	
	

	public IAudio getAudioConfig() {
		return aConfig;
	}
	
	
	public void setAudioConfig(IAudio aConfig) {
		this.aConfig = aConfig;
	}
	
	
	public IVideo getVideoConfig() {
		return vConfig;
	}
	
	
	public void setVideoConfig(IVideo vConfig) {
		this.vConfig = vConfig;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public ITranscodeOutput getOutput() {
		// TODO Auto-generated method stub
		return output;
	}


	@Override
	public void setOutput(ITranscodeOutput output) {
		// TODO Auto-generated method stub
		this.output = output;
	}
	
	
}
