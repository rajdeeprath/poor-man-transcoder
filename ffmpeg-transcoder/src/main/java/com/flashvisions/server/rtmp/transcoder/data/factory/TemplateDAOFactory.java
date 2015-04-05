package com.flashvisions.server.rtmp.transcoder.data.factory;

import java.io.File;

import com.flashvisions.server.rtmp.transcoder.data.dao.TemplateDao;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfigDao;
import com.flashvisions.server.rtmp.transcoder.system.Globals;


public class TemplateDAOFactory extends AbstractDAOFactory 
{
	@Override
	public ITranscodeConfigDao getTranscodeDao(String filename) {
		// TODO Auto-generated method stub
		return new TemplateDao(Globals.getEnv(Globals.Vars.TEMPLATE_DIRECTORY) + File.separator + filename);
	}
	
	
	/***** Important!! Not to be used with prototype pattern ******/
	@Override
	public ITranscodeConfigDao getTranscodeDao(String filename, boolean lazyLoad) {
		// TODO Auto-generated method stub
		return new TemplateDao((Globals.getEnv(Globals.Vars.TEMPLATE_DIRECTORY) + File.separator + filename), lazyLoad);
	}	
	
}