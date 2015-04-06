package com.flashvisions.server.rtmp.transcoder.pojo.base;

import com.flashvisions.server.rtmp.transcoder.interfaces.IPassThruProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderProperty;

public abstract class PassThruProperty extends TranscoderProperty implements
		IPassThruProperty, ITranscoderProperty {

	private boolean sameAsSource;
	
	@Override
	public void setSameAsSource(boolean sameAsSource) {
		// TODO Auto-generated method stub
		this.sameAsSource = sameAsSource;
	}

	@Override
	public boolean getSameAsSource() {
		// TODO Auto-generated method stub
		return sameAsSource;
	}

}
