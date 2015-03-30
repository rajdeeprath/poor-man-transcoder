package com.flashvisions.server.rtmp.transcoder.vo;

import com.flashvisions.server.rtmp.transcoder.interfaces.IDataInput;

public class StreamingSource implements IDataInput {

	private String source;
	
	
	public StreamingSource(String source){
		setSourcePath(source);
	}
	
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
		return false;
	}

	@Override
	public boolean isSttreamingMedia() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object getMediaInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
