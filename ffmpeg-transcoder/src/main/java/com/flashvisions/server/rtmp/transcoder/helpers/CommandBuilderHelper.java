package com.flashvisions.server.rtmp.transcoder.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.data.factory.LibRtmpConfigurationFactory;
import com.flashvisions.server.rtmp.transcoder.ffmpeg.Flags;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioBitrate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioChannel;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodecImplementation;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameSize;
import com.flashvisions.server.rtmp.transcoder.interfaces.IKeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.interfaces.ILibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.CodecImplementations;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Protocol;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Server;
import com.flashvisions.server.rtmp.transcoder.system.Globals;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;

public class CommandBuilderHelper {

	private static Logger logger = LoggerFactory.getLogger(CommandBuilderHelper.class);
	
	
	public ITranscoderResource buildOutput(CommandLine cmdLine, ITranscoderResource input, ITranscodeOutput output, String workingDirectory) throws Exception
	{
		logger.info("Processing output destination for encode");
		
		ITranscoderResource template = output.getMediaOutput();
		ArrayList<IProperty> properties = output.getOutputProperties();
		ITranscoderResource transcoderOutput = IOUtils.createOutputFromInput(input, template);
		
		cmdLine.addArgument(Flags.OVERWRITE);
		cmdLine.addArgument(Flags.OUTPUT);
		cmdLine.addArgument(transcoderOutput.getContainer().toString());		
		
		
		/************* special pre-processing for hls output ******/
		String ownSegmentDirectory = "";
		switch(transcoderOutput.getContainer().getType())
		{
			case SSEGMENT:
			ownSegmentDirectory = getOwnSegmentDirectory(cmdLine, transcoderOutput, workingDirectory);
			break;
			
			default:
			cmdLine.addArgument(transcoderOutput.describe());
			break;
		}
		
		
		/***************** extra properties for output **************/		
		if(!properties.isEmpty())
		{
			Iterator<IProperty> it = properties.iterator();
			
			while(it.hasNext())	{
			String prop = it.next().getData();
			prop = prop.replace("${OwnSegmentDirectory}", ownSegmentDirectory);
			cmdLine.addArgument(prop, true);
			}
		}
		
		
		return transcoderOutput;
	}
	
	
	protected String getOwnSegmentDirectory(CommandLine cmdLine, ITranscoderResource output, String masterWorkingDirectory)
	{	
		// create sub directory for hls output
		String outName = output.getMediaName();
		String outNameWithOutExt = FilenameUtils.removeExtension(outName);
		
		File sub = new File(masterWorkingDirectory + File.separator + outNameWithOutExt);
		if(!sub.exists()) sub.mkdir();
		
		String relative = new File(masterWorkingDirectory).toURI().relativize(new File(sub.getAbsolutePath()).toURI()).getPath();
		logger.info("relative path of segment directory " + relative);
		
		return relative;
		
	}
	
	
	public String prepareWorkingDirectory(ITranscoderResource input, String masterWorkingDirectory)
	{
		// create master working directory if not exists
		File dir = new File(masterWorkingDirectory);
		if(!dir.exists()) dir.mkdir();
		
				
		switch(Protocol.valueOf(input.getProtocol().toUpperCase()))
		{
			case RTSP:
			case RTP:
			logger.info("create application name folder");	
			break;
		
			case RTMP:
			case RTMPS:
			case RTMPT:
				
			String inName = input.getMediaName();
			
			ILibRtmpConfig rtmpInterpretor = LibRtmpConfigurationFactory.getLibRtmpConfiguration(Server.valueOf(Globals.getEnv(Globals.Vars.OPERATING_SERVER).toUpperCase()));
			rtmpInterpretor.prepareFrom(input);
			String appName = rtmpInterpretor.getAppName();
			
			File appScopeDir = new File(dir.getAbsolutePath() + File.separator + appName);
			if(!appScopeDir.exists()) appScopeDir.mkdirs();
			dir = appScopeDir;
			
			break;
				
			default:
			break;			  
		}
		
		return dir.getAbsolutePath();
	}
	
	public void buildAudioQuery(CommandLine cmdLine, IAudio config) throws Exception
	{
		ICodec acodec = config.getCodec();
		
		if(!acodec.getEnabled())
		throw new Exception("Audio codec disabled");
		
	
		if(acodec.getSameAsSource())
		{
			cmdLine.addArgument(acodec.getKey());
			cmdLine.addArgument(String.valueOf(acodec.getValue()).toLowerCase());
		}
		else
		{
			// new encode params
			logger.info("Setting audio codec");
			cmdLine.addArgument(acodec.getKey());
			cmdLine.addArgument(String.valueOf(acodec.getValue()).toLowerCase());
			
			ICodecImplementation impl = config.getImplementation();
			if(!CodecImplementations.NORMAL.name().equalsIgnoreCase(String.valueOf(impl.getValue()))){
			cmdLine.addArgument(impl.getKey());
			cmdLine.addArgument(String.valueOf(impl.getValue()));
			}
			
			logger.info("Setting audio bitrate");
			IAudioBitrate abitrate = config.getBitrate();
			if(abitrate.getSameAsSource())
			{
				// NO OP
			}
			else
			{
				if((Integer)abitrate.getValue()>0) {
				cmdLine.addArgument(abitrate.getKey());
				cmdLine.addArgument(String.valueOf(abitrate.getValue())+"k");
				}
			}
			
			
			logger.info("Setting audio samplerate");
			IAudioSampleRate asamplerate = config.getSamplerate();
			if(asamplerate.getSameAsSource())
			{
				// NO OP
			}
			else
			{
				if((Integer)asamplerate.getValue()>0){
				cmdLine.addArgument(asamplerate.getKey());
				cmdLine.addArgument(String.valueOf(asamplerate.getValue()));
				}
			}
			
			
			logger.info("Setting audio channel");
			IAudioChannel achannel = config.getChannel();
			if(achannel.getSameAsSource())
			{
				// NO OP
			}
			else
			{
				if((Integer)achannel.getChannels()>0){
				cmdLine.addArgument(achannel.getKey());
				cmdLine.addArgument(String.valueOf(achannel.getChannels()));
				}
			}
			
			
			/* Extra params (dashed)*/
			logger.info("Setting extra audio params");
			ArrayList<IParameter> params = config.getExtraParams();
			if(params != null){
			for(IParameter param: params){
			cmdLine.addArgument(Flags.DASH + param.getKey());
			cmdLine.addArgument(String.valueOf(param.getValue()));
			}
			}
			
			
			/* Extra properties*/
			logger.info("Setting extra audio properties");
			ArrayList<IProperty> props = config.getExtraProperties();
			if(props != null){
			for(IProperty prop: props){
			cmdLine.addArgument(prop.getData());
			}
			}
		}
	}
	
	public void buildVideoQuery(CommandLine cmdLine, IVideo config) throws Exception
	{
		ICodec vcodec = config.getCodec();
		
		if(!vcodec.getEnabled())
		throw new Exception("Video codec disabled");
	
		if(vcodec.getSameAsSource())
		{
			// pass thru -> use same as source
			cmdLine.addArgument(vcodec.getKey());
			cmdLine.addArgument(String.valueOf(vcodec.getValue()).toLowerCase());
		}
		else
		{
			logger.info("Calculating new video settings");
			
			logger.info("Setting codec");
			cmdLine.addArgument(vcodec.getKey());
			cmdLine.addArgument(String.valueOf(vcodec.getValue()));
			
			
			ICodecImplementation impl = config.getImplementation();
			if(!CodecImplementations.NORMAL.name().equalsIgnoreCase(String.valueOf(impl.getValue()))){
			cmdLine.addArgument(impl.getKey());
			cmdLine.addArgument(String.valueOf(impl.getValue()));
			}
			
			logger.info("Setting frame size");
			IFrameSize framesize = config.getFramesize();
			if(framesize.getSameAsSource())
			{
				// NO OP
			}
			else
			{
				cmdLine.addArgument(framesize.getKey());
				cmdLine.addArgument(String.valueOf(framesize.getValue()));
			}
			
			
			logger.info("Setting frame rate");
			IFrameRate framerate = config.getFramerate();
			if(framerate.getSameAsSource())
			{
				// NO OP
			}
			else
			{
				cmdLine.addArgument(framerate.getKey());
				cmdLine.addArgument(String.valueOf(framerate.getValue()));
			}
			
			
			logger.info("Setting bitrate");
			IVideoBitrate vbitrate = config.getBitrate();
			if(vbitrate.getSameAsSource())
			{
				// NO OP
			}
			else
			{
				IParameter avgBitrate = vbitrate.getAverage();
				if(((Integer)avgBitrate.getValue() > 0)){
					logger.info("ABR enabled");
					cmdLine.addArgument(avgBitrate.getKey());
					cmdLine.addArgument(avgBitrate.getValue()+"k");
				}
				else
				{
					logger.info("VBR enabled");
					IParameter minBitrate = vbitrate.getMinimum();
					if(((Integer)minBitrate.getValue() > 0)){
					cmdLine.addArgument(minBitrate.getKey());
					cmdLine.addArgument(minBitrate.getValue()+"k");
					}
					
					IParameter maxBitrate = vbitrate.getMaximum();
					if(((Integer)maxBitrate.getValue() > 0)){
					cmdLine.addArgument(maxBitrate.getKey());
					cmdLine.addArgument(maxBitrate.getValue()+"k");
					}
					
					IParameter deviceBuffer = vbitrate.getDeviceBuffer();
					if(((Integer)deviceBuffer.getValue() > 0)){
					cmdLine.addArgument(deviceBuffer.getKey());
					cmdLine.addArgument(deviceBuffer.getValue()+"k");
					}
				}
			}
			
			
			logger.info("Setting keyframeinterval & gop");
			IKeyFrameInterval keyframeinterval = config.getKeyFrameInterval();
			if(keyframeinterval.getSameAsSource())
			{
				// NO OP
			}
			else
			{
				IParameter gop = keyframeinterval.getGop();
				if((Integer)gop.getValue()>0)
				{
					IParameter g = (IParameter) gop;
					cmdLine.addArgument(g.getKey());
					cmdLine.addArgument(String.valueOf(g.getValue()));
				}
				
				
				IParameter minkfi = keyframeinterval.getMinimunInterval();
				{
					IParameter kfi_min = (IParameter) minkfi;
					cmdLine.addArgument(kfi_min.getKey());
					cmdLine.addArgument(String.valueOf(kfi_min.getValue()));
				}
			}
			
			
			/* Extra params (dashed)*/
			logger.info("Setting extra video params");
			ArrayList<IParameter> params = config.getExtraParams();
			if(params != null){
			for(IParameter param: params){
			cmdLine.addArgument(Flags.DASH + param.getKey());
			cmdLine.addArgument(String.valueOf(param.getValue()));
			}
			}
			
			
			/* Extra properties*/
			logger.info("Setting extra video properties");
			ArrayList<IProperty> props = config.getExtraProperties();
			if(props != null){
			for(IProperty prop: props){
			cmdLine.addArgument(prop.getData());
			}
			}			
		}
	}
}
