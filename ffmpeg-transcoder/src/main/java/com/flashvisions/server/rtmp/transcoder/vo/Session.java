package com.flashvisions.server.rtmp.transcoder.vo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.exec.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.interfaces.IArbitaryProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioBitrate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioChannel;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncode;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeIterator;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFlag;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameSize;
import com.flashvisions.server.rtmp.transcoder.interfaces.IKeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlay;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlayCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlayIterator;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISessionHandler;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;

public class Session implements ISession {

	private static Logger logger = LoggerFactory.getLogger(Session.class);
	
	private ProcessBuilder transcodePb = null;
	private Process process;
	
	private ITranscodeConfig config;
	private ISessionHandler handler;
	private IMediaInput source;
	
	
	private Session(Builder builder) 
	{
		this.config = builder.config;
		this.source = builder.source;
		this.transcodePb = builder.transcodePb;
	}

	
	@Override
	public int getIdentifier() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public IMediaInput getInputSource() {
		// TODO Auto-generated method stub
		return source;
	}

	@Override
	public void setInputSource(IMediaInput source) {
		// TODO Auto-generated method stub
		this.source = source;
	}

	@Override
	public ITranscodeConfig getTranscodeConfig() {
		// TODO Auto-generated method stub
		return config;
	}

	@Override
	public void setTranscodeConfig(ITranscodeConfig config) {
		// TODO Auto-generated method stub
		this.config = config;
	}

	@Override
	public ISessionHandler getHandler() {
		// TODO Auto-generated method stub
		return handler;
	}

	@Override
	public void setHandler(ISessionHandler handler) {
		// TODO Auto-generated method stub
		this.handler = handler;
	}

	@Override
	public void start() 
	{
		// TODO Auto-generated method stub
		try 
		{
			process = transcodePb.start();
			
			if(handler != null) 
			handler.onStart(this, process);
		} 
		catch (IOException e) 
		{
			logger.info("Error starting process " + e.getMessage());
		}
	}

	@Override
	public boolean stop() 
	{
		try
		{
			if(process != null && process.isAlive())
			process.destroyForcibly();
			
			if(handler != null) 
			handler.onStop(this, process);
		}
		catch(Exception e)
		{
			logger.info("Error stopping process " + e.getMessage());
			return false;
		}
		
		return true;
	}

	
	@Override
	public boolean isRunning() 
	{
		if(process != null && process.isAlive())
		return true;
		else
		return false;	
	}

	
	/***************** Session Builder *********************/
	
	public static class Builder {
		
		private ITranscodeConfig config;
		private IMediaInput source;
		private ISession session;		
		private ProcessBuilder transcodePb;
		
		public static Builder newSession(){
			return new Builder();
		}
		
		public Builder usingTranscodeConfig(ITranscodeConfig config){
			this.config = config;
			return this;
		}
		
		public Builder usingMediaInput(IMediaInput source){
			this.source = source;
			return this;
		}
		
		public ISession build(){
			
			this.session = new Session(this);
			this.session.setInputSource(this.source);
			this.session.setTranscodeConfig(this.config);
			this.buildExecutableCommand(this.source, this.config);
			
			return this.session;
		}
		
		protected void buildExecutableCommand(IMediaInput source, ITranscodeConfig config){
			logger.info("Building transcoder command");
			
			CommandLine cmdLine = new CommandLine("${ffmpegExecutable}");
			HashMap<String, Object> replacementMap = new HashMap<String, Object>();
			replacementMap.put("ffmpegExecutable", "ffmpeg.exe");			
			
			
			if(config.getEnabled())
			{
				IEncodeCollection outputs = config.getEncodes();
				IEncodeIterator iterator = outputs.iterator();
				
				while(iterator.hasNext())
				{
					logger.info("Parsing encode configuration");
					IEncode encode = iterator.next();
					
					
					if(encode.getEnabled())
					{
						IVideo vConfig = encode.getVideoConfig();
						IAudio aConfig = encode.getAudioConfig();
						ArrayList<IFlag> outFlags = encode.getOutputflags();
						IMediaOutput output = encode.getOutput();
						
						if(vConfig.getEnabled())
						{
							logger.info("Parsing video settings for encode");
							ICodec vcodec = vConfig.getCodec();
							
							if(vcodec.getSameAsSource())
							{
								// pass thru -> use same as source
								// no op
							}
							else
							{
								// new encode params
								cmdLine.addArgument("-codec:v");
								cmdLine.addArgument(vcodec.getName());
								
								
								
								IFrameSize framesize = vConfig.getFramesize();
								if(framesize.getSameAsSource())
								{
									// NO OP
								}
								else
								{
									cmdLine.addArgument("-s");
									cmdLine.addArgument(framesize.getWidth()+"x"+framesize.getHeight());
								}
								
								
								
								IFrameRate framerate = vConfig.getFramerate();
								if(framerate.getSameAsSource())
								{
									// NO OP
								}
								else
								{
									cmdLine.addArgument("-r");
									cmdLine.addArgument(String.valueOf(framerate.getFramerate()));
								}
								
								
								
								IVideoBitrate vbitrate = vConfig.getBitrate();
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
										if(vbitrate.getMinimum()>0)
										{
											cmdLine.addArgument("-minrate");
											cmdLine.addArgument(vbitrate.getMinimum()+"k");
										}
										
										if(vbitrate.getMaximum()>0)
										{
											cmdLine.addArgument("-maxrate");
											cmdLine.addArgument(vbitrate.getMaximum()+"k");
										}
										
										if(vbitrate.getDeviceBuffer()>0)
										{
											cmdLine.addArgument("-bufsize");
											cmdLine.addArgument(vbitrate.getDeviceBuffer()+"k");
										}
									}
								}
								
								IKeyFrameInterval keyframeinterval = vConfig.getKeyFrameInterval();
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
								
								
								/* Overlays */
								IOverlayCollection overlays = vConfig.getOverlays();
								IOverlayIterator it = overlays.iterator();
								while(it.hasNext()){
									IOverlay overlay = it.next();
									// to do
								}								
								
								
								/* Extra params such as filters */
								ArrayList<IArbitaryProperty> extraVideoParams = vConfig.getExtraParams();
								Iterator<IArbitaryProperty> itv = extraVideoParams.iterator();
								while(it.hasNext()){
									IArbitaryProperty prop = itv.next();
									cmdLine.addArgument(prop.getKey());
									cmdLine.addArgument(prop.getValue());
								}
								
							}
						}
						else
						{
							cmdLine.addArgument("-vn");
						}
						
						
						
						
						if(aConfig.getEnabled())
						{
							logger.info("Parsing audio settings for encode");
							ICodec acodec = aConfig.getCodec();
							
							if(acodec.getSameAsSource())
							{
								// pass thru -> use same as source
								// no op
							}
							else
							{
								// new encode params
								cmdLine.addArgument("-codec:a");
								cmdLine.addArgument(acodec.getName());
								
								
								
								IAudioBitrate abitrate = aConfig.getBitrate();
								if(abitrate.getSameAsSource())
								{
									// NO OP
								}
								else
								{
									if(abitrate.getBitrate()>0)
									{
										cmdLine.addArgument("-b:a");
										cmdLine.addArgument(abitrate.getBitrate()+"k");
									}
								}
								
								
								IAudioSampleRate asamplerate = aConfig.getSamplerate();
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
								
								
								IAudioChannel achannel = aConfig.getChannel();
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
								ArrayList<IArbitaryProperty> extraAudioParams = aConfig.getExtraParams();
								Iterator<IArbitaryProperty> ita = extraAudioParams.iterator();
								while(ita.hasNext()){
									IArbitaryProperty prop = ita.next();
									cmdLine.addArgument(prop.getKey());
									cmdLine.addArgument(prop.getValue());
								}
							}
						}
						else
						{
							cmdLine.addArgument("-an");
						}
						
						
						
						if(!outFlags.isEmpty())
						{
							logger.info("Parsing extra output flags for encode");
							Iterator<IFlag> it = outFlags.iterator();
							
							while(it.hasNext())
							{
								cmdLine.addArgument(it.next().getData());
							}
						}
						
						
						if(output.getSourcePath() != null)
						{
							logger.info("Processing output destination for encode");
							
							IMediaOutput destination = IOUtils.createOutputFromInput(this.source, output);
							
							logger.info("Processing output destination for encode"
									+ " "
									+ "Container :" 
									+ destination.getContainer()
									+ " "
									+ "Destination :" + destination.getSourcePath());
							
							cmdLine.addArgument("-y");
							
							cmdLine.addArgument("-f");
							cmdLine.addArgument(destination.getContainer());
							
							cmdLine.addArgument(destination.getSourcePath());
						}
					}
				}
				
				cmdLine.setSubstitutionMap(replacementMap);
			}
		}
	}
}
