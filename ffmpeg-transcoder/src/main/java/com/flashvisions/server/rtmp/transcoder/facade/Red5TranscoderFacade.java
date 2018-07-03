package com.flashvisions.server.rtmp.transcoder.facade;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.command.chain.TranscoderBootStrap;
import com.flashvisions.server.rtmp.transcoder.context.TranscoderContext;
import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderFacade;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Server;
import com.flashvisions.server.rtmp.transcoder.system.Globals;

public final class Red5TranscoderFacade implements ITranscoderFacade {

	private static Logger logger = LoggerFactory.getLogger(Red5TranscoderFacade.class);
	
	private static TranscoderContext context;
	private static boolean bootstrap = false;
	
	private String FFmpegPath;
	private String operatingMediaServer;
	private String workingDirectory;
	private String templateDirectory;
	private String homeDirectory;
	
	
	public Red5TranscoderFacade(){
		context = new TranscoderContext();
	}	
	

	@Override
	public synchronized void init() throws TranscoderException {
		// TODO Auto-generated method stub		
		try
		{
			if(!bootstrap){
				
				context.setFFmpegPath(this.FFmpegPath);
				context.setWorkingDirectory(this.workingDirectory);
				context.setOperatingMediaServer(Server.RED5.name().toLowerCase());
				context.setTemplateDirectory(this.templateDirectory);
				context.setHomeDirectory(this.homeDirectory);				
				new TranscoderBootStrap().execute(context);
			}
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
		this.FFmpegPath = ffmpegPath;
	}

	@Override
	public  String getFFmpegPath() {
		// TODO Auto-generated method stub
		return this.FFmpegPath;
	}

	@Override
	public void setOperatingMediaServer(String serverName) {
		this.operatingMediaServer = serverName;
	}

	@Override
	public String getOperatingMediaServer() {
		// TODO Auto-generated method stub
		return this.operatingMediaServer;
	}

	@Override
	public void setWorkingDirectory(String workingDirectoryPath) {
		// TODO Auto-generated method stub
		this.workingDirectory = workingDirectoryPath;
	}

	@Override
	public  String getWorkingDirectory() {
		return this.workingDirectory;
	}
	
	@Override
	public void setTemplateDirectory(String templateDirectoryPath) {
		this.templateDirectory = templateDirectoryPath;
	}

	@Override
	public String getTemplateDirectory() {
		return this.templateDirectory;
	}

	@Override
	public void setHomeDirectory(String homeDirectoryPath) {
		this.homeDirectory = homeDirectoryPath;
	}

	@Override
	public String getHomeDirectory() {
		// TODO Auto-generated method stub
		return this.homeDirectory;
	}
}