package com.flashvisions.server.rtmp.transcoder.decorator;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.exception.InvalidTranscoderResourceException;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMedia;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.interfaces.InterpretStrategy;

public class SimpleTranscoderResource extends TranscoderResource implements ITranscoderResource{
	
	public SimpleTranscoderResource(IMedia media) throws InvalidTranscoderResourceException{
		super(media);
	}
	
	public SimpleTranscoderResource(IMedia media, ArrayList<IProperty> flags) throws InvalidTranscoderResourceException{
		super(media, flags);
	}
	
	public SimpleTranscoderResource(IMedia media, InterpretStrategy strategy) throws InvalidTranscoderResourceException{
		super(media, strategy);
	}
	
	public SimpleTranscoderResource(IMedia media, InterpretStrategy strategy, ArrayList<IProperty> flags) throws InvalidTranscoderResourceException{
		super(media, strategy, flags);
	}

}
