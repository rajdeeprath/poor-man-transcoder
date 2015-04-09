package com.flashvisions.server.rtmp.transcoder.pojo.audio;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec;



public class AudioCodec extends Codec {

	private static Logger logger = LoggerFactory.getLogger(AudioCodec.class);
	
	public static enum Encoder {
		LIBSPEEX, LIBMP3LAME, AAC, LIBFDK_AAC, LIBVO_AACENC, LIBFAAC, LIBVORBIS, NELLYMOSER 
    }

	public AudioCodec(){
		super();
	}
	
	public AudioCodec(ICodec object) {
		super(object);
	}
	
	public AudioCodec(String name) {
		super(name);
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
				switch(Encoder.valueOf(codecName.toUpperCase()))
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
