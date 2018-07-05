package com.flashvisions.server.rtmp.transcoder.decorator;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.exception.InvalidTranscoderResourceException;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMedia;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.impl.RTMPInterpretStrategy;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.impl.RTSPInterpretStrategy;
import com.flashvisions.server.rtmp.transcoder.pojo.io.strategy.interfaces.InterpretStrategy;

public class RTSPTranscoderResource extends TranscoderResource implements ITranscoderResource{
	
	public RTSPTranscoderResource(IMedia media) throws InvalidTranscoderResourceException{
		super(media);
		setStrategy(new RTSPInterpretStrategy());
	}
	
	public RTSPTranscoderResource(IMedia media, ArrayList<IProperty> flags) throws InvalidTranscoderResourceException{
		super(media, flags);
		setStrategy(new RTSPInterpretStrategy());
	}
	
	public RTSPTranscoderResource(IMedia media, InterpretStrategy strategy) throws InvalidTranscoderResourceException{
		super(media, strategy);
	}
	
	public RTSPTranscoderResource(IMedia media, InterpretStrategy strategy, ArrayList<IProperty> flags) throws InvalidTranscoderResourceException{
		super(media, strategy, flags);
	}

}
