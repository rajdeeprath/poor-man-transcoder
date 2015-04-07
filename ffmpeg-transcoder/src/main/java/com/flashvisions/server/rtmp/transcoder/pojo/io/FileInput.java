package com.flashvisions.server.rtmp.transcoder.pojo.io;

import java.io.File;

import com.flashvisions.server.rtmp.transcoder.interfaces.IFileInput;
import com.flashvisions.server.rtmp.transcoder.pojo.io.base.MediaInput;

public class FileInput extends MediaInput implements IFileInput {

	File file;
	
	public FileInput(String source) {
		super(source);
		// TODO Auto-generated constructor stub
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
	protected void validateSource() {
		// TODO Auto-generated method stub
		this.setFile(new File(this.getSourcePath()));
		this.setStreamName(this.getFile().getName());
		super.validateSource();
	}

	
}
