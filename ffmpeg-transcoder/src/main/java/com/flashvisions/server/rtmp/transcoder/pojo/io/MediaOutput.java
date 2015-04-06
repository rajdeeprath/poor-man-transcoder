package com.flashvisions.server.rtmp.transcoder.pojo.io;

import java.net.URI;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;
import com.flashvisions.server.rtmp.transcoder.pojo.Container;
import com.flashvisions.server.rtmp.transcoder.pojo.Property;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;

public class MediaOutput implements IMediaOutput {

	private static Logger logger = LoggerFactory.getLogger(MediaOutput.class);
	
	private boolean isTemplate;
	
	private String streamName;
	private String protocol;
	private String source;
	private IContainer container;
	private ArrayList<Property> outputFlags;
	
	
	public MediaOutput(String source){
		setSourcePath(source);
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
		if(!isTemplate)this.validateSource();
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
	public ArrayList<Property> getOutputFlags() {
		// TODO Auto-generated method stub
		return this.outputFlags;
	}

	@Override
	public void setOutputFlags(ArrayList<Property> outputFlags) {
		// TODO Auto-generated method stub
		this.outputFlags = outputFlags;
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
	
	protected void validateSource()
	{
		try 
		{
			URI uri = new URI(this.source);
			this.protocol = uri.getScheme();
			this.streamName = uri.getPath().substring(uri.getPath().lastIndexOf("/")+1);
			this.setContainer(new Container(IOUtils.getContainer(this.source)));
		} 
		catch (Exception e) 
		{
			logger.info(e.getMessage());
		}
	}

	public boolean isTemplate() 
	{
		return this.isTemplate;
	}

}
