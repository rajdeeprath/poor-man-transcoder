package com.flashvisions.server.rtmp.transcoder.pool;

import java.util.Enumeration;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.context.TranscodeRequest;
import com.flashvisions.server.rtmp.transcoder.context.TranscoderContext;
import com.flashvisions.server.rtmp.transcoder.exception.MalformedTranscodeQueryException;
import com.flashvisions.server.rtmp.transcoder.exception.MediaIdentifyException;
import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISessionObserver;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pojo.Session;
import com.flashvisions.server.rtmp.transcoder.system.Globals;
import com.flashvisions.server.rtmp.transcoder.utils.SessionUtil;

public class TranscodeSessionPool implements ISessionObserver {
	  
	  private static Logger logger = LoggerFactory.getLogger(TranscodeSessionPool.class);
	
	  private long expirationTime = 30000;
	  private TranscoderContext context; 
	  private Hashtable<ISession, Long> locked, unlocked;
	  private Hashtable<String, ISession> sessionSignatureTable;
	  private Hashtable<ISession, String> templateTable;
	  
	  
	  public long getSessionExpirationTime() 
	  {
			return expirationTime;
	  }

	  private void setSessionExpirationTime(long expirationTime) 
	  {
			this.expirationTime = expirationTime;
	  }

	  public TranscodeSessionPool(TranscoderContext context) 
	  {	    
		  	this.context = context;
		  	this.initialize();
	  }
	  
	  public TranscodeSessionPool(TranscoderContext context, long sessionExpireTime) 
	  {
		  	this.context = context;
			this.setSessionExpirationTime(sessionExpireTime);	
		    this.initialize();
	  }
	  
	  private void initialize()
	  {
		  this.locked = new Hashtable<ISession, Long>();
		  this.unlocked = new Hashtable<ISession, Long>();
		  this.sessionSignatureTable = new Hashtable<String, ISession>();
		  this.templateTable = new Hashtable<ISession, String>();
	  }

	  protected ISession create(ITranscoderResource input, TranscodeRequest request) throws MalformedTranscodeQueryException, MediaIdentifyException
	  {
		  String hostserver = this.context.getOperatingMediaServer().toLowerCase();
		  String workingDirectory = (request.getWorkingDirectory() == null || request.getWorkingDirectory() == "")?Globals.getEnv(Globals.Vars.WORKING_DIRECTORY):request.getWorkingDirectory();
		  ISession session = Session.Builder.newSession()
					.usingMediaInput(input)
					.usingTemplateFile(request.getTemplateFileName())
					.inWorkingDirectory(workingDirectory)
					.cleanUpOnExit(request.isCleanUpSegmentsOnExit())
					.forServer(hostserver)
					.build();
		  
		  session.registerObserver(this);
		  templateTable.put(session, request.getTemplateFileName());
		  sessionSignatureTable.put(getSignature(session), session);
		  
		  logger.info("new session created " + session.getId());		  
		  return session;
	  }

	  // check if this available session is usable for our request
	  public boolean validate(ITranscoderResource input, String usingTemplate, ISession available)
	  {
		  String requestSignature = SessionUtil.generateSessionSignature(input.getSourcePath(), usingTemplate);
		  return getSignature(available).equals(requestSignature);
	  }

	  public void expire(ISession session)
	  {
		  logger.info("Expiring session " + session.getId());
		  session.removeObserver(this);
		  sessionSignatureTable.remove(getSignature(session));
		  templateTable.remove(session);
		  session.dispose();
		  session = null;
	  }
	  
	  public String getSignature(ISession session)
	  {
		  String template = templateTable.get(session);
		  String input = session.getInputSource().getSourcePath();
		  return SessionUtil.generateSessionSignature(input, template);
	  }

	  public synchronized ISession checkOut(ITranscoderResource input, TranscodeRequest request) throws TranscoderException 
	  {
		  
	    long now = System.currentTimeMillis();
	    ISession t;
	    
	    if (unlocked.size() > 0) {
	      Enumeration<ISession> e = unlocked.keys();
	      while (e.hasMoreElements()) {
	        t = e.nextElement();
	        if ((now - unlocked.get(t)) > expirationTime) {
	          // object has expired
	          unlocked.remove(t);
	          expire(t);
	          t = null;
	        } else {
	          if (validate(input, request.getTemplateFileName(), t)) {
	            unlocked.remove(t);
	            locked.put(t, now);
	            return (t);
	          } else {
	            // object failed validation
	            unlocked.remove(t);
	            expire(t);
	            t = null;
	          }
	        }
	      }
	    }
	   
	    
	    // no objects available, create a new one
	    try 
	    {
	    	t = create(input, request);
	    	
		} 
	    catch (MalformedTranscodeQueryException | MediaIdentifyException e) 
	    {
	    	throw new TranscoderException("Unable to create a valid session " + e.getMessage());
		}
	    
	    locked.put(t, now);
	    logger.info("Checking out " + t.getId());
	    return (t);
	  }

	  public synchronized void checkIn(ISession session) {
		logger.info("Checking in " + session.getId());  
	    locked.remove(session);
	    unlocked.put(session, System.currentTimeMillis());
	  }
	  
	  public ISession getSession(String signature)
	  {
		  return sessionSignatureTable.get(signature);
	  }
	  
	  /*****************************************************
	  ********** ISession event Handlers *******************
	  *****************************************************/

	  @Override
	  public void onSessionPreStart(ISession session) 
	  {
			// TODO Auto-generated method stub	
		  logger.info("Session prestart " + session.getId());
		  
		  
	  }
	  
	  @Override
	  public void onSessionStart(ISession session, Object data) 
	  {
			// TODO Auto-generated method stub
		  logger.info("Session started " + session.getId());
		  logger.info("Waiting to read stream source");
	  }
	  
	
	  @Override
	  public void onSessionComplete(ISession session, Object data) {
			// TODO Auto-generated method stub
		  logger.info("Session complete " + session.getId());
		  checkIn(session);
	  }
	
	  
	  @Override
	  public void onSessionFailed(ISession session, Object data) {
			// TODO Auto-generated method stub
		  logger.info("Session failed. Could not read stream data properly.");
		  checkIn(session);
	  }
	
	  @Override
	  public void onSessionData(ISession session, Object data) {
			// TODO Auto-generated method stub	
	  }

	  @Override
	  public void onSessionProcessAdded(ISession session, Process proc) {
		// TODO Auto-generated method stub
	  }

	  @Override
	  public void onSessionProcessRemoved(ISession session, Process proc) {
		// TODO Auto-generated method stub
	  }

	  public TranscoderContext getContext() {
			return context;
	  }
		
	  public void setContext(TranscoderContext context) {
			this.context = context;
	  }
}
