package com.flashvisions.server.rtmp.transcoder.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.command.chain.TranscoderBootStrap;
import com.flashvisions.server.rtmp.transcoder.context.TranscodeRequest;
import com.flashvisions.server.rtmp.transcoder.context.TranscoderContext;
import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;
import com.flashvisions.server.rtmp.transcoder.generic.command.AbortTranscodeCommand;
import com.flashvisions.server.rtmp.transcoder.generic.command.DoTranscodeCommand;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderFacade;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;

public final class GenericTranscoderFacade implements ITranscoderFacade {

	private static Logger logger = LoggerFactory.getLogger(GenericTranscoderFacade.class);
	
	private static TranscoderContext context;
	private static volatile ITranscoderFacade instance;
	private static boolean bootstrap = false;
	
	private GenericTranscoderFacade(){
		context = new TranscoderContext();
	}	
	
	public static ITranscoderFacade getInstance()
	{
		if(instance == null)
		{
			synchronized (GenericTranscoderFacade.class){
				if(instance == null){
					instance = new GenericTranscoderFacade();
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
	public void doTranscode(ITranscoderResource input, TranscodeRequest request) throws TranscoderException {
		
		try 
		{
			new DoTranscodeCommand(input, request).execute(context);
		} 
		catch (Exception e) 
		{
			logger.error("doTranscode ->"+e.getMessage());
		}
	}
	
	@Override
	public void abortTranscode()
	{
		try 
		{
			new AbortTranscodeCommand().execute(context);
		} 
		catch (Exception e) 
		{
			logger.error(e.getMessage());
		}
	}
}