package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface ITranscodeSettingsDao  {
	
	public String getTemplate();
	public void setTemplate(String templatePath);	
	public ITranscode getTranscodeConfig();
	public void readTemplate();
}
