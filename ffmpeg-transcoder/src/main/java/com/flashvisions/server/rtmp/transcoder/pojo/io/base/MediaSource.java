package com.flashvisions.server.rtmp.transcoder.pojo.io.base;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;

public class MediaSource extends Media implements IMediaInput {

	
	private String streamName;
	private String protocol;
	private String source;
	private IContainer container;
	private ArrayList<IProperty> flags;
	
	public MediaSource(String source, IContainer container){
		setSourcePath(source);
		setContainer(container);
	}
	
	public MediaSource(String source){
		setSourcePath(source);
	}
	
	@Override
	public String getSourcePath() {
		// TODO Auto-generated method stub
		return this.source;
	}

	@Override
	public void setSourcePath(String source) {
		// TODO Auto-generated method stub
		this.source = source;
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return this.protocol;
	}

	@Override
	public boolean isFile() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStreamingMedia() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<IProperty> getReadFlags() {
		// TODO Auto-generated method stub
		return this.flags;
	}

	@Override
	public void setReadFlags(ArrayList<IProperty> inputFlags) {
		// TODO Auto-generated method stub
		this.flags = inputFlags;
	}

	@Override
	public Object getMediaInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContainer getContainer() {
		// TODO Auto-generated method stub
		return this.container;
	}

	@Override
	public void setContainer(IContainer container) {
		// TODO Auto-generated method stub		
		this.container = container;
	}

	@Override
	public String getStreamName() {
		// TODO Auto-generated method stub
		return streamName;
	}

	@Override
	public void setStreamName(String streamName) {
		// TODO Auto-generated method stub
		this.streamName = streamName;
	}

	@Override
	public void setProtocol(String protocol) {
		// TODO Auto-generated method stub
		this.protocol = protocol;
	}
}
