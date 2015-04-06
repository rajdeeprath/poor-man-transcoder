package com.flashvisions.server.rtmp.transcoder.facade;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;
import com.flashvisions.server.rtmp.transcoder.interfaces.ILibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscode;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderFacade;
import com.flashvisions.server.rtmp.transcoder.pool.TranscodeSessionPool;
import com.flashvisions.server.rtmp.transcoder.system.Globals;
import com.flashvisions.server.rtmp.transcoder.system.Server;
import com.flashvisions.server.rtmp.transcoder.utils.TranscoderUtils;

public final class TranscoderFacade implements ITranscoderFacade {

	private static Logger logger = LoggerFactory.getLogger(TranscoderFacade.class);
	
	private String ffmpegPath;
	private String homeDirectoryPath;
	private String workingDirectoryPath;
	private String templateDirectoryPath;
	private String server;
	
	private TranscodeSessionPool pool;
	private static volatile ITranscoderFacade instance;
	
	
	private TranscoderFacade(){
		
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
	public void init() throws TranscoderException {
		// TODO Auto-generated method stub
		try
		{
			if(ffmpegPath.equals(null))
			throw new IOException("Please specify ffmpeg executable path");
			logger.info("FFmpeg path : " + ffmpegPath);
			
			File home = new File(homeDirectoryPath);
			if(!home.exists()) throw new IOException("Please specify media server home");
			logger.info("Home directory : " + home.getAbsolutePath());
			
			
			File templatesHome = new File(templateDirectoryPath);
			if(!templatesHome.exists()) throw new IOException("Please specify transcoder templates directory");
			logger.info("Template directory : " + templatesHome.getAbsolutePath());
			
			
			File working = new File(workingDirectoryPath);
			if(!working.exists()){
				workingDirectoryPath = home.getAbsolutePath();
				working  = new File(workingDirectoryPath);
			}
			logger.info("Working directory : " + working.getAbsolutePath());
			
			
			if(!TranscoderUtils.isValidMediaServer(server)) throw new IllegalArgumentException("Invlaid server type");
			logger.info("Target server platform : " + server);
			
			logger.info("Preparing session pool");
			pool = new TranscodeSessionPool(Server.valueOf(server.toUpperCase()));
			
			logger.info("Transcoder ready");
		}
		catch(Exception e)
		{
			logger.error("Transcoder initialization failed " + e.getMessage());
			throw new TranscoderException(e);
		}
	}

	@Override
	public void setFFmpegPath(String ffmpegPath) {
		// TODO Auto-generated method stub
		this.ffmpegPath = ffmpegPath;
		Globals.addEnv(Globals.Vars.FFMPEG_EXECUTABLE_PATH, this.ffmpegPath);
	}

	@Override
	public String getFFmpegPath() {
		// TODO Auto-generated method stub
		return ffmpegPath;
	}

	@Override
	public void setOperatingMediaServer(String serverName) {
		// TODO Auto-generated method stub
		this.server = serverName;
		Globals.addEnv(Globals.Vars.OPERATING_SERVER, this.server);
	}

	@Override
	public String getOperatingMediaServer() {
		// TODO Auto-generated method stub
		return server;
	}

	@Override
	public void setWorkingDirectory(String workingDirectoryPath) {
		// TODO Auto-generated method stub
		this.workingDirectoryPath = workingDirectoryPath;
		Globals.addEnv(Globals.Vars.WORKING_DIRECTORY, this.workingDirectoryPath);
	}

	@Override
	public String getWorkingDirectory() {
		// TODO Auto-generated method stub
		return workingDirectoryPath;
	}
	
	@Override
	public void setTemplateDirectory(String templateDirectoryPath) {
		// TODO Auto-generated method stub
		this.templateDirectoryPath = templateDirectoryPath;
		Globals.addEnv(Globals.Vars.TEMPLATE_DIRECTORY, this.templateDirectoryPath);
	}


	@Override
	public String getTemplateDirectory() {
		// TODO Auto-generated method stub
		return templateDirectoryPath;
	}

	@Override
	public void setHomeDirectory(String homeDirectoryPath) {
		// TODO Auto-generated method stub
		this.homeDirectoryPath = homeDirectoryPath;
		Globals.addEnv(Globals.Vars.HOME_DIRECTORY, this.homeDirectoryPath);
	}

	@Override
	public String getHomeDirectory() {
		// TODO Auto-generated method stub
		return homeDirectoryPath;
	}

	@Override
	public Object doTranscode(IMediaInput input, String usingTemplate) throws TranscoderException {
		// TODO Auto-generated method stub
		ISession session = pool.checkOut(input, usingTemplate);
		session.start();
		
		return null;
	}

	@Override
	public Object doTranscode(IMediaInput input, String usingTemplate, ILibRtmpConfig librtmpConfig) throws TranscoderException {
		// TODO Auto-generated method stub
		ISession session = pool.checkOut(input, usingTemplate);
		session.start();
		
		return session.getSignature();
	}

	@Override
	public Object doTranscode(IMediaInput input, ITranscode transcode, ILibRtmpConfig librtmpConfig) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void abortTranscode(long sessionId) {
		// TODO Auto-generated method stub
	}

	@Override
	public void abortTranscode(String sessionSignature) {
		// TODO Auto-generated method stub
		pool.checkIn(pool.getSession(sessionSignature));
	}


	
}