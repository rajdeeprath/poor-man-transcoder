package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface IEncodeIterator {
	public boolean hasNext();    
    public IEncode next();
}
