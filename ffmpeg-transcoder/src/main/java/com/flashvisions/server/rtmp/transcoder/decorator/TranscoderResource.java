package com.flashvisions.server.rtmp.transcoder.decorator;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.exception.InvalidTranscoderResourceException;
import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMedia;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.impl.DefaultInterpretStrategy;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.interfaces.InterpretStrategy;

public abstract class TranscoderResource implements IMedia,ITranscoderResource {

	private ArrayList<IProperty> flags;
	private InterpretStrategy strategy;
	private IMedia media;
	
	
	public TranscoderResource(IMedia media) throws InvalidTranscoderResourceException{
		this.media = media;
		this.parseMedia(media);
		this.strategy = new DefaultInterpretStrategy();
	}
	
	public TranscoderResource(IMedia media, ArrayList<IProperty> flags) throws InvalidTranscoderResourceException{
		this.media = media;
		this.parseMedia(media);
		this.setOptionFlags(flags);
		this.strategy = new DefaultInterpretStrategy();
	}
	
	public TranscoderResource(IMedia media, InterpretStrategy strategy) throws InvalidTranscoderResourceException{
		this.media = media;
		this.parseMedia(media);
		
		this.setStrategy(strategy);
	}
	
	public TranscoderResource(IMedia media, InterpretStrategy strategy, ArrayList<IProperty> flags) throws InvalidTranscoderResourceException{
		this.media = media;
		this.parseMedia(media);
		this.setOptionFlags(flags);		
		this.setStrategy(strategy);
	}
	
	
	protected void parseMedia(IMedia media) throws InvalidTranscoderResourceException
	{
		try
		{
			URI parser = URI.create(media.getSourcePath());
			String protocol = parser.getScheme();
			String path = parser.getPath();
			String mediaName = (media.isStreamingMedia())?path.substring(path.lastIndexOf("/")+1):path.substring(path.lastIndexOf(File.separator)+1);
			this.setProtocol(protocol);
			this.setMediaName(mediaName);
		}
		catch(Exception e)
		{
			throw new InvalidTranscoderResourceException(e);
		}
	}
	
	@Override
	public IContainer getContainer() {
		// TODO Auto-generated method stub
		return this.media.getContainer();
	}

	@Override
	public void setContainer(IContainer container) {
		// TODO Auto-generated method stub
		this.media.setContainer(container);
	}

	@Override
	public String getSourcePath() {
		// TODO Auto-generated method stub
		return this.media.getSourcePath();
	}

	@Override
	public void setSourcePath(String source) {
		// TODO Auto-generated method stub
		this.media.setSourcePath(source);
	}

	@Override
	public void setProtocol(String protocol) {
		// TODO Auto-generated method stub
		this.media.setProtocol(protocol);
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return this.media.getProtocol();
	}

	@Override
	public boolean isFile() {
		// TODO Auto-generated method stub
		return this.media.isFile();
	}

	@Override
	public boolean isStreamingMedia() {
		// TODO Auto-generated method stub
		return this.media.isStreamingMedia();
	}

	@Override
	public String getMediaName() {
		// TODO Auto-generated method stub
		return this.media.getMediaName();
	}

	@Override
	public void setMediaName(String streamName) {
		// TODO Auto-generated method stub
		this.media.setMediaName(streamName);
	}

	@Override
	public InterpretStrategy getStrategy() {
		return strategy;
	}

	@Override
	public void setStrategy(InterpretStrategy strategy) {
		this.strategy = strategy;
	}
	
	@Override
	public ArrayList<IProperty> getOptionFlags() {
		// TODO Auto-generated method stub
		return flags;
	}

	@Override
	public void setOptionFlags(ArrayList<IProperty> flags) {
		// TODO Auto-generated method stub
		this.flags = flags;
	}

	@Override
	public String describe()
	{
		return this.strategy.interpret(this);
	}
}
