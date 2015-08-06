package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IPassThru {
	public void setSameAsSource(boolean same);
	public boolean getSameAsSource();
	
	public void setIgnore(boolean ignore);
	public boolean getIgnore();
}
