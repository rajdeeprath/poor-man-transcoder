package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

public interface IMediaInput extends IMedia {
	public ArrayList<IProperty> getReadFlags();
	public void setReadFlags(ArrayList<IProperty> readFlags);
}
