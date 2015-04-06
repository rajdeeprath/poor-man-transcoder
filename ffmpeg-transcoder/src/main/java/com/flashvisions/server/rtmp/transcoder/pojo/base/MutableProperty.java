package com.flashvisions.server.rtmp.transcoder.pojo.base;

import com.flashvisions.server.rtmp.transcoder.interfaces.IMutableProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderProperty;

public abstract class MutableProperty extends TranscoderProperty implements IMutableProperty, ITranscoderProperty {

	private boolean enabled;
	
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
