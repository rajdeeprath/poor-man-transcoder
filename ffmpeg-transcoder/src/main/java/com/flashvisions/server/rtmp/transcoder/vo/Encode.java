package com.flashvisions.server.rtmp.transcoder.vo;

import java.io.Serializable;
import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncode;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFlag;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;
import com.flashvisions.server.rtmp.transcoder.pojo.base.Mutable;

public class Encode extends Mutable implements IEncode, Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149259018891670101L;
	private String name;
	private IMediaOutput output;
	
	private IVideo vConfig;
	private IAudio aConfig;
	
	public ArrayList<IFlag> outputflags;
	
	
	
	public ArrayList<IFlag> getOutputflags() {
		return outputflags;
	}


	public void setOutputflags(ArrayList<IFlag> outputflags) {
		this.outputflags = outputflags;
	}


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
	
	
	public IMediaOutput getOutput() {
		return output;
	}
	
	public void setOutput(IMediaOutput output) {
		this.output = output;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
}
