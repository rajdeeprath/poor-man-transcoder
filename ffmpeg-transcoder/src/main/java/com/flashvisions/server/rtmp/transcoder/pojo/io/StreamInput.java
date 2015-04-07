package com.flashvisions.server.rtmp.transcoder.pojo.io;


import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IStreamInput;
import com.flashvisions.server.rtmp.transcoder.pojo.io.base.MediaInput;

public class StreamInput extends MediaInput implements IStreamInput {

	public StreamInput(String source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public StreamInput(String source, IContainer container){
		super(source, container);
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
