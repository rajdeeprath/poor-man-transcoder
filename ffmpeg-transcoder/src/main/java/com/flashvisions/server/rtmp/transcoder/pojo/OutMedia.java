package com.flashvisions.server.rtmp.transcoder.pojo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;

public class OutMedia implements IMediaOutput {

	private boolean isTemplate;
	
	private String streamName;
	private String protocol;
	private String source;
	private String container;
	private ArrayList<Flag> outputFlags;
	
	
	public OutMedia(String source){
		setSourcePath(source);
	}
	
	public OutMedia(String source, boolean isTemplate){
		this.isTemplate = isTemplate ;
		setSourcePath(source);
	}
	
	@Override
	public String getContainer() {
		// TODO Auto-generated method stub
		return container;
	}

	@Override
	public void setContainer(String container) {
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
	public ArrayList<Flag> getOutputFlags() {
		// TODO Auto-generated method stub
		return this.outputFlags;
	}

	@Override
	public void setOutputFlags(ArrayList<Flag> outputFlags) {
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
			
			switch(Protocol.SCHEMES.valueOf(this.protocol.toUpperCase()))
			{		
				case RTMP:
				case RTMPE:
				case RTMPS:
				case RTMPT:
					setContainer(String.valueOf(Container.SCHEMES.FLV).toLowerCase());
					break;
					
				case RTP:
					setContainer(String.valueOf(Container.SCHEMES.RTP).toLowerCase());
					break;
					
				case RTSP:
					setContainer(String.valueOf(Container.SCHEMES.RTSP).toLowerCase());
					break;
					
				default:
					if(this.streamName.toLowerCase().contains("m3u8"))
					setContainer(String.valueOf(Container.SCHEMES.SEGMENT).toLowerCase());
					break;
			
			}
			
		} 
		catch (URISyntaxException e) 
		{
			e.printStackTrace();
		}
	}

	public boolean isTemplate() 
	{
		return this.isTemplate;
	}

}
