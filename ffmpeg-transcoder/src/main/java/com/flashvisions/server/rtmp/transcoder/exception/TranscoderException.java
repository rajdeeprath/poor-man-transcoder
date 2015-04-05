package com.flashvisions.server.rtmp.transcoder.exception;

public class TranscoderException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1722410653282508611L;
	private Exception rootCauseException;
	private String message = null;
	 
    public TranscoderException() {
        super();
    }
    
    public TranscoderException(Exception rootCauseException) {
    	super(rootCauseException);
        this.setRootCauseException(rootCauseException);
        this.message = rootCauseException.getMessage();
    }
 
    public TranscoderException(String message) {
        super(message);
        this.message = message;
    }
 
    public TranscoderException(Throwable cause) {
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
