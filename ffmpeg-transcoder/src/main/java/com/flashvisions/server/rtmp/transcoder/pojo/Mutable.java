package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.interfaces.IMutable;

public class Mutable implements IMutable 
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