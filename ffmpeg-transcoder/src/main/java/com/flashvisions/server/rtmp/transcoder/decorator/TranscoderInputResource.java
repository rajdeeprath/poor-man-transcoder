package com.flashvisions.server.rtmp.transcoder.decorator;

import com.flashvisions.server.rtmp.transcoder.exception.InvalidTranscoderResourceException;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMedia;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.interfaces.InterpretStrategy;

public class TranscoderInputResource extends TranscoderResource implements ITranscoderResource{
	
	public TranscoderInputResource(IMedia media) throws InvalidTranscoderResourceException {
		super(media);
		// TODO Auto-generated constructor stub
	}

	public TranscoderInputResource(IMedia media, InterpretStrategy strategy) throws InvalidTranscoderResourceException {
		super(media, strategy);
		// TODO Auto-generated constructor stub
	}

}
