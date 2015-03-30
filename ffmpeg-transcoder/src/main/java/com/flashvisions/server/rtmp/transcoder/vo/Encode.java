package com.flashvisions.server.rtmp.transcoder.vo;

import java.io.Serializable;
import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncode;
import com.flashvisions.server.rtmp.transcoder.pojo.Flag;
import com.flashvisions.server.rtmp.transcoder.pojo.base.Mutable;

public class Encode extends Mutable implements IEncode, Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149259018891670101L;
	private String name;
	private String output;
	
	private Video vConfig;
	private IAudio aConfig;
	
	public ArrayList<Flag> outputflags;
	
	
	
	public ArrayList<Flag> getOutputflags() {
		return outputflags;
	}


	public void setOutputflags(ArrayList<Flag> outputflags) {
		this.outputflags = outputflags;
	}


	public IAudio getAudioConfig() {
		return aConfig;
	}
	
	
	public void setAudioConfig(IAudio aConfig) {
		this.aConfig = aConfig;
	}
	
	
	public Video getVideoConfig() {
		return vConfig;
	}
	
	
	public void setVideoConfig(Video vConfig) {
		this.vConfig = vConfig;
	}
	
	
	public String getOutput() {
		return output;
	}
	
	public void setOutput(String output) {
		this.output = output;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
}
