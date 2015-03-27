package com.flashvisions.server.rtmp.transcoder.data.factory;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeSettingsDao;

public abstract class TranscoderDAOFactory {

	public static final int FROOM_XML_TEMPLATE = 1;
	
	public abstract ITranscodeSettingsDao getTranscodeDao(String filename);
	public abstract ITranscodeSettingsDao getTranscodeDao(String filename, boolean lazyLoad);
	
	public abstract Object getTemplateBasePath();
	public abstract void setTemplateBasePath(String baseDir);
	
	public static TranscoderDAOFactory getDAOFactory(int type)
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
