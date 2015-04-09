package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

public interface IMediaOutput extends IMedia{
	public ArrayList<IProperty> getWriteFlags();
	public void setWriteFlags(ArrayList<IProperty> writeFlags);
}
