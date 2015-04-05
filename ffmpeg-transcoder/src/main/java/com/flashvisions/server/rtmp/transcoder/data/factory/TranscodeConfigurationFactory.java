package com.flashvisions.server.rtmp.transcoder.data.factory;

import java.util.HashMap;
import java.util.Map;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfigDao;


public class TranscodeConfigurationFactory {

	private static TranscodeConfigurationFactory instance; 
	private AbstractDAOFactory daoFactory;
	private static Map<String, ITranscodeConfig> prototypes = null;;
	
	public TranscodeConfigurationFactory(SingletonEnforcer e){
		prototypes = new HashMap<String, ITranscodeConfig>();
	}
	
	public AbstractDAOFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoSupplier(AbstractDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public ITranscodeConfig getTranscodeConfiguration(String template)
	{
		ITranscodeConfig config;
		
		if(prototypes.containsKey(template))
		{
			config = prototypes.get(template);
			return (ITranscodeConfig) config.clone();
		}
		else
		{
			ITranscodeConfigDao dao = this.daoFactory.getTranscodeDao(template);
			config = dao.getTranscodeConfig();
			prototypes.put(template, config);
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