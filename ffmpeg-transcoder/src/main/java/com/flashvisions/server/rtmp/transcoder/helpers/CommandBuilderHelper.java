package com.flashvisions.server.rtmp.transcoder.helpers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.CodecImplementations;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Protocol;
import com.flashvisions.server.rtmp.transcoder.system.Globals;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;

public class CommandBuilderHelper {

	private static Logger logger = LoggerFactory.getLogger(CommandBuilderHelper.class);
	
	
	public ITranscoderResource buildOutput(CommandLine cmdLine, ITranscoderResource input, ITranscodeOutput output, TokenReplacer tokenReplacer, String workingDirectory) throws Exception
	{
		logger.info("Processing output destination for encode");
		
		String HLS_FILES_PATTERN = "([^\\s]+(\\.(?i)(m3u8|ts))$)";
		
		String segmentDirectory = null;
		
		ITranscoderResource template = output.getMediaOutput();
		ArrayList<IProperty> properties = output.getOutputProperties();
		ArrayList<IParameter> parameters = output.getOutputIParameters();
		ITranscoderResource transcoderOutput = IOUtils.createOutputFromInput(input, template, tokenReplacer);
		
		
		cmdLine.addArgument(Flags.OVERWRITE);
		cmdLine.addArgument(Flags.OUTPUT);
		cmdLine.addArgument(transcoderOutput.getContainer().toString());
		
		
		/************* special pre-processing for hls output ******/
		
		switch(transcoderOutput.getContainer().getType())
		{
			case SSEGMENT:
			segmentDirectory = prepareSegmentDirectory(cmdLine, transcoderOutput, tokenReplacer, workingDirectory);
			tokenReplacer.setTokenValue(TokenReplacer.TOKEN.OWN_SEGMENT_DIRECTORY, segmentDirectory);
			break;
			
			default:
			cmdLine.addArgument(transcoderOutput.describe(), true);
			break;
		}
		
		
		/***************** extra parameters for output **************/
		if(!parameters.isEmpty())
		{
			for(IParameter param: parameters){
				String key = param.getKey();
				String value = String.valueOf(param.getValue());
				
				
				switch(transcoderOutput.getContainer().getType())
				{
					case SSEGMENT:
					value = value.replaceAll(HLS_FILES_PATTERN, tokenReplacer.asPlaceholder(TokenReplacer.TOKEN.OWN_SEGMENT_DIRECTORY) + value);
					value = tokenReplacer.processReplacement(value);
					break;
					
					default:
					break;
				}
				
				cmdLine.addArgument(Flags.DASH + key);
				cmdLine.addArgument(value);
			}
		}
		
		/***************** extra properties for output **************/		
		if(!properties.isEmpty())
		{
			for(IProperty property: properties){
				String data = property.getData();
				
				
				switch(transcoderOutput.getContainer().getType())
				{
					case SSEGMENT:
					data = data.replaceAll(HLS_FILES_PATTERN, tokenReplacer.asPlaceholder(TokenReplacer.TOKEN.OWN_SEGMENT_DIRECTORY) + data);
					data = tokenReplacer.processReplacement(data);
					break;
					
					default:
					break;
				}
				
				cmdLine.addArgument(data, true);
			}
		}
		
		
		return transcoderOutput;
	}
	
	
	protected String prepareSegmentDirectory(CommandLine cmdLine, ITranscoderResource output, TokenReplacer tokenReplacer, String masterWorkingDirectory) throws IOException
	{	
		// create sub directory for hls output
		String HLS_SAMPLE_PLAYBACK_TEMPLATE = "hls-index-sample.html";
		String OUTPUT_HLS_PLAYBACK_TEMPLATE = "index.html";
		tokenReplacer.setTokenValue(TokenReplacer.TOKEN.HLS_SAMPLE_PLAYBACK_TEMPLATE, HLS_SAMPLE_PLAYBACK_TEMPLATE);
		
		String outName = output.getMediaName();
		String outNameWithOutExt = FilenameUtils.removeExtension(outName);
		
		File sub = new File(masterWorkingDirectory + File.separator + outNameWithOutExt);
		if(!sub.exists()) sub.mkdir();
		
		File indexTemplateSample = new File(Globals.getEnv(Globals.Vars.TEMPLATE_DIRECTORY) + File.separator + HLS_SAMPLE_PLAYBACK_TEMPLATE);
		File indexTemplate = new File(sub.getAbsolutePath() + File.separator + OUTPUT_HLS_PLAYBACK_TEMPLATE);
		if(indexTemplateSample.exists())  FileUtils.copyFile(indexTemplateSample, indexTemplate);
		logger.info("Copying html template for hls playback into " + sub.getAbsolutePath());
		
		String relative = new File(masterWorkingDirectory).toURI().relativize(new File(sub.getAbsolutePath()).toURI()).getPath();
		logger.info("Relative path of segment directory " + relative);
		
		return relative;
		
	}
	
	
	public String prepareWorkingDirectory(ITranscoderResource input, String masterWorkingDirectory) throws Exception
	{
		// create master working directory if not exists
		File dir = new File(masterWorkingDirectory);
		if(!dir.exists()) dir.mkdir();
		
				
		switch(Protocol.valueOf(input.getProtocol().toUpperCase()))
		{
			case RTSP:
			case RTP:	
			break;
		
			case RTMP:
			case RTMPS:
			case RTMPT:
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
			
			return;
		}
		else if(acodec.getIgnore())
		{
			// skip codec assignment
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
		}
			
		
		/*****************************************
		 ****** SET AUDIO ENCODING PARAMS ********
		 ****************************************/
		
		
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
			
			return;
		}
		
		if(vcodec.getIgnore())
		{
			// skip
		}
		else
		{
			logger.info("Setting codec");
			cmdLine.addArgument(vcodec.getKey());
			cmdLine.addArgument(String.valueOf(vcodec.getValue()));
			
			ICodecImplementation impl = config.getImplementation();
			if(!CodecImplementations.NORMAL.name().equalsIgnoreCase(String.valueOf(impl.getValue()))){
			cmdLine.addArgument(impl.getKey());
			cmdLine.addArgument(String.valueOf(impl.getValue()));
			}
		}
			
			
		/*****************************************
		 ****** SET VIDEO ENCODING PARAMS ********
		 ****************************************/
			
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
