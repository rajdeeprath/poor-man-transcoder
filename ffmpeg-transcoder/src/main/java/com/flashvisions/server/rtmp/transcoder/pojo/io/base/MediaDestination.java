package com.flashvisions.server.rtmp.transcoder.pojo.io.base;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;

public class MediaDestination extends Media implements IMediaOutput {
	
	private ArrayList<IProperty> flags;
	private String streamName;
	private String protocol;
	private String source;
	private IContainer container;
	
	
	public MediaDestination(IMediaOutput object){
		this.setSourcePath(object.getSourcePath());
		this.setMediaName(object.getMediaName());
		this.setProtocol(object.getProtocol());
		this.setContainer(object.getContainer());
	}
	
	public MediaDestination(String source){
		setSourcePath(source);
	}
	
	public MediaDestination(String source, IContainer container){
		setSourcePath(source);
		setContainer(container);
	}
	
	public MediaDestination(String source, boolean isTemplate){
		setSourcePath(source);
	}
	
	@Override
	public IContainer getContainer() {
		// TODO Auto-generated method stub
		return container;
	}

	@Override
	public void setContainer(IContainer container) {
		// TODO Auto-generated method stub
		this.container = container;
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

	public String getMediaName() 
	{
		return streamName;
	}

	public void setMediaName(String streamName) 
	{
		this.streamName = streamName;
	}

	@Override
	public void setProtocol(String protocol) {
		// TODO Auto-generated method stub
		this.protocol = protocol;
	}

	@Override
	public ArrayList<IProperty> getWriteFlags() {
		// TODO Auto-generated method stub
		return flags;
	}

	@Override
	public void setWriteFlags(ArrayList<IProperty> writeFlags) {
		// TODO Auto-generated method stub
		this.flags = writeFlags;
	}

}
