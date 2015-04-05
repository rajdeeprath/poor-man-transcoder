package com.flashvisions.server.rtmp.transcoder.data.factory;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfigDao;

public abstract class AbstractDAOFactory {

	public static final int FROM_XML_TEMPLATE = 1;
	
	public abstract ITranscodeConfigDao getTranscodeDao(String filename);
	public abstract ITranscodeConfigDao getTranscodeDao(String filename, boolean lazyLoad);
	
	/***** Using factory pattern to keep flexibility for future. This leaves flexibility to load template from database / file ******/ 
	public static AbstractDAOFactory getDAOFactory(int type)
	{
		switch(type)
		{
			case FROM_XML_TEMPLATE:
			return new TemplateDAOFactory();
			
			default:
			return new NullDAOFactory();
			
		}
	}
}