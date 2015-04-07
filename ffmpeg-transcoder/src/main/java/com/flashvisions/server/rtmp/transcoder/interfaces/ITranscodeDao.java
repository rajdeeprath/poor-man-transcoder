package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface ITranscodeDao  {
	
	public String getTemplate();
	public void setTemplate(String templatePath);	
	public ITranscode getTranscodeConfig();
}
