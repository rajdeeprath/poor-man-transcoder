package com.flashvisions.server.rtmp.transcoder.command;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.context.TranscoderOutputContext;
import com.flashvisions.server.rtmp.transcoder.data.factory.LibRtmpConfigurationFactory;
import com.flashvisions.server.rtmp.transcoder.interfaces.ILibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Protocol;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Server;
import com.flashvisions.server.rtmp.transcoder.system.Globals;

public class PreSegmentOutputCommand implements Command {
	
	private static Logger logger = LoggerFactory.getLogger(PreSegmentOutputCommand.class);

	@Override
	public boolean execute(Context context) throws Exception {
		// TODO Auto-generated method stub
		TranscoderOutputContext ctx = (TranscoderOutputContext) context;
		
		ITranscoderResource input = ctx.getInput();
		ArrayList<ITranscoderResource> outputs = ctx.getOutputs();
		String workingDirectory = ctx.getWorkingDirectory();
		String inName = input.getMediaName();
		
		// create master working directory if not exists
		File dir = new File(workingDirectory);
		if(!dir.exists()) dir.mkdirs();
		
		switch(Protocol.valueOf(input.getProtocol().toUpperCase()))
		{
			case RTSP:
			case RTP:
			// TO DO
			logger.info("create application name folder");	
			break;
		
			case RTMP:
			case RTMPS:
			case RTMPT:
				
			ILibRtmpConfig rtmpInterpretor = LibRtmpConfigurationFactory.getLibRtmpConfiguration(Server.valueOf(Globals.getEnv(Globals.Vars.OPERATING_SERVER).toUpperCase()));
			rtmpInterpretor.prepareFrom(input);
			String appName = rtmpInterpretor.getAppName();
			
			File appScopeDir = new File(dir.getAbsolutePath() + File.separator + appName);
			if(!appScopeDir.exists()) appScopeDir.mkdir();
			dir = appScopeDir;
			
			break;
				
			default:
			break;			  
		}
		
		
		// create sub directories as required
		for(ITranscoderResource output : outputs)
		{
			String outName = output.getMediaName();
			String outNameWithOutExt = FilenameUtils.removeExtension(outName);
			
			File sub = new File(dir.getAbsolutePath() + File.separator + outNameWithOutExt);
			if(!sub.exists()) sub.mkdir();
		}
		
		return false;
	}

}
