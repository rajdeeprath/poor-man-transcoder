package com.flashvisions.server.rtmp.transcoder.data.factory;

import com.flashvisions.server.rtmp.transcoder.data.dao.TemplateDao;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfigDao;


public class TemplateDAOFactory extends AbstractDAOFactory 
{
	@Override
	public ITranscodeConfigDao getTranscodeDao(String templateFilePath) {
		// TODO Auto-generated method stub
		return new TemplateDao(templateFilePath);
	}
	
	
	/***** Important!! Not to be used with prototype pattern ******/
	@Override
	public ITranscodeConfigDao getTranscodeDao(String filename, boolean lazyLoad) {
		// TODO Auto-generated method stub
		return new TemplateDao(filename, lazyLoad);
	}	
	
}