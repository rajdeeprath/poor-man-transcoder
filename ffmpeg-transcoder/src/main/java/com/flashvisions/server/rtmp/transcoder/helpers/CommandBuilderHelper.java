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
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodecImplementation;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameSize;
import com.flashvisions.server.rtmp.transcoder.interfaces.IKeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.CodecImplementations;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;

public class CommandBuilderHelper {

	private static Logger logger = LoggerFactory.getLogger(CommandBuilderHelper.class);
	
	
	public void buildOutputQuery(CommandLine cmdLine, ITranscoderResource input, ITranscodeOutput output) throws Exception
	{
		logger.info("Processing output destination for encode");
		
		ITranscoderResource template = output.getMediaOutput();
		ArrayList<IProperty> properties = output.getOutputProperties();
		
		ITranscoderResource trannscoderoutput = IOUtils.createOutputFromInput(input, template);
									
		cmdLine.addArgument("-y");
		cmdLine.addArgument("-f");
		cmdLine.addArgument(trannscoderoutput.getContainer().toString());		
		
		
		/***************** extra properties for output **************/
		
		if(!properties.isEmpty())
		{
			Iterator<IProperty> it = properties.iterator();
			while(it.hasNext())	{
			cmdLine.addArgument(it.next().getData(), true);
			}
		}
		
		/************* special pre-processing for hls output ******/
		switch(trannscoderoutput.getContainer().getType())
		{
			case SSEGMENT:
			break;
			
			default:
			cmdLine.addArgument(trannscoderoutput.describe());
			break;
		}
	}
	
	public void buildAudioQuery(CommandLine cmdLine, IAudio config) throws Exception
	{
		final String DASH = "-";
		ICodec acodec = config.getCodec();
		
		if(!acodec.getEnabled())
		throw new Exception("Audio codec disabled");
		
	
		if(acodec.getSameAsSource())
		{
			// pass thru -> use same as source
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
			
			
			/* Extra params such as filters */
			logger.info("Setting extra audio params");
			ArrayList<IParameter> extraAudioParams = config.getExtraParams();
			Iterator<?> ita = extraAudioParams.iterator();
			while(ita.hasNext()){
				IParameter prop = (IParameter) ita.next();
				cmdLine.addArgument(DASH+prop.getKey());
				cmdLine.addArgument(String.valueOf(prop.getValue()));
			}
		}
	}
	
	public void buildVideoQuery(CommandLine cmdLine, IVideo config) throws Exception
	{
		final String DASH = "-";
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
			
			
			/* Extra params such as filters */
			logger.info("Setting extra video params");
			ArrayList<IParameter> extraVideoParams = config.getExtraParams();
			Iterator<IParameter> itv = extraVideoParams.iterator();
			while(itv.hasNext()){
				IParameter prop = (IParameter) itv.next();
				cmdLine.addArgument(DASH+prop.getKey());
				cmdLine.addArgument(String.valueOf(prop.getValue()));
			}		
		}
	}
}
