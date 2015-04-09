package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IAudioChannel extends IPassThru {

	public String getChannelType();
	public void setChannelType(String type)throws IllegalAccessException;
	public int getChannels();
}
