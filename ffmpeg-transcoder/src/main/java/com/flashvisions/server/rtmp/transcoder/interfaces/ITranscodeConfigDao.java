package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface ITranscodeConfigDao  {
	
	public String getTemplate();
	public void setTemplate(String templatePath);	
	public ITranscodeConfig getTranscodeConfig();
	public void readTemplate();
}
