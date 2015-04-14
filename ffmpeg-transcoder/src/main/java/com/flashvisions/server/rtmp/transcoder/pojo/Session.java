package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.data.factory.AbstractDAOFactory;
import com.flashvisions.server.rtmp.transcoder.data.factory.TranscodeConfigurationFactory;
import com.flashvisions.server.rtmp.transcoder.exception.MalformedTranscodeQueryException;
import com.flashvisions.server.rtmp.transcoder.exception.MediaIdentifyException;
import com.flashvisions.server.rtmp.transcoder.handler.TranscodeSessionDestroyer;
import com.flashvisions.server.rtmp.transcoder.handler.TranscodeSessionResultHandler;
import com.flashvisions.server.rtmp.transcoder.handler.TranscodeSessionOutputStream;
import com.flashvisions.server.rtmp.transcoder.helpers.CommandBuilderHelper;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncode;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeIterator;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISessionObserver;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscode;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.TranscodeSessionDataCallback;
import com.flashvisions.server.rtmp.transcoder.interfaces.TranscodeSessionResultCallback;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Server;
import com.flashvisions.server.rtmp.transcoder.system.Globals;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;

/**
 * @author Rajdeep
 *
 */
public class Session implements ISession, TranscodeSessionResultCallback, TranscodeSessionDataCallback {

	private static Logger logger = LoggerFactory.getLogger(Session.class);
	
	public static enum Event{
		START, STOP, PROGRESS, FAILED, COMPLETE
	};
	
	private ITranscode config;
	private ITranscoderResource source;
	private String workingDirectoryPath;
	
	private DefaultExecutor executor;
	private CommandLine cmdLine;
	
	private TranscodeSessionOutputStream outstream;
	private TranscodeSessionResultHandler resultHandler;
	
	private long executonTimeout;
	private ExecuteWatchdog watchdog;
	
	private ArrayList<ISessionObserver> observers;
	
	private static long id;
	
	private Session(Builder builder) 
	{
		Session.id++;
		
		this.config = builder.config;
		this.source = builder.source;
		this.cmdLine = builder.cmdLine;	
		
		this.executor = new DefaultExecutor();
		
		// set this locally not globally -|
		this.workingDirectoryPath = (builder.workingDirectoryPath == null || builder.workingDirectoryPath == "")?Globals.getEnv(Globals.Vars.WORKING_DIRECTORY):builder.workingDirectoryPath;
		this.executor.setWorkingDirectory(new File(this.workingDirectoryPath));
		this.executonTimeout = ExecuteWatchdog.INFINITE_TIMEOUT;
		this.watchdog = new ExecuteWatchdog(executonTimeout);
		
		this.observers = new ArrayList<ISessionObserver>();
		
		logger.info("Session :"+Session.id);
		logger.info("Command :" + this.cmdLine.toString());
	}

	
	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	

	@Override
	public ITranscoderResource getInputSource() {
		// TODO Auto-generated method stub
		return source;
	}

	@Override
	public ITranscode getTranscodeConfig() {
		// TODO Auto-generated method stub
		return config;
	}

	@Override
	public void start() 
	{
		startTranscode();
	}
	
	protected void startTranscode()
	{
		// TODO Auto-generated method stub
		try 
		{	
			this.outstream = new TranscodeSessionOutputStream();
			this.resultHandler = new TranscodeSessionResultHandler(this.watchdog, this);
			
			this.executor.setStreamHandler(new PumpStreamHandler(this.outstream));
			this.executor.setProcessDestroyer(new TranscodeSessionDestroyer(this));
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
		return stopTranscode();
	}

	protected boolean stopTranscode()
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
	public void onTranscodeProcessComplete(int exitValue, long timestamp) {
		// TODO Auto-generated method stub
		logger.error("Process completed with exitValue " + exitValue);
		notifyObservers(Event.COMPLETE, null);
	}

	@Override
	public void onTranscodeProcessFailed(ExecuteException e, ExecuteWatchdog watchdog, long timestamp) {
		// TODO Auto-generated method stub
		if(watchdog != null && watchdog.killedProcess())
		logger.error("Process timed out");
		else
		logger.error("Process failed : " + e.getMessage());
		
		notifyObservers(Event.FAILED, null);
	}
	
	@Override
	public void onTranscodeProcessData(Object data, long timestamp) {
		// TODO Auto-generated method stub
		logger.info(String.valueOf(data));
		notifyObservers(Event.PROGRESS, data);
	}
	
	@Override
	public void onTranscodeProcessStart(long timestamp) {
		// TODO Auto-generated method stub
		notifyObservers(Event.START, null);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		logger.info("Destroying session");
		
		try{
		stopTranscode();
		}catch(Exception e){}
		
		try{watchdog.stop();}
		catch(Exception e1){	}
		finally{watchdog = null;}
		
		try{outstream.close();}
		catch(Exception e2){}
		finally{outstream = null;}
	
		try{resultHandler = null;}
		catch(Exception e3){}
		finally{}
		
		try{observers.clear();}
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

	
	@Override
	public void registerObserver(ISessionObserver observer) {
		// TODO Auto-generated method stub
		this.observers.add(observer);
	}


	@Override
	public void removeObserver(ISessionObserver observer) {
		// TODO Auto-generated method stub
		this.observers.remove(observer);
	}


	@Override
	public void notifyObservers(Event event, Object data) {
		// TODO Auto-generated method stub
		for(ISessionObserver observer : observers)
		{
			switch(event)
			{
				case START:
				observer.onSessionStart(null);
				break;
				
				case PROGRESS:
				observer.onSessionData(null);
				break;
					
				case COMPLETE:
				observer.onSessionComplete(null);
				break;
					
				case FAILED:
				observer.onSessionFailed(null);
				break;
				
				case STOP:
				break;
				
				default:
				break;		
			}
		}
	}

	/***************** Session Builder *********************/
	
	public static class Builder {
		
		// responsible for loading data from xml template
		private static AbstractDAOFactory daoproducer;
		private TranscodeConfigurationFactory configFactory;
				
		private ITranscode config;
		private String templateFile;
		private String workingDirectoryPath;
		
		private ITranscoderResource source;
		private ISession session;
		
		private CommandLine cmdLine;
		@SuppressWarnings("unused")
		private Server serverType;
		
		
		public static Builder newSession(){
			daoproducer = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.FROM_XML_TEMPLATE);
			return new Builder();
		}
		
		public Builder usingTranscodeConfig(ITranscode config){
			this.config = config;
			return this;
		}
		
		public Builder usingMediaInput(ITranscoderResource source){
			this.source = source;
			return this;
		}
		
		public Builder setWorkingDirectory(String workingDirectoryPath){
			this.workingDirectoryPath = workingDirectoryPath;
			return this;
		}
		
		public Builder forServer(String rtmpserver){
			this.serverType = Server.valueOf(rtmpserver.toUpperCase());
			return this;
		}


		public Builder usingTemplateFile(String templateFile) {
			this.templateFile = templateFile;
			return this;
		}

		
		public ISession build() throws MalformedTranscodeQueryException, MediaIdentifyException{
			this.identifyInput();
			this.config = (this.templateFile != null)?this.buildTranscodeConfigFromTemplate(this.templateFile):this.config;
			this.cmdLine = buildExecutableCommand(this.source, this.config);
			this.session = new Session(this);						
			return this.session;
		}

		protected void identifyInput() throws MediaIdentifyException
		{
			logger.info("Identifying input");
			IOUtils.IdentifyInput(source);
		}
		
		protected ITranscode buildTranscodeConfigFromTemplate(String templateFile)
		{
			configFactory = TranscodeConfigurationFactory.getInstance();
			configFactory.setDaoSupplier(daoproducer);
			
			return configFactory.getTranscodeConfiguration(templateFile);
		}
		
		protected CommandLine buildExecutableCommand(ITranscoderResource source, ITranscode config) throws MalformedTranscodeQueryException{
			
			logger.info("Building transcoder command");
			
			HashMap<String, Object> replacementMap = new HashMap<String, Object>();
			CommandLine cmdLine = new CommandLine("${ffmpegExecutable}");
			CommandBuilderHelper helper = new CommandBuilderHelper();
			
			try
			{
						replacementMap.put("ffmpegExecutable", Globals.getEnv(Globals.Vars.FFMPEG_EXECUTABLE_PATH));
						replacementMap.put("inputSource", source.describe());
									
										
						if(!config.getEnabled())
						throw new Exception("Transcode configuration disabled");
						
						
						/********************************************
						********** Processing Inputs ****************
						*********************************************/
						
						cmdLine.addArgument("-re");
						cmdLine.addArgument("-i");
						cmdLine.addArgument("${inputSource}");						
						
						/************************************************
						 ********** Processing Encodes ****************
						 ************************************************/	
						
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
								ITranscodeOutput output = encode.getOutput();
	
								
								
								/************************************************
								 ********** Video configurations ****************
								 ************************************************/
								try
								{
									logger.info("Parsing video settings for encode");
									
									if(!vConfig.getEnabled())
									throw new Exception("Video configuration disabled");
																	
									try
									{
										helper.buildVideoQuery(cmdLine, vConfig);
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
									logger.info("Parsing audio settings for encode");
									
									if(!aConfig.getEnabled())
									throw new Exception("Audio configuration disabled");
																	
									
									try
									{
										helper.buildAudioQuery(cmdLine, aConfig);
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
								
								
								
								/************************************************
								 ********** Output configurations ****************
								 ************************************************/
								try
								{								
									helper.buildOutputQuery(cmdLine, source, output);
								}
								catch(Exception e)
								{
									logger.info("Error building output");
									throw(e);
								}
							}
							catch(Exception e)
							{
								logger.info("Error configuring encode. {cause: "+e.getMessage()+"} Skipping...");
								throw new MalformedTranscodeQueryException(e);
							}
						}
						
						replacementMap.put("inputSource", source.describe());
						cmdLine.setSubstitutionMap(replacementMap);
						return cmdLine;
			}
			catch(Exception e)
			{
				logger.info("Error creating session : " + e.getMessage());
				throw new MalformedTranscodeQueryException(e);
			}
			finally
			{
				helper = null;
			}
		}
	}


	


	
}
