package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.File;
import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IFileInput;

public class InFile implements IFileInput {

	private String source;
	private ArrayList<Flag> inputFlags;
	private File file;
	
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
	public ArrayList<Flag> getInputFlags() {
		// TODO Auto-generated method stub
		return inputFlags;
	}

	@Override
	public void setInputFlags(ArrayList<Flag> inputFlags) {
		// TODO Auto-generated method stub
		this.inputFlags = inputFlags;
	}

	@Override
	public Object getMediaInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFile() {
		// TODO Auto-generated method stub
		return file;
	}

	@Override
	public void setFile(File file) {
		// TODO Auto-generated method stub
		this.file = file;
	}

}
