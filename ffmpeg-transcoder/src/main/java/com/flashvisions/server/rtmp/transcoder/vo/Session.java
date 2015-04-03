package com.flashvisions.server.rtmp.transcoder.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.exception.MalformedTranscodeQueryException;
import com.flashvisions.server.rtmp.transcoder.handler.SessionDestroyer;
import com.flashvisions.server.rtmp.transcoder.handler.SessionResultHandler;
import com.flashvisions.server.rtmp.transcoder.handler.SessionOutputStream;
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
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec;
import com.flashvisions.server.rtmp.transcoder.pojo.Overlay.Location.ALIGNMENT;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;

public class Session implements ISession {

	private static Logger logger = LoggerFactory.getLogger(Session.class);
	
	private ITranscodeConfig config;
	private IMediaInput source;
	
	private DefaultExecutor executor;
	private CommandLine cmdLine;
	private SessionOutputStream outstream;
	private SessionResultHandler resultHandler;
	private long executonTimeout = 0;
	
	
	private Session(Builder builder) 
	{
		this.config = builder.config;
		this.source = builder.source;
		this.cmdLine = builder.cmdLine;	
		
		
		this.executonTimeout = builder.executonTimeout;
		this.executor = new DefaultExecutor();
		this.executor.setWatchdog(new ExecuteWatchdog(this.executonTimeout));
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
	public void start() 
	{
		// TODO Auto-generated method stub
		try 
		{
			this.outstream = new SessionOutputStream();
			this.resultHandler = new SessionResultHandler();
			this.executor.setStreamHandler(new PumpStreamHandler(this.outstream));
			this.executor.setProcessDestroyer(new SessionDestroyer());
			this.executor.setExitValue(0);
			this.executor.execute(this.cmdLine, this.resultHandler);
		} 
		catch (Exception e) 
		{
			logger.info("Error starting process " + e.getMessage());
		}
	}

	@Override
	public boolean stop() 
	{
		try
		{
			if(isRunning())
			executor.getWatchdog().destroyProcess();
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
		return (this.outstream == null)?false:this.outstream.isRunning();
	}

	
	/***************** Session Builder *********************/
	
	public static class Builder {
		
		private ITranscodeConfig config;
		private IMediaInput source;
		private ISession session;		
		
		private CommandLine cmdLine;
		private long executonTimeout = 15000;
		
		
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
		
		public ISession build() throws MalformedTranscodeQueryException{
			
			this.cmdLine = buildExecutableCommand(this.source, this.config);
			this.session = new Session(this);						
			return this.session;
		}
		
		protected CommandLine buildExecutableCommand(IMediaInput source, ITranscodeConfig config) throws MalformedTranscodeQueryException{
			
			logger.info("Building transcoder command");
			
			HashMap<String, Object> replacementMap = new HashMap<String, Object>();
			CommandLine cmdLine = new CommandLine("${ffmpegExecutable}");
			
			replacementMap.put("ffmpegExecutable", "ffmpeg.exe");			
			
			try
			{			
			
					if(config.getEnabled())
					{
						IEncodeCollection outputs = config.getEncodes();
						IEncodeIterator iterator = outputs.iterator();
						
						while(iterator.hasNext())
						{
							logger.info("Parsing encode configuration");
							IEncode encode = iterator.next();
							
							try
							{
								if(!encode.getEnabled())
								throw new Exception("Video configuration disabled");	
							
								IVideo vConfig = encode.getVideoConfig();
								IAudio aConfig = encode.getAudioConfig();
								ArrayList<IFlag> outFlags = encode.getOutputflags();
								IMediaOutput output = encode.getOutput();
								
								try
								{
									logger.info("Parsing video settings for encode");
									
									if(!vConfig.getEnabled())
									throw new Exception("Video configuration disabled");
																	
									try
									{
										ICodec vcodec = vConfig.getCodec();
										
										if(!vcodec.getEnabled())
										throw new Exception("Video codec disabled");
									
										if(vcodec.getSameAsSource())
										{
											// pass thru -> use same as source
											// no op
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
											
											
											logger.info("Setting frame rate");
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
											
											
											logger.info("Setting bitrate");
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
											logger.info("Setting overlays");
											IOverlayCollection overlays = vConfig.getOverlays();
											IOverlayIterator ito = overlays.iterator();
											while(ito.hasNext())
											{
												IOverlay o = ito.next();
												
												if(!o.getEnabled())
												{
													// NO OP
												}
												else
												{
													ALIGNMENT align = o.getLocation().getAlign();
													switch(align)
													{
														case BOTTOMLEFT:
														break;
														
														case BOTTOMRIGHT:
														break;
														
														case CENTERBOTTOM:
														break;
														
														case CENTERLEFT:
														break;
														
														case CENTERMIDDLE:
														break;
														
														case CENTERRIGHT:
														break;
														
														case CENTERTOP:
														break;
														
														case TOPLEFT:
														break;
														
														case TOPRIGHT:
														break;
													}										
													
												}
											}
											
											
											/* Extra params such as filters */
											logger.info("Setting extra video params");
											ArrayList<IArbitaryProperty> extraVideoParams = vConfig.getExtraParams();
											Iterator<IArbitaryProperty> itv = extraVideoParams.iterator();
											while(itv.hasNext()){
												IArbitaryProperty prop = (IArbitaryProperty) itv.next();
												cmdLine.addArgument("-"+prop.getKey());
												cmdLine.addArgument(prop.getValue());
											}
											
										}
									}
									catch(Exception e)
									{
										throw(e); 
									}
								}
								catch(Exception e)
								{
									logger.info("Condition in video encode settings.{"+e.getMessage()+"} Disabling video..");
									cmdLine.addArgument("-vn");
								}
								
								
								
								
								/************************************************
								 ********** Audio configurations ****************
								 ************************************************/
								try
								{
									if(!aConfig.getEnabled())
									throw new Exception("Audio configuration disabled");
									
									logger.info("Parsing audio settings for encode");
									
									try
									{
										ICodec acodec = aConfig.getCodec();
										
										if(!acodec.getEnabled())
										throw new Exception("Audio codec disabled");
									
										if(acodec.getSameAsSource())
										{
											// pass thru -> use same as source
											// no op
										}
										else
										{
											// new encode params
											logger.info("Setting audio codec");
											cmdLine.addArgument("-codec:a");
											cmdLine.addArgument(acodec.getName());
											
											logger.info("Setting codec implementation");
											if(acodec.getImplementation() != Codec.Implementation.NORMAL){
											cmdLine.addArgument("-strict");
											cmdLine.addArgument(acodec.getImplementation().name().toLowerCase());
											}
											
											
											logger.info("Setting audio bitrate");
											IAudioBitrate abitrate = aConfig.getBitrate();
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
											
											
											logger.info("Setting audio channel");
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
											logger.info("Setting extra audio params");
											ArrayList<IArbitaryProperty> extraAudioParams = aConfig.getExtraParams();
											Iterator<?> ita = extraAudioParams.iterator();
											while(ita.hasNext()){
												IArbitaryProperty prop = (IArbitaryProperty) ita.next();
												cmdLine.addArgument("-"+prop.getKey());
												cmdLine.addArgument(prop.getValue());
											}
										}
									}
									catch(Exception e)
									{
										throw(e);
									}
								}
								catch(Exception e)
								{
									logger.info("Condition in audio encode settings.{"+e.getMessage()+"} Disabling audio..");
									cmdLine.addArgument("-an");
								}
								
								
								
								
								if(!outFlags.isEmpty())
								{
									logger.info("Parsing extra output flags for encode");
									Iterator<IFlag> it = outFlags.iterator();
									
									while(it.hasNext())	{
										cmdLine.addArgument(it.next().getData());
									}
								}
								
								
								if(output.getSourcePath() != null)
								{
									logger.info("Processing output destination for encode");
									
									IMediaOutput destination = IOUtils.createOutputFromInput(this.source, output);
									
									logger.info("Output destination for encode"
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
							catch(Exception e)
							{
								logger.info("Disabled encode configuration. Skipping...");
							}
						}
						
						cmdLine.setSubstitutionMap(replacementMap);
					}
					
					return cmdLine;
			}
			catch(Exception e)
			{
				throw new MalformedTranscodeQueryException(e);
			}
		}

		public long getExecutonTimeout() {
			return executonTimeout;
		}

		public void setExecutonTimeout(long executonTimeout) {
			this.executonTimeout = executonTimeout;
		}
	}
}
