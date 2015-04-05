package com.flashvisions.server.rtmp.transcoder.pool;

import java.util.Enumeration;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.exception.MalformedTranscodeQueryException;
import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfig;
import com.flashvisions.server.rtmp.transcoder.pojo.Session;
import com.flashvisions.server.rtmp.transcoder.system.Server;
import com.flashvisions.server.rtmp.transcoder.utils.SessionUtil;

public class TranscodeSessionPool {
	  
	  private static Logger logger = LoggerFactory.getLogger(TranscodeSessionPool.class);
	
	  private long expirationTime = 30000;
	  private Server operatingServer; 
	  private Hashtable<ISession, Long> locked, unlocked;
	  private Hashtable<String, ISession> sessionMap;
	  
	  
	  public long getSessionExpirationTime() 
	  {
			return expirationTime;
	  }

	  public void setSessionExpirationTime(long expirationTime) 
	  {
			this.expirationTime = expirationTime;
	  }

	  public TranscodeSessionPool(Server operatingServer) 
	  {
		  	this.setOperatingServer(operatingServer);	    
		  	this.initHashMaps();
	  }
	  
	  public TranscodeSessionPool(Server operatingServer, long sessionExpireTime) 
	  {
			this.setOperatingServer(operatingServer);
			this.setSessionExpirationTime(sessionExpireTime);	
		    this.initHashMaps();
	  }
	  
	  private void initHashMaps()
	  {
		  this.locked = new Hashtable<ISession, Long>();
		  this.unlocked = new Hashtable<ISession, Long>();
		  this.sessionMap = new Hashtable<String, ISession>();
	  }
	  
	  // !!! for future use
	  protected ISession create(IMediaInput input, ITranscodeConfig transcode) throws MalformedTranscodeQueryException
	  {
		  // need to add template name to transcode object else session signature wont be created
		  ISession session = Session.Builder.newSession()
					.usingMediaInput(input)
					.usingTranscodeConfig(transcode)
					.forServer(String.valueOf(this.operatingServer).toLowerCase())
					.build();
		  
		  return session;
	  }

	  protected ISession create(IMediaInput input, String usingTemplate) throws MalformedTranscodeQueryException
	  {
		  logger.info("Creating new object");
		  ISession session = Session.Builder.newSession()
					.usingMediaInput(input)
					.usingTemplateFile(usingTemplate)
					.forServer(String.valueOf(this.operatingServer).toLowerCase())
					.build();
		  
		  logger.info("new session created " + session.getId());
		  return session;
	  }

	  // check if this available session is usable for our request
	  public boolean validate(IMediaInput input, String usingTemplate, ISession available)
	  {
		  String requestSignature = SessionUtil.generateSessionSignature(input.getSourcePath(), usingTemplate);
		  return available.getSignature().equals(requestSignature);
	  }

	  public void expire(ISession session)
	  {
		  logger.info("Expiring object " + session.getId());
		  sessionMap.remove(session.getSignature());
		  session.dispose();
		  session = null;
	  }

	  public synchronized ISession checkOut(IMediaInput input, String usingTemplate) throws TranscoderException 
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
	          if (validate(input, usingTemplate, t)) {
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
	    	t = create(input, usingTemplate);
	    	sessionMap.put(t.getSignature(), t);
		} 
	    catch (MalformedTranscodeQueryException e) 
	    {
	    	logger.info(e.getMessage());
	    	throw new TranscoderException("Unable to create a valid session " + e.getMessage());
		}
	    
	    locked.put(t, now);
	    logger.info("Checking out " + t.getId());
	    return (t);
	  }

	  public synchronized void checkIn(ISession session) {
		logger.info("Checking in object " + session.getId());  
	    locked.remove(session);
	    unlocked.put(session, System.currentTimeMillis());
	  }
	  
	  public ISession getSession(String signature)
	  {
		  return sessionMap.get(signature);
	  }

	  public Server getOperatingServer() {
		return operatingServer;
	  }

	  public void setOperatingServer(Server operatingServer) {
		this.operatingServer = operatingServer;
	  }
	}
