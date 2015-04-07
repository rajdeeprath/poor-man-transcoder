package com.flashvisions.server.rtmp.transcoder.decorator;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderEntity;

public abstract class PropertyDecorator implements ITranscoderEntity {
 
	protected ITranscoderEntity property;
	
	public PropertyDecorator(ITranscoderEntity property){
		this.property = property;
	}

}
