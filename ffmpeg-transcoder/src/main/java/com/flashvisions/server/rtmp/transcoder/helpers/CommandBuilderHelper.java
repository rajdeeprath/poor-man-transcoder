package com.flashvisions.server.rtmp.transcoder.helpers;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.exec.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioBitrate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioChannel;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameSize;
import com.flashvisions.server.rtmp.transcoder.interfaces.IKeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;

public class CommandBuilderHelper {

	private static Logger logger = LoggerFactory.getLogger(CommandBuilderHelper.class);
	
	
	public void buildOutputQuery(CommandLine cmdLine, IMediaInput input, ITranscodeOutput output) throws Exception
	{
		logger.info("Processing output destination for encode");
		
		IMediaOutput template = output.getMediaOutput();
		ArrayList<IProperty> properties = output.getOutputProperties();
		
		IMediaOutput destination = IOUtils.createOutputFromInput(input, template);
									
		cmdLine.addArgument("-y");
		cmdLine.addArgument("-f");
		cmdLine.addArgument(destination.getContainer().toString());		
		
		
		/***************** extra properties for output **************/
		
		if(!properties.isEmpty())
		{
			Iterator<IProperty> it = properties.iterator();
			while(it.hasNext())	{
				cmdLine.addArgument(it.next().getData());
			}
		}
		
		
		cmdLine.addArgument(destination.getSourcePath());
	}
	
	public void buildAudioQuery(CommandLine cmdLine, IAudio config) throws Exception
	{
		ICodec acodec = config.getCodec();
		
		if(!acodec.getEnabled())
		throw new Exception("Audio codec disabled");
	
		if(acodec.getSameAsSource())
		{
			// pass thru -> use same as source
			cmdLine.addArgument("-codec:a");
			cmdLine.addArgument("copy");
		}
		else
		{
			// new encode params
			logger.info("Setting audio codec");
			cmdLine.addArgument("-codec:a");
			cmdLine.addArgument(acodec.getName());
			
			
			logger.info("Setting codec implementation");
			if(acodec.getImplementation() != null && acodec.getImplementation() != Codec.Implementation.NORMAL){
			cmdLine.addArgument("-strict");
			cmdLine.addArgument(acodec.getImplementation().name().toLowerCase());
			}
			
			
			logger.info("Setting audio bitrate");
			IAudioBitrate abitrate = config.getBitrate();
			if(abitrate.getSameAsSource())
			{
				// NO OP
			}
			else
			{
				if(abitrate.getBitrate()>0) {
					cmdLine.addArgument("-b:a");
					cmdLine.addArgument(abitrate.getBitrate()+"k");
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
				if(asamplerate.getSamplerate()>0){
				cmdLine.addArgument("-ar");
				cmdLine.addArgument(String.valueOf(asamplerate.getSamplerate()));
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
				if(achannel.getChannels()>0){
				cmdLine.addArgument("-ac");
				cmdLine.addArgument(String.valueOf(achannel.getChannels()));
				}
			}
			
			
			/* Extra params such as filters */
			logger.info("Setting extra audio params");
			ArrayList<IParameter> extraAudioParams = config.getExtraParams();
			Iterator<?> ita = extraAudioParams.iterator();
			while(ita.hasNext()){
				IParameter prop = (IParameter) ita.next();
				cmdLine.addArgument("-"+prop.getKey());
				cmdLine.addArgument(String.valueOf(prop.getValue()));
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
			cmdLine.addArgument("-codec:v");
			cmdLine.addArgument("copy");
		}
		else
		{
			logger.info("Calculating new video settings");
			
			
			logger.info("Setting codec");
			cmdLine.addArgument("-codec:v");
			cmdLine.addArgument(vcodec.getName());
			
			
			logger.info("Setting codec implementation");
			if(vcodec.getImplementation() != Codec.Implementation.NORMAL){
			cmdLine.addArgument("-strict");
			cmdLine.addArgument(vcodec.getImplementation().name().toLowerCase());
			}
			
			
			logger.info("Setting frame size");
			IFrameSize framesize = config.getFramesize();
			if(framesize.getSameAsSource())
			{
				// NO OP
			}
			else
			{
				cmdLine.addArgument("-s");
				cmdLine.addArgument(framesize.getWidth()+"x"+framesize.getHeight());
			}
			
			
			logger.info("Setting frame rate");
			IFrameRate framerate = config.getFramerate();
			if(framerate.getSameAsSource())
			{
				// NO OP
			}
			else
			{
				cmdLine.addArgument("-r");
				cmdLine.addArgument(String.valueOf(framerate.getFramerate()));
			}
			
			
			logger.info("Setting bitrate");
			IVideoBitrate vbitrate = config.getBitrate();
			if(vbitrate.getSameAsSource())
			{
				// NO OP
			}
			else
			{
				if(vbitrate.getAverage()>0)
				{
					logger.info("ABR enabled");
					cmdLine.addArgument("-b:v");
					cmdLine.addArgument(vbitrate.getAverage()+"k");
				}
				else
				{
					if(vbitrate.getMinimum()>0){
						cmdLine.addArgument("-minrate");
						cmdLine.addArgument(vbitrate.getMinimum()+"k");
					}
					
					if(vbitrate.getMaximum()>0){
						cmdLine.addArgument("-maxrate");
						cmdLine.addArgument(vbitrate.getMaximum()+"k");
					}
					
					if(vbitrate.getDeviceBuffer()>0){
						cmdLine.addArgument("-bufsize");
						cmdLine.addArgument(vbitrate.getDeviceBuffer()+"k");
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
				if(keyframeinterval.getGop()>0){
				cmdLine.addArgument("-g");
				cmdLine.addArgument(String.valueOf(keyframeinterval.getGop()));
				}
				
				if(keyframeinterval.getMinimunInterval()>0){
				cmdLine.addArgument("-keyint_min");
				cmdLine.addArgument(String.valueOf(keyframeinterval.getMinimunInterval()));
				}
			}
			
			
			/* Extra params such as filters */
			logger.info("Setting extra video params");
			ArrayList<IParameter> extraVideoParams = config.getExtraParams();
			Iterator<IParameter> itv = extraVideoParams.iterator();
			while(itv.hasNext()){
				IParameter prop = (IParameter) itv.next();
				cmdLine.addArgument("-"+prop.getKey());
				cmdLine.addArgument(String.valueOf(prop.getValue()));
			}		
		}
	}
}
