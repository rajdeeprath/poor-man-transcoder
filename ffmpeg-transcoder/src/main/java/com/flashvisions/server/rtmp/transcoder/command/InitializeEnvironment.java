package com.flashvisions.server.rtmp.transcoder.command;

import java.io.File;
import java.io.IOException;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.context.TranscoderContext;
import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;
import com.flashvisions.server.rtmp.transcoder.system.Globals;
import com.flashvisions.server.rtmp.transcoder.utils.TranscoderUtils;

public class InitializeEnvironment implements Command {

	private static Logger logger = LoggerFactory.getLogger(InitializeEnvironment.class);
	
	@Override
	public boolean execute(Context context) throws Exception {
		
		logger.info("Initialize Environment");
		
		try
		{
			TranscoderContext ctx = (TranscoderContext) context;
			
			if(ctx.getFFmpegPath() == null)
			throw new IOException("Please specify ffmpeg executable path");
			Globals.addEnv(Globals.Vars.FFMPEG_EXECUTABLE_PATH, ctx.getFFmpegPath());
			logger.info("Ffmpeg binary " + Globals.getEnv(Globals.Vars.FFMPEG_EXECUTABLE_PATH));
			
			if(ctx.getHomeDirectory() == null) 
			throw new IOException("Media server home directory not specified");
			File home = new File(ctx.getHomeDirectory());
			if(!home.exists()) throw new IOException("Invalid server home directory. Does not exist!");
			Globals.addEnv(Globals.Vars.HOME_DIRECTORY, ctx.getHomeDirectory());
			logger.info("Home directory " + ctx.getHomeDirectory());
				
			
			if(ctx.getTemplateDirectory() == null) 
			throw new IOException("Templates directory not specified");
			File templatesHome = new File(ctx.getTemplateDirectory());
			if(!templatesHome.exists()) throw new IOException("Invalid templates directory. Does not exist!");
			Globals.addEnv(Globals.Vars.TEMPLATE_DIRECTORY, ctx.getTemplateDirectory());
			logger.info("Templates directory " + ctx.getWorkingDirectory());
			
			if(ctx.getWorkingDirectory() == null)
			throw new IOException("Global working directory not specified");
			File working = new File(ctx.getWorkingDirectory());
			if(!working.exists()) throw new IOException("Invalid working directory. Does nto exist!");
			Globals.addEnv(Globals.Vars.WORKING_DIRECTORY, ctx.getWorkingDirectory());
			logger.info("Working directory " + ctx.getWorkingDirectory());
			
			
			if(ctx.getOperatingMediaServer().equalsIgnoreCase(null) || ctx.getOperatingMediaServer() == null || !TranscoderUtils.isValidMediaServer(ctx.getOperatingMediaServer())) 
			throw new IllegalArgumentException("Invalid media server type");
			Globals.addEnv(Globals.Vars.OPERATING_SERVER, ctx.getOperatingMediaServer());
			logger.info("Media server " + ctx.getOperatingMediaServer());
		}
		catch(Exception e)
		{
			logger.error("Transcoder initialization failed " + e.getMessage());
			throw new TranscoderException(e);
		}
		
		// TODO Auto-generated method stub
		return false;
	}

}
