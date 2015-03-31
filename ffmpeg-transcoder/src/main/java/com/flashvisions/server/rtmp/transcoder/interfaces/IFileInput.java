package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.io.File;

public interface IFileInput extends IMediaInput {
	public File getFile();
	public void setFile(File input);
}
