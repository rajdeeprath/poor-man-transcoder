package com.flashvisions.server.rtmp.transcoder.pojo.io;

import com.flashvisions.server.rtmp.transcoder.interfaces.IStreamOutput;

public class StreamOutput extends MediaOutput implements IStreamOutput {

	public StreamOutput(String source) {
		super(source);
	}
	
	public StreamOutput(String source, boolean isTemplate){
		super(source,isTemplate);
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

}
