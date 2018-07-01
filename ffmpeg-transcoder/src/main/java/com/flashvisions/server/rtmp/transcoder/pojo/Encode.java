package com.flashvisions.server.rtmp.transcoder.pojo;


import javax.validation.constraints.NotNull;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncode;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.pojo.base.MutableObject;

public class Encode extends MutableObject implements IEncode  {

	@NotNull
	private String name;
	
	@NotNull
	private IVideo vConfig;
	
	@NotNull
	private IAudio aConfig;
	
	@NotNull
	private ITranscodeOutput output;	
	

	@Override
	public IAudio getAudioConfig() {
		return aConfig;
	}
	
	
	@Override
	public void setAudioConfig(IAudio aConfig) {
		this.aConfig = aConfig;
	}
	
	
	@Override
	public IVideo getVideoConfig() {
		return vConfig;
	}
	
	
	@Override
	public void setVideoConfig(IVideo vConfig) {
		this.vConfig = vConfig;
	}


	@Override
	public String getName() {
		return name;
	}


	@Override
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
