package com.flashvisions.server.rtmp.transcoder.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.command.chain.TranscoderBootStrap;
import com.flashvisions.server.rtmp.transcoder.context.TranscoderContext;
import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderFacade;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pool.TranscodeSessionPool;

public final class TranscoderFacade implements ITranscoderFacade {

	private static Logger logger = LoggerFactory.getLogger(TranscoderFacade.class);
	
	private static TranscoderContext context;
	private static volatile ITranscoderFacade instance;
	private static boolean bootstrap = false;
	
	private TranscoderFacade(){
		context = new TranscoderContext();
	}	
	
	public static ITranscoderFacade getInstance()
	{
		if(instance == null)
		{
			synchronized (TranscoderFacade.class){
				if(instance == null){
					instance = new TranscoderFacade();
				}
			}
		}
		
		return instance;
	}
	

	@Override
	public synchronized void init() throws TranscoderException {
		// TODO Auto-generated method stub		
		try
		{
			if(!bootstrap)
			new TranscoderBootStrap().execute(context);
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
		}
		finally
		{
			bootstrap = true;
		}
	}

	@Override
	public void setFFmpegPath(String ffmpegPath) {
		// TODO Auto-generated method stub
		context.setFFmpegPath(ffmpegPath);
	}

	@Override
	public  String getFFmpegPath() {
		// TODO Auto-generated method stub
		return context.getFFmpegPath();
	}

	@Override
	public void setOperatingMediaServer(String serverName) {
		// TODO Auto-generated method stub
		context.setOperatingMediaServer(serverName);
	}

	@Override
	public String getOperatingMediaServer() {
		// TODO Auto-generated method stub
		return context.getOperatingMediaServer();
	}

	@Override
	public void setWorkingDirectory(String workingDirectoryPath) {
		// TODO Auto-generated method stub
		context.setWorkingDirectory(workingDirectoryPath);
	}

	@Override
	public  String getWorkingDirectory() {
		// TODO Auto-generated method stub
		return context.getWorkingDirectory();
	}
	
	@Override
	public void setTemplateDirectory(String templateDirectoryPath) {
		// TODO Auto-generated method stub
		context.setTemplateDirectory(templateDirectoryPath);
	}

	@Override
	public String getTemplateDirectory() {
		// TODO Auto-generated method stub
		return context.getTemplateDirectory();
	}

	@Override
	public void setHomeDirectory(String homeDirectoryPath) {
		// TODO Auto-generated method stub
		context.setHomeDirectory(homeDirectoryPath);
	}

	@Override
	public String getHomeDirectory() {
		// TODO Auto-generated method stub
		return context.getHomeDirectory();
	}

	
	/*********************************************************************************************/
	
	@Override
	public Object doTranscode(ITranscoderResource input, String usingTemplate) throws TranscoderException {
		
		// TODO Auto-generated method stub
		TranscodeSessionPool pool = context.getPool();
		ISession session = pool.checkOut(input, usingTemplate);
		session.start();
		
		return null;
	}
}