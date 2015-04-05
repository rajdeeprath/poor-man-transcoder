package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.interfaces.IStreamOutput;

public class OutStream extends OutMedia implements IStreamOutput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3821162980006650017L;

	public OutStream(String source) {
		super(source);
	}
	
	public OutStream(String source, boolean isTemplate){
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
