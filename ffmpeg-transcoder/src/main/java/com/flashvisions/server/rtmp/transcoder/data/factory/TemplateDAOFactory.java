package com.flashvisions.server.rtmp.transcoder.data.factory;

import java.io.File;

import com.flashvisions.server.rtmp.transcoder.data.dao.TemplateSettingsDao;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeSettingsDao;


public class TemplateDAOFactory extends TranscoderDAOFactory 
{
	private String baseDir;

	@Override
	public ITranscodeSettingsDao getTranscodeDao(String filename) {
		// TODO Auto-generated method stub
		if(baseDir != null)
		return new TemplateSettingsDao(baseDir + File.separator + filename);
		else
		return new TemplateSettingsDao(filename);	
	}
	
	@Override
	public ITranscodeSettingsDao getTranscodeDao(String filename, boolean lazyLoad) {
		// TODO Auto-generated method stub
		if(baseDir != null)
		return new TemplateSettingsDao(baseDir + File.separator + filename, lazyLoad);
		else
		return new TemplateSettingsDao(filename, lazyLoad);
	}

	@Override
	public Object getTemplateBasePath() {
		// TODO Auto-generated method stub
		return baseDir;
	}

	@Override
	public void setTemplateBasePath(String baseDir) {
		// TODO Auto-generated method stub
		this.baseDir = baseDir;
	}

	
	
}
