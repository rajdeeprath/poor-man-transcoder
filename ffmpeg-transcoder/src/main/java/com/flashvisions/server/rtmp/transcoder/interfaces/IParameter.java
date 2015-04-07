package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IParameter extends ITranscoderEntity {
	public String getKey();
	public void setKey(String key);
	public Object getValue();
	public void setValue(Object value) ;
}
