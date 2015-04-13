package com.flashvisions.server.rtmp.transcoder.decorator;

import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMedia;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.impl.DefaultInterpretStrategy;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.interfaces.InterpretStrategy;

public abstract class TranscoderOutputResource implements IMedia,ITranscoderResource {

	private InterpretStrategy strategy;
	private IMedia media;
	
	
	public TranscoderOutputResource(IMedia media){
		this.media = media;
		this.setStrategy(new DefaultInterpretStrategy());
	}
	
	public TranscoderOutputResource(IMedia media, InterpretStrategy strategy){
		this.media = media;
		this.setStrategy(strategy);
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
	public String describe()
	{
		return this.strategy.interpret(this);
	}
}
