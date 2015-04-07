package com.flashvisions.server.rtmp.transcoder.decorator;

import com.flashvisions.server.rtmp.transcoder.decorator.PropertyDecorator;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMutable;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderEntity;

public class MutableDecorator extends PropertyDecorator implements IMutable {

	private boolean enabled;
	
	public MutableDecorator(ITranscoderEntity property)
	{
		super(property);
	}

	@Override
	public boolean getEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		this.enabled = enabled;
	}
}
