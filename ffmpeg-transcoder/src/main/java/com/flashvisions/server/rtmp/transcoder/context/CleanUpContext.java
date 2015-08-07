package com.flashvisions.server.rtmp.transcoder.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.managers.IOManager;
import com.flashvisions.server.rtmp.transcoder.pool.TranscodeSessionPool;

public class CleanUpContext extends ContextBase implements Context {

	/**
	 * 
	 */
	private static final long serialVersionUID = -710512383393541304L;
	
	
	private ITranscoderResource input;
	private ArrayList<ITranscoderResource> outputs;
	private String workingDirectory;
	
	
	
	public ITranscoderResource getInput() {
		return input;
	}
	public void setInput(ITranscoderResource input) {
		this.input = input;
	}
	public ArrayList<ITranscoderResource> getOutputs() {
		return outputs;
	}
	public void setOutputs(ArrayList<ITranscoderResource> outputs) {
		this.outputs = outputs;
	}
	public String getWorkingDirectory() {
		return workingDirectory;
	}
	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

}
