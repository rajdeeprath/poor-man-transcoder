package com.flashvisions.server.rtmp.transcoder.pojo.io.base;

import java.net.URI;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;
import com.flashvisions.server.rtmp.transcoder.pojo.Container;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;

public class MediaInput implements IMediaInput {

	private static Logger logger = LoggerFactory.getLogger(MediaInput.class);
	
	private String streamName;
	private String protocol;
	private String source;
	private IContainer container;
	private ArrayList<IProperty> inputFlags;
	
	public MediaInput(String source){
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
		this.validateSource();
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
	public ArrayList<IProperty> getInputFlags() {
		// TODO Auto-generated method stub
		return this.inputFlags;
	}

	@Override
	public void setInputFlags(ArrayList<IProperty> inputFlags) {
		// TODO Auto-generated method stub
		this.inputFlags = inputFlags;
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
}
