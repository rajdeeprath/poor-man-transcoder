package com.flashvisions.server.rtmp.transcoder.decorator;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderProperty;

public abstract class PropertyDecorator implements ITranscoderProperty {
 
	protected ITranscoderProperty property;
	
	public PropertyDecorator(ITranscoderProperty property){
		this.property = property;
	}
	
	@Override
	public String aboutMe() {
		// TODO Auto-generated method stub
		return this.property.aboutMe();
	}
}
