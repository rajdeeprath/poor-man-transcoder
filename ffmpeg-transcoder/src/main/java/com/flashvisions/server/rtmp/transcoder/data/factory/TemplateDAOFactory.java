package com.flashvisions.server.rtmp.transcoder.data.factory;

import java.io.File;

import com.flashvisions.server.rtmp.transcoder.data.dao.TemplateDao;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeDao;
import com.flashvisions.server.rtmp.transcoder.system.Globals;


public class TemplateDAOFactory extends AbstractDAOFactory 
{
	@Override
	public ITranscodeDao getTranscodeDao(String filename) {
		// TODO Auto-generated method stub
		return new TemplateDao(Globals.getEnv(Globals.Vars.TEMPLATE_DIRECTORY) + File.separator + filename);
	}	
	
}