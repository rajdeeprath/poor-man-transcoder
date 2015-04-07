package com.flashvisions.server.rtmp.transcoder.pojo.base;

import com.flashvisions.server.rtmp.transcoder.interfaces.IMutable;

public abstract class Mutable extends TranscoderEntity implements IMutable 
{
	private boolean enabled;
	
	public boolean getEnabled()
	{
		return enabled;
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

}
