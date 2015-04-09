package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IParameter extends ITranscoderEntity {
	public String getKey();
	public void setKey(String key);
	public String getValue();
	public void setValue(String value) ;
	public IParameter clone();
}
