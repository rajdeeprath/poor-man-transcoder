package com.flashvisions.server.rtmp.transcoder.pool;

import java.util.Enumeration;
import java.util.Hashtable;

import com.flashvisions.server.rtmp.transcoder.exception.MalformedTranscodeQueryException;
import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.pojo.Session;
import com.flashvisions.server.rtmp.transcoder.system.Server;
import com.flashvisions.server.rtmp.transcoder.utils.SessionUtil;

public class TranscodeSessionPool {
	  
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
			this.locked = new Hashtable<ISession, Long>();
		    this.unlocked = new Hashtable<ISession, Long>();
	  }
	  
	  public TranscodeSessionPool(Server operatingServer, long sessionExpireTime) 
	  {
			this.setOperatingServer(operatingServer);
			this.setSessionExpirationTime(sessionExpireTime);	
		    this.locked = new Hashtable<ISession, Long>();
		    this.unlocked = new Hashtable<ISession, Long>();
	  }

	  protected ISession create(IMediaInput input, String usingTemplate) throws MalformedTranscodeQueryException
	  {
		  ISession session = Session.Builder.newSession()
					.usingMediaInput(input)
					.usingTemplateFile(usingTemplate)
					.forServer(String.valueOf(this.operatingServer).toLowerCase())
					.build();
		  
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
	    	throw new TranscoderException("Unable to create a valid session " + e.getMessage());
		}
	    
	    locked.put(t, now);
	    return (t);
	  }

	  public synchronized void checkIn(ISession session) {
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
