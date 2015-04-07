package com.flashvisions.server.rtmp.transcoder.pojo.io;

import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IStreamOutput;
import com.flashvisions.server.rtmp.transcoder.pojo.io.base.MediaOutput;

public class StreamOutput extends MediaOutput implements IStreamOutput {

	public StreamOutput(String source) {
		super(source);
	}
	
	public StreamOutput(String source, IContainer container){
		super(source, container);
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
