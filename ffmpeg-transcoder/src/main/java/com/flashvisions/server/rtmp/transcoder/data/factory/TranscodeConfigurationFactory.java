package com.flashvisions.server.rtmp.transcoder.data.factory;

import java.util.HashMap;
import java.util.Map;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscode;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeDao;


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

	public void setDaoSupplier(AbstractDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public ITranscode getTranscodeConfiguration(String template)
	{
		ITranscode config;
		
		if(prototypes.containsKey(template))
		{
			config = prototypes.get(template);
			return (ITranscode) config.clone();
		}
		else
		{
			ITranscodeDao dao = this.daoFactory.getTranscodeDao(template);
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