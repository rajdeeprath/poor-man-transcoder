package com.flashvisions.server.rtmp.transcoder.pojo;


import com.flashvisions.server.rtmp.transcoder.interfaces.IStreamInput;

public class InStream extends InMedia implements IStreamInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 302773710557348669L;

	public InStream(String source) {
		super(source);
		// TODO Auto-generated constructor stub
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
