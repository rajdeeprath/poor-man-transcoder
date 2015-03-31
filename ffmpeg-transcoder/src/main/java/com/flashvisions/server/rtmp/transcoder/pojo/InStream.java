package com.flashvisions.server.rtmp.transcoder.pojo;

import java.util.ArrayList;
import com.flashvisions.server.rtmp.transcoder.interfaces.IStreamInput;

public class InStream implements IStreamInput {

	private String source;
	private ArrayList<Flag> inputFlags;
	
	public InStream(String source){
		setSourcePath(source);
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
		return false;
	}

	@Override
	public boolean isStreamingMedia() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object getMediaInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Flag> getInputFlags() {
		// TODO Auto-generated method stub
		return inputFlags;
	}

	@Override
	public void setInputFlags(ArrayList<Flag> inputFlags) {
		// TODO Auto-generated method stub
		this.inputFlags = inputFlags;
	}

}
