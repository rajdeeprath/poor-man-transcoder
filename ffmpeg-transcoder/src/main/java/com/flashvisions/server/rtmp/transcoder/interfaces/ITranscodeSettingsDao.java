package com.flashvisions.server.rtmp.transcoder.interfaces;

import com.flashvisions.server.rtmp.transcoder.exception.TranscodeConfigurationException;

public interface ITranscodeSettingsDao  {
	
	public String getTemplate();
	public void setTemplate(String template);	
	public Object getTranscodeSession();
	
	public void load() throws TranscodeConfigurationException;
}
