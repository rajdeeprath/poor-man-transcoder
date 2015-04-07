package com.flashvisions.server.rtmp.transcoder.pojo.base;

import com.flashvisions.server.rtmp.transcoder.interfaces.IMutableObject;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderEntity;

public abstract class MutableObject extends TranscoderEntity implements IMutableObject, ITranscoderEntity {

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
