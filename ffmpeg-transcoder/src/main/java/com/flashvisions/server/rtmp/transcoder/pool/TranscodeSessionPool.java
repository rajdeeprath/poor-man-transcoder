package com.flashvisions.server.rtmp.transcoder.pool;

import java.util.Enumeration;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.exception.MalformedTranscodeQueryException;
import com.flashvisions.server.rtmp.transcoder.exception.MediaIdentifyException;
import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscode;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pojo.Session;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Server;
import com.flashvisions.server.rtmp.transcoder.utils.SessionUtil;

public class TranscodeSessionPool {
	  
	  private static Logger logger = LoggerFactory.getLogger(TranscodeSessionPool.class);
	
	  private long expirationTime = 30000;
	  private Server operatingServer; 
	  private Hashtable<ISession, Long> locked, unlocked;
	  private Hashtable<String, ISession> sessionMap;
	  private Hashtable<Long, ISession> sessionIdMap;
	  private Hashtable<ISession, String> templateMap;
	  
	  
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
		  this.templateMap = new Hashtable<ISession, String>();
		  this.sessionIdMap = new Hashtable<Long, ISession>();
	  }
	  
	  // !!! for future use
	  protected ISession create(ITranscoderResource input, ITranscode transcode) throws MalformedTranscodeQueryException, MediaIdentifyException
	  {
		  // need to add template name to transcode object else session signature wont be created
		  ISession session = Session.Builder.newSession()
					.usingMediaInput(input)
					.usingTranscodeConfig(transcode)
					.forServer(String.valueOf(this.operatingServer).toLowerCase())
					.build();
		  
		  return session;
	  }

	  protected ISession create(ITranscoderResource input, String usingTemplate) throws MalformedTranscodeQueryException, MediaIdentifyException
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
	  public boolean validate(ITranscoderResource input, String usingTemplate, ISession available)
	  {
		  String requestSignature = SessionUtil.generateSessionSignature(input.getSourcePath(), usingTemplate);
		  return getSignature(available).equals(requestSignature);
	  }

	  public void expire(ISession session)
	  {
		  logger.info("Expiring object " + session.getId());
		  sessionMap.remove(getSignature(session));
		  sessionIdMap.remove(session.getId());
		  templateMap.remove(session);
		  session.dispose();
		  session = null;
	  }
	  
	  public String getSignature(ISession session)
	  {
		  String template = templateMap.get(session);
		  String input = session.getInputSource().getSourcePath();
		  return SessionUtil.generateSessionSignature(input, template);
	  }

	  public synchronized ISession checkOut(ITranscoderResource input, String usingTemplate) throws TranscoderException 
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
	    	templateMap.put(t, usingTemplate);
	    	sessionMap.put(getSignature(t), t);
	    	sessionIdMap.put(t.getId(), t);
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
		logger.info("Checking in object " + session.getId());  
	    locked.remove(session);
	    unlocked.put(session, System.currentTimeMillis());
	  }
	  
	  public ISession getSession(String signature)
	  {
		  return sessionMap.get(signature);
	  }
	  
	  public ISession getSession(long id)
	  {
		  return sessionIdMap.get(id);
	  }

	  public Server getOperatingServer() {
		return operatingServer;
	  }

	  public void setOperatingServer(Server operatingServer) {
		this.operatingServer = operatingServer;
	  }
	}
