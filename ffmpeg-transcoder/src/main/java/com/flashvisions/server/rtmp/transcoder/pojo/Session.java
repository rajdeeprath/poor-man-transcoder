package com.flashvisions.server.rtmp.transcoder.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.data.factory.AbstractDAOFactory;
import com.flashvisions.server.rtmp.transcoder.data.factory.LibRtmpConfigurationFactory;
import com.flashvisions.server.rtmp.transcoder.data.factory.TranscodeConfigurationFactory;
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
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameSize;
import com.flashvisions.server.rtmp.transcoder.interfaces.IKeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.interfaces.ILibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlay;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlayCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlayIterator;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.system.Globals;
import com.flashvisions.server.rtmp.transcoder.system.Server;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;
import com.flashvisions.server.rtmp.transcoder.utils.SessionUtil;

public class Session implements ISession {

	private static Logger logger = LoggerFactory.getLogger(Session.class);
	
	private ITranscodeConfig config;
	private IMediaInput source;
	
	private DefaultExecutor executor;
	private CommandLine cmdLine;
	private PumpStreamHandler pumpStream;
	private SessionOutputStream outstream;
	private SessionResultHandler resultHandler;
	private long executonTimeout = 0;
	private ExecuteWatchdog watchdog;
	
	private Process proc;
	private String signature; 
	private static long id;
	
	private Session(Builder builder) 
	{
		Session.id++;
		
		this.config = builder.config;
		this.source = builder.source;
		this.cmdLine = builder.cmdLine;	
		
		this.executonTimeout = builder.executonTimeout;
		this.executor = new DefaultExecutor();
		this.watchdog = new ExecuteWatchdog(this.executonTimeout);
		
		this.signature = builder.signature;
		//logger.info("Session signature :" + this.signature);
		logger.info(Session.id +":"+this.cmdLine.toString());
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	@Override
	public String getSignature() {
		// TODO Auto-generated method stub
		return signature;
	}

	@Override
	public IMediaInput getInputSource() {
		// TODO Auto-generated method stub
		return source;
	}

	@Override
	public ITranscodeConfig getTranscodeConfig() {
		// TODO Auto-generated method stub
		return config;
	}

	@Override
	public void start() 
	{
		// TODO Auto-generated method stub
		try 
		{
			
			this.outstream = new SessionOutputStream();
			this.pumpStream = new PumpStreamHandler(this.outstream);
			this.resultHandler = new SessionResultHandler(this.watchdog, this);
			
			this.executor.setStreamHandler(this.pumpStream);
			this.executor.setProcessDestroyer(new SessionDestroyer(this));
			this.executor.setWatchdog(this.watchdog);
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
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		logger.info("Destroying session");
		
		try{watchdog.stop();}
		catch(Exception e1){	}
		finally{watchdog = null;}
		
		try{outstream.close();}
		catch(Exception e2){}
		finally{outstream = null;}
		
		try{pumpStream = null;}
		catch(Exception e2){}
		finally{}
	
		try{resultHandler = null;}
		catch(Exception e3){}
		finally{}
		
		try{cmdLine = null;}
		catch(Exception e3){}
		finally{}
		
		try{executor = null;}
		catch(Exception e4){}
		finally{}
		
		try{source = null;}
		catch(Exception e5){}
		finally{}
		
		try{config = null;}
		catch(Exception e6){}
		finally{}
	}

	
	public Process getProcess() {
		return proc;
	}


	public void setProcess(Process proc) {
		this.proc = proc;
	}


	/***************** Session Builder *********************/
	
	public static class Builder {
		
		// responsible for loading data from xml template
		private static AbstractDAOFactory daoproducer;
		private TranscodeConfigurationFactory configFactory;
				
		private ITranscodeConfig config;
		private String templateFile;
		
		private IMediaInput source;
		private ISession session;	
		private ILibRtmpConfig librtmpConfig;
		private String signature;
		
		private long executonTimeout = 15000;
		private CommandLine cmdLine;
		private Server serverType;
		
		
		public static Builder newSession(){
			daoproducer = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.FROM_XML_TEMPLATE);
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
		
		public Builder forServer(String rtmpserver){
			switch(Server.valueOf(rtmpserver.toUpperCase()))
			{
				case RED5:
				serverType = Server.RED5;
				break;
				
				case WOWZA:
				serverType = Server.WOWZA;
				break;
				
				case FMS:
				break;
				
				default:
				break;
			
			}
			return this;
		}

		public void withTimeout(long executonTimeout) {
			this.executonTimeout = executonTimeout;
		}

		public Builder usingTemplateFile(String templateFile) {
			this.templateFile = templateFile;
			return this;
		}
		
		public Builder usingLibRtmpConfig(ILibRtmpConfig librtmpConfig){
			this.librtmpConfig = librtmpConfig;
			return this;
		}
		
		public ISession build() throws MalformedTranscodeQueryException{
			this.config = (this.templateFile != null)?this.buildTranscodeConfigFromTemplate(this.templateFile):this.config;
			this.cmdLine = buildExecutableCommand(this.source, this.config);
			this.signature = SessionUtil.generateSessionSignature(source.getSourcePath(), templateFile);
			this.session = new Session(this);						
			return this.session;
		}
		
		private String buildLibRtmpString(ILibRtmpConfig librtmpConfig)
		{
			return librtmpConfig.toString();
		}
		
		private ILibRtmpConfig buildLibRtmpConfigurion(IMediaInput input, Server serverType)
		{
			ILibRtmpConfig configuration = LibRtmpConfigurationFactory.getLibRtmpConfiguration(serverType);
			configuration.parseRtmp(input);
			return configuration;
		}
		
		protected ITranscodeConfig buildTranscodeConfigFromTemplate(String templateFile)
		{
			configFactory = TranscodeConfigurationFactory.getInstance();
			configFactory.setDaoSupplier(daoproducer);
			
			return configFactory.getTranscodeConfiguration(templateFile);
		}
		
		
		protected CommandLine buildExecutableCommand(IMediaInput source, ITranscodeConfig config) throws MalformedTranscodeQueryException{
			
			logger.info("Building transcoder command");
			
			HashMap<String, Object> replacementMap = new HashMap<String, Object>();
			CommandLine cmdLine = new CommandLine("${ffmpegExecutable}");
			
			
			try
			{
					replacementMap.put("ffmpegExecutable", Globals.getEnv(Globals.Vars.FFMPEG_EXECUTABLE_PATH));
				
					/* Building librtmp params string */
					{
						ILibRtmpConfig librtmpConfig = (this.librtmpConfig == null)?buildLibRtmpConfigurion(source, serverType):this.librtmpConfig;
						String libRtmpParamString = this.buildLibRtmpString(librtmpConfig);
						replacementMap.put("inputSource", libRtmpParamString);	
					}
										
					if(config.getEnabled())
					{
						{
							logger.info("Setting input source");
							cmdLine.addArgument("-i");
							cmdLine.addArgument("${inputSource}");
						}
						
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
								ArrayList<IProperty> outFlags = encode.getOutputflags();
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
													// DO OP
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
											if(acodec.getImplementation() != null && acodec.getImplementation() != Codec.Implementation.NORMAL){
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
									Iterator<IProperty> it = outFlags.iterator();
									
									while(it.hasNext())	{
										cmdLine.addArgument(it.next().getData());
									}
								}
								
								
								logger.info("Processing output destination for encode");
								
								try
								{
									IMediaOutput destination = IOUtils.createOutputFromInput(this.source, output);
									
									logger.info("Output destination for encode"
											+ " "
											+ "Container :" 
											+ destination.getContainer().getName().toLowerCase()
											+ " "
											+ "Destination :" + destination.getSourcePath());
																		
									cmdLine.addArgument("-f");
									cmdLine.addArgument(destination.getContainer().getName().toLowerCase());
									
									cmdLine.addArgument(destination.getSourcePath());
								}
								catch(Exception e)
								{
									logger.info("Error evaluating output");
									throw(e);
								}
							}
							catch(Exception e)
							{
								logger.info("Disabled encode configuration. {"+e.getMessage()+"} Skipping...");
							}
						}
						
						
						cmdLine.setSubstitutionMap(replacementMap);
					}
					
					return cmdLine;
			}
			catch(Exception e)
			{
				logger.info("Error : " + e.getMessage());
				throw new MalformedTranscodeQueryException(e);
			}
		}

		
	}
}
