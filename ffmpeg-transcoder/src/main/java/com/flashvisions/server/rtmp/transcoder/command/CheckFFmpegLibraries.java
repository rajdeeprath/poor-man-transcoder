package com.flashvisions.server.rtmp.transcoder.command;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.context.TranscoderContext;
import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;
import com.flashvisions.server.rtmp.transcoder.utils.TranscoderUtils;

public class CheckFFmpegLibraries implements Command {

	private static Logger logger = LoggerFactory.getLogger(CheckFFmpegLibraries.class);
	
	@Override
	public boolean execute(Context context) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Check FFmpeg Libraries");
		
		long timeout = 2000; // 2sec
		CommandLine commandline;
		PumpStreamHandler streamHandler;
		ByteArrayOutputStream stdout;
		DefaultExecutor exec;
		
		try
		{
			TranscoderContext ctx = (TranscoderContext) context;
			
			commandline = CommandLine.parse(ctx.getFFmpegPath());
		    commandline.addArgument("-version");
			
		    stdout = new ByteArrayOutputStream();
		    streamHandler = new PumpStreamHandler(stdout);
		    
		    exec = new DefaultExecutor();
		    exec.setWatchdog(new ExecuteWatchdog(timeout));
		    exec.setStreamHandler(streamHandler);
		    exec.execute(commandline);
		    String capturedOutput = stdout.toString();
		    
		    
		    /************* get all libraries *************/
		    List<String> libraries = TranscoderUtils.captureLibraries(capturedOutput);
		    ctx.setSupportedLibraries(libraries);
		    
		    
		   /************* get version *************/
		   String version = TranscoderUtils.captureVersion(capturedOutput);
		   logger.info("FFmpeg version " + version);
		   ctx.setFfmpegVersion(version);
		   
		    
		}
		catch(Exception e)
		{
			logger.error("Fffmpeg check failed " + e.getMessage());
			throw new TranscoderException(e);
		}
		finally
		{
			commandline = null;
			stdout = null;
			streamHandler = null;
			exec = null;
		}
		
		
		return false;
	}

}
