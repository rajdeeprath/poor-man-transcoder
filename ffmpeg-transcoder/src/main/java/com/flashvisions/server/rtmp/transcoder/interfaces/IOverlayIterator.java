package com.flashvisions.server.rtmp.transcoder.interfaces;


public interface IOverlayIterator {
	public boolean hasNext();    
    public IOverlay next();
}
