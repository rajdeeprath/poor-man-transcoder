package com.flashvisions.server.rtmp.transcoder.data.factory;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfigDao;

public abstract class AbstractDAOFactory {

	public static final int FROOM_XML_TEMPLATE = 1;
	
	public abstract ITranscodeConfigDao getTranscodeDao(String filename);
	public abstract ITranscodeConfigDao getTranscodeDao(String filename, boolean lazyLoad);
	
	public abstract Object getTemplateBasePath();
	public abstract void setTemplateBasePath(String baseDir);
	
	/***** Using factory pattern to keep flexibility for future. This leaves flexibility to load template from database / file ******/ 
	public static AbstractDAOFactory getDAOFactory(int type)
	{
		switch(type)
		{
			case FROOM_XML_TEMPLATE:
			return new TemplateDAOFactory();
			
			default:
			return new NullDAOFactory();
			
		}
	}
}