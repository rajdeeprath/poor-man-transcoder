package com.flashvisions.server.rtmp.transcoder.pojo.io;

import java.io.File;

import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFileInput;
import com.flashvisions.server.rtmp.transcoder.pojo.io.base.MediaSource;

public class FileSource extends MediaSource implements IFileInput {

	File file;
	
	public FileSource(String source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public FileSource(String source, IContainer container){
		super(source, container);
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

	
}
