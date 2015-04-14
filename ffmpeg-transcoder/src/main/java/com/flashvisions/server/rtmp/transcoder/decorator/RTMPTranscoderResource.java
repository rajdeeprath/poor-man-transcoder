package com.flashvisions.server.rtmp.transcoder.decorator;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.exception.InvalidTranscoderResourceException;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMedia;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.interfaces.InterpretStrategy;

public class RTMPTranscoderResource extends TranscoderResource implements ITranscoderResource{
	
	public RTMPTranscoderResource(IMedia media) throws InvalidTranscoderResourceException{
		super(media);
	}
	
	public RTMPTranscoderResource(IMedia media, ArrayList<IProperty> flags) throws InvalidTranscoderResourceException{
		super(media, flags);
	}
	
	public RTMPTranscoderResource(IMedia media, InterpretStrategy strategy) throws InvalidTranscoderResourceException{
		super(media, strategy);
	}
	
	public RTMPTranscoderResource(IMedia media, InterpretStrategy strategy, ArrayList<IProperty> flags) throws InvalidTranscoderResourceException{
		super(media, strategy, flags);
	}

}
