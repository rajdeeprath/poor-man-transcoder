package com.flashvisions.server.rtmp.transcoder.pojo.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.pojo.Codec;


public class VideoCodec extends Codec {
	
	private static Logger logger = LoggerFactory.getLogger(VideoCodec.class);
	
	
	public static enum Type {
		LIBX264, FLV, LIBTHEORA, H263, FLASHSV, MPEG4
    }
	
	public VideoCodec(){
		super();
	}

	public VideoCodec(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	

	public VideoCodec(String name, String implementation){
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
