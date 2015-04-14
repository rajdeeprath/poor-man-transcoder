package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.io.File;

public interface IFileMedia extends IMedia {
	public File getFile();
	public void setFile(File input);
}
