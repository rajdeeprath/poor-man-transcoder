package com.flashvisions.server.rtmp.transcoder.pojo.io;

import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFileOutput;
import com.flashvisions.server.rtmp.transcoder.pojo.io.base.MediaDestination;

public class FileDestination extends MediaDestination implements IFileOutput {
	
	public FileDestination(String source) {
		super(source);
	}
	
	public FileDestination(String source, IContainer container){
		super(source, container);
	}
	
	public FileDestination(String source, boolean isTemplate){
		super(source,isTemplate);
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
