package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IAudioChannel extends IPassThru, IParameter {
	public Integer getChannels();
}
