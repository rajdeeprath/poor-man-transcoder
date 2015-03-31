package com.flashvisions.server.rtmp.transcoder.pojo;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IFileOutput;

public class OutFile implements IFileOutput {

	private String source;
	private String container;
	private ArrayList<Flag> outputFlags;
	
	@Override
	public String getContainer() {
		// TODO Auto-generated method stub
		return this.container;
	}

	@Override
	public void setContainer(String container) {
		// TODO Auto-generated method stub
		this.container = container;
	}

	@Override
	public String getSourcePath() {
		// TODO Auto-generated method stub
		return source;
	}

	@Override
	public void setSourcePath(String source) {
		// TODO Auto-generated method stub
		this.source = source;
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFile() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isStreamingMedia() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Flag> getOutputFlags() {
		// TODO Auto-generated method stub
		return this.outputFlags;
	}

	@Override
	public void setOutputFlags(ArrayList<Flag> outputFlags) {
		// TODO Auto-generated method stub
		this.outputFlags = outputFlags;
	}

	@Override
	public Object getMediaInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
