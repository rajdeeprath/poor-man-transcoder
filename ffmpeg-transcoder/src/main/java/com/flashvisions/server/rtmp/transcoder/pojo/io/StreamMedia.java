package com.flashvisions.server.rtmp.transcoder.pojo.io;


import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IStreamingMedia;
import com.flashvisions.server.rtmp.transcoder.pojo.io.base.Media;

public class StreamMedia extends Media implements IStreamingMedia {

	public StreamMedia(String source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	public StreamMedia(String source, IContainer container){
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
