package com.flashvisions.server.rtmp.transcoder.pojo.io;

import com.flashvisions.server.rtmp.transcoder.interfaces.IFileOutput;
import com.flashvisions.server.rtmp.transcoder.pojo.io.base.MediaOutput;

public class FileOutput extends MediaOutput implements IFileOutput {
	
	public FileOutput(String source) {
		super(source);
	}
	
	public FileOutput(String source, boolean isTemplate){
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
