package com.flashvisions.server.rtmp.transcoder.exception;

public class MalformedTranscodeQueryException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1722410653282508611L;
	private Exception rootCauseException;
	private String message = null;
	 
    public MalformedTranscodeQueryException() {
        super();
    }
    
    public MalformedTranscodeQueryException(Exception rootCauseException) {
    	super(rootCauseException);
        this.setRootCauseException(rootCauseException);
        this.message = rootCauseException.getMessage();
    }
 
    public MalformedTranscodeQueryException(String message) {
        super(message);
        this.message = message;
    }
 
    public MalformedTranscodeQueryException(Throwable cause) {
        super(cause);
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }

	public Exception getRootCauseException() {
		return rootCauseException;
	}

	public void setRootCauseException(Exception rootCauseException) {
		this.rootCauseException = rootCauseException;
	}
}
