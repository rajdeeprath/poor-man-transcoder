package com.flashvisions.server.rtmp.transcoder.data.factory;

import java.util.HashMap;
import java.util.Map;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscode;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeDao;


/**
 * @author Rajdeep
 * 
 * Factory responsible for creating and dispatching
 * transcode configuration object (ITranscode type).
 * 
 * Uses cache to avoid reloading of template data
 * for a given template.
 */
public class TranscodeConfigurationFactory {

	private static TranscodeConfigurationFactory instance; 
	private AbstractDAOFactory daoFactory;
	private static Map<String, ITranscode> cache = null;
	
	
	private TranscodeConfigurationFactory(){
		cache = new HashMap<String, ITranscode>();
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
		
		try
		{	
			if(!cache.containsKey(template))
			throw new Exception("Configuration found not in cache");
			
			config = cache.get(template);
			
			if(config == null)
			throw new Exception("Configuration is null");
			
		}
		catch(Exception e)
		{
			config = buildTranscodeConfiguration(template);
			cache.put(template, config);
		}
		
		return config;
	}
	
	private ITranscode buildTranscodeConfiguration(String template)
	{
		ITranscodeDao dao = this.daoFactory.getTranscodeDao(template);
		ITranscode config = dao.getTranscodeConfig();
		return config;
	}
	
	/**
	 * @author Rajdeep
	 * 
	 * singleton access method for this factory
	 */
	public static TranscodeConfigurationFactory getInstance()
	{
		if(instance == null) instance = new TranscodeConfigurationFactory();
		return instance;
	}
}