package com.flashvisions.server.rtmp.transcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.context.TranscodeRequest;
import com.flashvisions.server.rtmp.transcoder.decorator.RTMPTranscoderResource;
import com.flashvisions.server.rtmp.transcoder.decorator.RTSPTranscoderResource;
import com.flashvisions.server.rtmp.transcoder.exception.InvalidTranscoderResourceException;
import com.flashvisions.server.rtmp.transcoder.exception.MalformedTranscodeQueryException;
import com.flashvisions.server.rtmp.transcoder.exception.MediaIdentifyException;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISessionObserver;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeSessionManager;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pojo.Property;
import com.flashvisions.server.rtmp.transcoder.pojo.Session;
import com.flashvisions.server.rtmp.transcoder.pojo.io.StreamMedia;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Server;
import com.flashvisions.server.rtmp.transcoder.system.Globals;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;
import com.flashvisions.server.rtmp.transcoder.utils.SessionUtil;

import org.red5.server.api.stream.IBroadcastStream;


public class TranscodeSessionManager implements ISessionObserver, ITranscodeSessionManager {
	
	private static Logger logger = LoggerFactory.getLogger(TranscodeSessionManager.class);

	private volatile static TranscodeSessionManager instance;
	private Hashtable<String, ISession> sessionSignatureTable;
	private ExecutorService threadPoolExecuttor;
	
	
	
	public static synchronized ITranscodeSessionManager getInstance(){
        if(instance == null){
            instance = new TranscodeSessionManager();
            instance.init();
        }
        return instance;
    }

	
	private void init()
	{
		this.threadPoolExecuttor = Executors.newCachedThreadPool();
		this.sessionSignatureTable = new Hashtable<String, ISession>();
		logger.debug("Transcode manager initialized");
	}
	
	
	/* (non-Javadoc)
	 * @see com.flashvisions.server.rtmp.transcoder.ITransCodeessionManager#doTranscode(org.red5.server.api.stream.IBroadcastStream, com.flashvisions.server.rtmp.transcoder.context.TranscodeRequest)
	 */
	@Override
	public void doTranscode(IBroadcastStream stream, TranscodeRequest request) throws InvalidTranscoderResourceException, MalformedTranscodeQueryException, MediaIdentifyException
	{
		String streamingMedia = IOUtils.buildStreamingMediaURL(stream, request);
		
		ArrayList<IProperty> inputflags = new ArrayList<IProperty>();
		
		if(request.getInputflags() != null){
			inputflags.addAll(request.getInputflags());
		}
		
		ITranscoderResource resource = new RTSPTranscoderResource(new StreamMedia(streamingMedia),inputflags);
		
		final ISession session = create(resource, request);
		
		
		this.threadPoolExecuttor.execute(new Runnable(){

			@Override
			public void run() {
				
				try 
				{
					Thread.sleep(request.getReadDelay());
				} catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				
				String sessionSignature = getSessionSignature(stream);
				sessionSignatureTable.put(sessionSignature, session);
				
				session.start();
			}
			
		});
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.flashvisions.server.rtmp.transcoder.ITransCodeessionManager#stopTranscode(org.red5.server.api.stream.IBroadcastStream)
	 */
	@Override
	public void stopTranscode(IBroadcastStream stream)
	{
		String sessionSignature = getSessionSignature(stream);
		final ISession session = getSession(sessionSignature);
		if(session != null){
			
			this.threadPoolExecuttor.execute(new Runnable(){

				@Override
				public void run() {
					
					if(session.isRunning()){
						session.stop();
					}
					
					sessionSignatureTable.remove(sessionSignature);
					destroy(session);
				}
			});
		}
	}
	
	
	private ISession getSession(Object sessionSignature) {
		return sessionSignatureTable.get(sessionSignature);
	}


	private String getSessionSignature(IBroadcastStream stream) {
		return SessionUtil.generateSessionSignature(stream);
	}
	
	
	protected ISession create(ITranscoderResource input, TranscodeRequest request) throws MalformedTranscodeQueryException, MediaIdentifyException
	{
		  String workingDirectory = (request.getWorkingDirectory() == null || request.getWorkingDirectory() == "")?Globals.getEnv(Globals.Vars.WORKING_DIRECTORY):request.getWorkingDirectory();
		  ISession session = Session.Builder.newSession()
					.usingMediaInput(input)
					.usingTemplateFile(request.getTemplateFileName())
					.inWorkingDirectory(workingDirectory)
					.cleanUpOnExit(request.isCleanUpSegmentsOnExit())
					.forServer(Server.RED5.name().toLowerCase())
					.build();
		  
		  session.registerObserver(this);
		  
		  logger.debug("new session created " + session.getId());		  
		  return session;
	}
	
	
	
	protected void destroy(ISession session)
	{	  
		  session.removeObserver(this);		  
		  session.dispose();		  
		  logger.debug("session destroyed " + session.getId());
	}
	


	@Override
	public void onSessionPreStart(ISession session) {
		// TODO Auto-generated method stub
		logger.debug("onSessionPreStart " + session.getId());
	}


	@Override
	public void onSessionStart(ISession session, Object data) {
		// TODO Auto-generated method stub
		logger.debug("onSessionStart " + session.getId());
	}


	@Override
	public void onSessionComplete(ISession session, Object data) {
		// TODO Auto-generated method stub
		logger.debug("onSessionComplete " + session.getId());
		
	}


	@Override
	public void onSessionFailed(ISession session, Object data) {
		// TODO Auto-generated method stub
		logger.debug("onSessionFailed " + session.getId());
	}


	@Override
	public void onSessionData(ISession session, Object data) {
		// TODO Auto-generated method stub
		logger.debug("onSessionData " + session.getId());
	}


	@Override
	public void onSessionProcessAdded(ISession session, Process proc) {
		// TODO Auto-generated method stub
		logger.debug("onSessionProcessAdded " + session.getId());
	}


	@Override
	public void onSessionProcessRemoved(ISession session, Process proc) {
		// TODO Auto-generated method stub
		logger.debug("onSessionProcessRemoved " + session.getId());
	}

}
