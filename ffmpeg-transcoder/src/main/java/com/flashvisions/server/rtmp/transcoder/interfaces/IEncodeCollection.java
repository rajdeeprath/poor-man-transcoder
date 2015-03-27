package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IEncodeCollection {
	public void addEncode(IEncode e);
	public void removeEncode(IEncode c);
	public void clear();
	
    public IEncodeIterator iterator();
}
