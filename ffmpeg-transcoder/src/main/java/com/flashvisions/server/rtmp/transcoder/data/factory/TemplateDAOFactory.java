package com.flashvisions.server.rtmp.transcoder.data.factory;

import java.io.File;
import com.flashvisions.server.rtmp.transcoder.data.dao.TemplateDao;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfigDao;


public class TemplateDAOFactory extends AbstractDAOFactory 
{
	private String baseDir;
	
	
	@Override
	public ITranscodeConfigDao getTranscodeDao(String filename) {
		// TODO Auto-generated method stub
		
		if(baseDir != null)
		return new TemplateDao(baseDir + File.separator + filename);
		else
		return new TemplateDao(filename);
	}
	
	
	/***** Important!! Not to be used with prototype pattern ******/
	@Override
	public ITranscodeConfigDao getTranscodeDao(String filename, boolean lazyLoad) {
		// TODO Auto-generated method stub
		if(baseDir != null)
		return new TemplateDao(baseDir + File.separator + filename, lazyLoad);
		else
		return new TemplateDao(filename, lazyLoad);
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