package com.flashvisions.server.rtmp.transcoder.context;

import java.util.ArrayList;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;

import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;


public class TranscoderOutputContext extends ContextBase implements Context {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6055565161013950468L;
	
	private ITranscoderResource input;
	private ArrayList<ITranscoderResource> outputs;
	private String workingDirectory;
	
	private ISession session;
	
	
	public TranscoderOutputContext(ISession session)	
	{
		this.setSession(session);
		
		this.setInput(session.getInputSource());
		this.setOutputs(session.getOutputs());
		this.setWorkingDirectory(session.getWorkingDirectoryPath());
	}
	
	public TranscoderOutputContext()	
	{
		
	}
	
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

	public ISession getSession() {
		return session;
	}

	public void setSession(ISession session) {
		this.session = session;
	}	
}
