package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.command.PostTranscodeCleanupCommand;
import com.flashvisions.server.rtmp.transcoder.context.CleanUpContext;
import com.flashvisions.server.rtmp.transcoder.data.factory.AbstractDAOFactory;
import com.flashvisions.server.rtmp.transcoder.data.factory.TranscodeConfigurationFactory;
import com.flashvisions.server.rtmp.transcoder.exception.MalformedTranscodeQueryException;
import com.flashvisions.server.rtmp.transcoder.exception.MediaIdentifyException;
import com.flashvisions.server.rtmp.transcoder.ffmpeg.Flags;
import com.flashvisions.server.rtmp.transcoder.handler.TranscodeSessionDestroyer;
import com.flashvisions.server.rtmp.transcoder.handler.TranscodeSessionResultHandler;
import com.flashvisions.server.rtmp.transcoder.handler.TranscodeSessionOutputStream;
import com.flashvisions.server.rtmp.transcoder.helpers.CommandBuilderHelper;
import com.flashvisions.server.rtmp.transcoder.helpers.TokenReplacer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncode;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeIterator;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISessionObserver;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscode;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Server;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.SessionEvent;
import com.flashvisions.server.rtmp.transcoder.system.Globals;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;
import com.flashvisions.server.rtmp.transcoder.vo.TranscoderExecutionError;

/**
 * @author Rajdeep
 *
 */
public class Session implements ISession  {

	private static Logger logger = LoggerFactory.getLogger(Session.class);
	
	private static final int  ABORT_TIMEOUT = 2000;
	private static final int  READ_TIMEOUT = 8000;
	private static final int  READ_TIME_THRESHOLD = 5000;
	
	private static final String  FAILURE_BY_TIMEOUT = "Timeout";
	private static final String  GENERIC_FAILURE = "Failure";
	
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
	private ArrayList<ITranscoderResource> outputs;
	
	private boolean cleanUpOnExit;
	
	private Timer processReadInTimer;
	
	private static long id;
	
	private Session(Builder builder) 
	{
		Session.id++;
		
		this.config = builder.config;
		this.source = builder.input;
		this.cmdLine = builder.cmdLine;	
		this.cleanUpOnExit = builder.cleanUpOnExit;
		this.setOutputs(builder.outputs);
		this.executor = new DefaultExecutor();
		
		// set this locally not globally -|
		this.workingDirectoryPath = (builder.workingDirectoryPath == null || builder.workingDirectoryPath == "")?Globals.getEnv(Globals.Vars.WORKING_DIRECTORY):builder.workingDirectoryPath;
		
		this.executonTimeout = ExecuteWatchdog.INFINITE_TIMEOUT;
		this.watchdog = new ExecuteWatchdog(executonTimeout);
		this.observers = new ArrayList<ISessionObserver>();
		
		
		logger.info("Command :" + this.cmdLine.toString());
	}

	@Override
	public String getWorkingDirectoryPath() 
	{
		return workingDirectoryPath;
	}

	@Override
	public void setWorkingDirectoryPath(String workingDirectoryPath) 
	{
		this.workingDirectoryPath = workingDirectoryPath;
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
		try 
		{	
			logger.info("Starting transcode");
			
			notifyObservers(SessionEvent.PRESTART, null);
			
			this.outstream = new TranscodeSessionOutputStream(this);
			this.resultHandler = new TranscodeSessionResultHandler(this.watchdog, this);
			
			this.executor.setWorkingDirectory(new File(this.workingDirectoryPath));
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
		this.resultHandler.setAbortRequestTimestamp(System.currentTimeMillis());
		return stopTranscode();
	}

	protected boolean stopTranscode()
	{
		try
		{
			logger.info("Stopping transcode");
			executor.getWatchdog().destroyProcess();
		}
		catch(Exception e)
		{
			logger.info("Error stopping process " + e.getMessage());
			return false;
		}
		
		return true;
	}

	
	/*
	 * Note: Not Accurate !! Dont use this
	 * (non-Javadoc)
	 * @see com.flashvisions.server.rtmp.transcoder.interfaces.ISession#isRunning()
	 */
	@Override
	public boolean isRunning() 
	{
		return (this.outstream == null)?false:this.outstream.isRunning();
	}
	
	
	@Override
	public void onTranscodeProcessComplete(int exitValue, long timestamp) {
		// TODO Auto-generated method stub
		logger.info("onTranscodeProcessComplete exitValue: " + exitValue);
		
		doCleanUp();
		notifyObservers(SessionEvent.COMPLETE, null);		
	}

	
	protected void doCleanUp()
	{
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(new Runnable() {
		    @Override
			public void run() {
		        if(cleanUpOnExit)
		        {
		        	try 
		        	{
		        		CleanUpContext ctx = new CleanUpContext();
		        		ctx.setInput(source);
		        		ctx.setOutputs(outputs);
		        		ctx.setWorkingDirectory(getWorkingDirectoryPath());
		        		
						new PostTranscodeCleanupCommand().execute(ctx);
					} 
		        	catch (Exception e) 
		        	{
						logger.error("Cleanup error " + e.getMessage());
					}
		        }
		    }
		});
		executorService.shutdown();
	}
	
	@Override
	public void onTranscodeProcessFailed(ExecuteException e, ExecuteWatchdog watchdog, long timestamp) {
		// TODO Auto-generated method stub
		String cause = null;
		
		if(watchdog != null && watchdog.killedProcess()) cause = FAILURE_BY_TIMEOUT;
		else cause = GENERIC_FAILURE;
		
		if(timestamp - this.resultHandler.getAbortRequestTimestamp()>ABORT_TIMEOUT)
		{
			logger.warn("onTranscodeProcessFailed cause: " + cause);
			notifyObservers(SessionEvent.FAILED, new TranscoderExecutionError(e, cause, timestamp));
		}
		else
		{
			logger.warn("Probable force abort");
			doCleanUp();
		}
	}
	
	@Override
	public void onTranscodeProcessData(Object data, long timestamp) {
		// TODO Auto-generated method stub
		logger.debug("onTranscodeProcessData");
		notifyObservers(SessionEvent.DATA, data);
	}
	
	
	@Override
	public void onTranscodeProcessStart(long timestamp) {
		// TODO Auto-generated method stub
		logger.debug("onTranscodeProcessStart");
		notifyObservers(SessionEvent.START, timestamp);
	}
	
	
	@Override
	public void onTranscodeProcessAdded(Process proc) {
		// TODO Auto-generated method stub
		logger.info("onTranscodeProcessAdded");
		notifyObservers(SessionEvent.PROCESSADDED, proc);
		
		// start timer to monitor timeout
		this.processReadInTimer = new Timer();
		this.processReadInTimer.schedule(new ReadTimeoutTask(this.processReadInTimer, this.outstream, this.watchdog), READ_TIMEOUT);
	}


	@Override
	public void onTranscodeProcessRemoved(Process proc) {
		// TODO Auto-generated method stub
		logger.info("onTranscodeProcessRemoved");
		notifyObservers(SessionEvent.PROCESSREMOVED, proc);
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
	public void notifyObservers(SessionEvent event, Object data) {
		// TODO Auto-generated method stub
		for(ISessionObserver observer : observers)
		{
			switch(event)
			{
				case PRESTART:
				observer.onSessionPreStart(this);
				break;
			
				case START:
				observer.onSessionStart(this, data);
				break;
				
				case DATA:
				observer.onSessionData(this, data);
				break;
					
				case COMPLETE:
				observer.onSessionComplete(this, data);
				break;
					
				case FAILED:
				observer.onSessionFailed(this, data);
				break;
				
				case STOP:
				break;
				
				case PROCESSADDED:
				observer.onSessionProcessAdded(this, (Process)data);
				break;
				
				case PROCESSREMOVED:
				observer.onSessionProcessRemoved(this, (Process)data);
				break;
				
				default:
				logger.warn("Unknown session event");
				break;		
			}
		}
	}

	@Override
	public ArrayList<ITranscoderResource> getOutputs() {
		return outputs;
	}

	private void setOutputs(ArrayList<ITranscoderResource> outputs) {
		this.outputs = outputs;
	}
	
	
	
	/* Timeout timer task */
	@SuppressWarnings("unused")
	private class ReadTimeoutTask extends TimerTask
	{
		TranscodeSessionOutputStream outstream;
		ExecuteWatchdog watchdog;
		Timer timer;
		
		public ReadTimeoutTask(Timer timer, TranscodeSessionOutputStream outstream, ExecuteWatchdog watchdog){
			
			this.timer = timer;
			this.outstream = outstream;
			this.watchdog = watchdog;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			this.outstream.flush();
			long lastOutput = this.outstream.getLastOutputTime();
			
			
			if(System.currentTimeMillis() - lastOutput > READ_TIME_THRESHOLD)
			{
				try
				{
					logger.info("Aborting transcode due to read timeout");
					executor.getWatchdog().destroyProcess();
				}
				catch(Exception e)
				{
					logger.info("Error aborting process " + e.getMessage());
				}
				
			}
			
			
			// discard calling timer object
			this.timer.cancel();
			this.timer = null;
			
		}
		
	}

	/***************** Session Builder *********************/
	
	public static class Builder {
		
		// responsible for loading data from xml template
		private static AbstractDAOFactory daoproducer;
		private TranscodeConfigurationFactory configFactory;
		
		private TokenReplacer tokenReplacer;
				
		private ITranscode config;
		private String templateFile;
		private String workingDirectoryPath;
		
		private ITranscoderResource input;
		private ArrayList<ITranscoderResource> outputs;
		private ISession session;
		
		private boolean cleanUpOnExit;
		
		private CommandLine cmdLine;
		@SuppressWarnings("unused")
		private Server serverType;
		
		
		public static Builder newSession(){
			daoproducer = AbstractDAOFactory.getDAOFactory(AbstractDAOFactory.FROM_XML_TEMPLATE);
			return new Builder();
		}
		
		private Builder()
		{
			this.initTokenReplacer();
		}
		
		public Builder usingTranscodeConfig(ITranscode config){
			
			this.config = config;
			return this;
		}
		
		protected void initTokenReplacer()
		{
			this.tokenReplacer = new TokenReplacer();
			
			this.tokenReplacer.setTokenValue(TokenReplacer.TOKEN.HOME_DIRECTORY_TOKEN_2, Globals.getEnv(Globals.Vars.HOME_DIRECTORY));
			this.tokenReplacer.setTokenValue(TokenReplacer.TOKEN.HOME_DIRECTORY_TOKEN, Globals.getEnv(Globals.Vars.HOME_DIRECTORY));
			this.tokenReplacer.setTokenValue(TokenReplacer.TOKEN.TEMPLATE_DIRECTORY, Globals.getEnv(Globals.Vars.TEMPLATE_DIRECTORY));
			this.tokenReplacer.setTokenValue(TokenReplacer.TOKEN.CURRENT_DATE_TOKEN, DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
		}
		
		public Builder usingMediaInput(ITranscoderResource source){
			this.input = source;
			return this;
		}
		
		public Builder inWorkingDirectory(String workingDirectoryPath){
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
		
		public Builder cleanUpOnExit(boolean cleanUpOnExit) {
			this.cleanUpOnExit = cleanUpOnExit;
			return this;
		}

		
		public ISession build() throws MalformedTranscodeQueryException, MediaIdentifyException{
			this.identifyInput();
			this.config = (this.templateFile != null)?this.buildTranscodeConfigFromTemplate(this.templateFile):this.config;
			this.outputs = new ArrayList<ITranscoderResource>();
			this.cmdLine = buildExecutableCommand(this.input, this.config, this.outputs);
			this.session = new Session(this);						
			return this.session;
		}

		protected void identifyInput() throws MediaIdentifyException
		{
			logger.info("Identifying input");
			IOUtils.IdentifyInput(input);
		}
		
		protected ITranscode buildTranscodeConfigFromTemplate(String templateFile)
		{
			configFactory = TranscodeConfigurationFactory.getInstance();
			configFactory.setDaoSupplier(daoproducer);
			
			return configFactory.getTranscodeConfiguration(templateFile);
		}
		
		protected CommandLine buildExecutableCommand(ITranscoderResource input, ITranscode config, ArrayList<ITranscoderResource> outputBucket) throws MalformedTranscodeQueryException{
			
			logger.info("Building transcoder command");
			
			HashMap<String, Object> replacementMap = new HashMap<String, Object>();
			CommandLine cmdLine = new CommandLine(TokenReplacer.TOKEN.FFMPEG_EXECUTABLE);
			CommandBuilderHelper helper = new CommandBuilderHelper();
			
			
			try
			{
						replacementMap.put("ffmpegExecutable", Globals.getEnv(Globals.Vars.FFMPEG_EXECUTABLE_PATH));
						
						
						if(!config.getEnabled())
						throw new Exception("Transcode configuration disabled");
						
						
						/********************************************
						********** Processing Input *****************
						*********************************************/
						
						ArrayList<IProperty> options = input.getOptionFlags();
						if(options != null) for(IProperty option: options) 
						cmdLine.addArgument(option.getData());
						cmdLine.addArgument(Flags.INPUT);
						cmdLine.addArgument(TokenReplacer.TOKEN.INPUT_SOURCE, false);
						
						
						/************************************************
						 ********** Prepare working directory ***********
						 ************************************************/
						
						String workingDirectory = (this.workingDirectoryPath == null || this.workingDirectoryPath == "")?Globals.getEnv(Globals.Vars.WORKING_DIRECTORY):this.workingDirectoryPath;
						workingDirectory = helper.prepareWorkingDirectory(input, workingDirectory);
						this.workingDirectoryPath = workingDirectory;
						this.tokenReplacer.setTokenValue(TokenReplacer.TOKEN.WORKING_DIRECTORY_TOKEN, this.workingDirectoryPath);
						
						
						/************************************************
						 ********** Processing Encodes ****************
						 ************************************************/	
						
						IEncodeCollection encodes = config.getEncodes();
						IEncodeIterator iterator = encodes.iterator();
						
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
									logger.info("Error condition in video encode settings.{"+e.getMessage()+"} Disabling video..");
									cmdLine.addArgument(Flags.DISABLE_VIDEO);
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
									logger.info("Error condition in audio encode settings.{"+e.getMessage()+"} Disabling audio..");
									cmdLine.addArgument(Flags.DISABLE_AUDIO);
								}
								
								
								
								/************************************************
								 ********** Output configurations ****************
								 ************************************************/
								try
								{				
									ITranscoderResource transcoderOutput = helper.buildOutput(cmdLine, input, output, tokenReplacer, workingDirectory);
									outputBucket.add(transcoderOutput);
								}
								catch(Exception e)
								{
									logger.info("Error building output " + e.getCause());
									throw(e);
								}
							}
							catch(Exception e)
							{
								logger.info("Error configuring encode. {cause: "+e.getMessage()+"} Skipping...");
								throw new MalformedTranscodeQueryException(e);
							}
						}
						
						String inputSource = input.describe();
						replacementMap.put("inputSource", inputSource);
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

		public boolean isCleanUpOnExit() {
			return cleanUpOnExit;
		}

		
	}
	
}
