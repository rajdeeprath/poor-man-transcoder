package com.flashvisions.server.rtmp.transcoder.data.factory;

import java.util.HashMap;
import java.util.Map;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscode;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfigDao;


public class TranscodeConfigurationFactory {

	private static TranscodeConfigurationFactory instance; 
	private AbstractDAOFactory daoFactory;
	private static Map<String, ITranscode> prototypes = null;;
	
	public TranscodeConfigurationFactory(SingletonEnforcer e){
		prototypes = new HashMap<String, ITranscode>();
	}
	
	public AbstractDAOFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(AbstractDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public ITranscode getTranscodeSession(String templateName)
	{
		ITranscode config;
		
		if(prototypes.containsKey(templateName))
		{
			config = prototypes.get(templateName);
			return config.clone();
		}
		else
		{
			ITranscodeConfigDao dao = this.daoFactory.getTranscodeDao(templateName);
			config = dao.getTranscodeConfig();
			prototypes.put(templateName, config);
			return config;
		}		
	}

	/** @throws IllegalAccessException ******/ 
	public static TranscodeConfigurationFactory getInstance()
	{
		if(instance == null) instance = new TranscodeConfigurationFactory(new SingletonEnforcer());
		return instance;
	}
}

class SingletonEnforcer{}