package com.flashvisions.server.rtmp.transcoder.vo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudio;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncode;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeIterator;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFlag;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISessionHandler;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
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
			
			transcodePb = new ProcessBuilder();
			List<String> command = new ArrayList<String>();
			
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
						}
						
						if(aConfig.getEnabled())
						{
							logger.info("Parsing audio settings for encode");
							ICodec acodec = aConfig.getCodec();
						}
						
						if(!outFlags.isEmpty())
						{
							logger.info("Parsing extra output flags for encode");
							Iterator<IFlag> it = outFlags.iterator();
							
							while(it.hasNext())
							{
								IFlag flag = it.next();	
								String data = flag.getData();
								command.add(data);
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
							
							command.add("-y");
							command.add("-f");
							command.add(destination.getContainer());
							command.add(destination.getSourcePath());
						}
					}
				}
			}
		}
	}
}
