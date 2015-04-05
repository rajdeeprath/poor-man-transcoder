package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;

public class MediaInput implements IMediaInput, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -914688622907385993L;
	private String streamName;
	private String protocol;
	private String source;
	private String container;
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
	public String getContainer() {
		// TODO Auto-generated method stub
		return this.container;
	}

	@Override
	public void setContainer(String container) {
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
		} 
		catch (URISyntaxException e) 
		{
			e.printStackTrace();
		}
	}
}
