package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class AudioCodec extends Codec implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6184248445560383627L;
	private static Logger logger = LoggerFactory.getLogger(AudioCodec.class);
	
	public static enum Type {
		LIBSPEEX, LIBMP3LAME, AAC, LIBFDK_AAC, LIBVO_AACENC, LIBFAAC, LIBVORBIS, NELLYMOSER 
    }

	public AudioCodec(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	

	public AudioCodec(String name, String implementation){
		super(name, implementation);
	}
	
	@Override
	protected boolean validateCodec(String codecName) 
	{
		boolean valid = false;
		
		try
		{
			super.validateCodec(codecName);
		}
		catch(Exception e)
		{
			try
			{
				switch(Type.valueOf(codecName.toUpperCase()))
				{	
					default:
					valid = true;
					break;
				}
			}
			catch(Exception ee)
			{
				super.setEnabled(false);
				logger.info("Invalid codec : " + codecName);
			}
		}
		
		return valid;
	}
}
