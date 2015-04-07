package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.List;

public interface IEncodeCollection {
	public void addEncode(IEncode e);
	public void removeEncode(IEncode c);
	public int getTotalEncodes();
	public void clear();
	
    public IEncodeIterator iterator();
	public List<IEncode> getEncodeList();
}
