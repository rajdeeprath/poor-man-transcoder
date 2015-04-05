package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncode;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;

public class Encode extends Mutable implements IEncode, Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149259018891670101L;
	private String name;
	private IMediaOutput output;
	
	private IVideo vConfig;
	private IAudio aConfig;
	
	public ArrayList<IProperty> outputflags;
	
	
	
	public ArrayList<IProperty> getOutputflags() {
		return outputflags;
	}


	public void setOutputflags(ArrayList<IProperty> outputflags) {
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