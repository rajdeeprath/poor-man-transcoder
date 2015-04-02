package com.flashvisions.server.rtmp.transcoder.interfaces;

import com.flashvisions.server.rtmp.transcoder.pojo.AudioChannel.ChannelType;

public interface IAudioChannel extends IPassThru {

	public ChannelType getChannelType();
	public void setChannelType(ChannelType type)throws IllegalAccessException;
	public int getChannels();
}
