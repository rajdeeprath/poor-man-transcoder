package com.flashvisions.server.rtmp.transcoder.pojo.io.base;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;

public class MediaOutput implements IMediaOutput {
	
	private boolean isTemplate;
	
	private String streamName;
	private String protocol;
	private String source;
	private IContainer container;
	private ArrayList<IProperty> properties;
	
	
	public MediaOutput(String source){
		setSourcePath(source);
	}
	
	public MediaOutput(String source, IContainer container){
		setSourcePath(source);
		setContainer(container);
	}
	
	public MediaOutput(String source, boolean isTemplate){
		this.isTemplate = isTemplate ;
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

	@Override
	public ArrayList<IProperty> getOutputFlags() {
		// TODO Auto-generated method stub
		return this.properties;
	}

	@Override
	public void setOutputFlags(ArrayList<IProperty> properties) {
		// TODO Auto-generated method stub
		this.properties = properties;
	}

	@Override
	public Object getMediaInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getStreamName() 
	{
		return streamName;
	}

	public void setStreamName(String streamName) 
	{
		this.streamName = streamName;
	}

	public boolean isTemplate() 
	{
		return this.isTemplate;
	}

	@Override
	public void setProtocol(String protocol) {
		// TODO Auto-generated method stub
		this.protocol = protocol;
	}

}
