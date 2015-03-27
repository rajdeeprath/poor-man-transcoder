package com.flashvisions.server.rtmp.transcoder.exception;

public class TranscodeConfigurationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Exception rootCauseException;
	private String message = null;
	 
    public TranscodeConfigurationException() {
        super();
    }
    
    public TranscodeConfigurationException(Exception rootCauseException) {
    	super(rootCauseException);
        this.setRootCauseException(rootCauseException);
        this.message = rootCauseException.getMessage();
    }
 
    public TranscodeConfigurationException(String message) {
        super(message);
        this.message = message;
    }
 
    public TranscodeConfigurationException(Throwable cause) {
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
