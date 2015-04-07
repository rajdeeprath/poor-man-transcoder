package com.flashvisions.server.rtmp.transcoder.data.factory;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeDao;

public abstract class AbstractDAOFactory {

	public static final int FROM_XML_TEMPLATE = 1;
	
	public abstract ITranscodeDao getTranscodeDao(String filename);
	
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