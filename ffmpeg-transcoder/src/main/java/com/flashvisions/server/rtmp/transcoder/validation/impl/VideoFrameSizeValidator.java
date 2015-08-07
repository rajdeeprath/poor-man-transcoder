package com.flashvisions.server.rtmp.transcoder.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameSize;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidVideoFrameSize;

public class VideoFrameSizeValidator implements ConstraintValidator<ValidVideoFrameSize, IFrameSize> {

	ValidVideoFrameSize constrain;
	@Override
	public void initialize(ValidVideoFrameSize constrain) {
		// TODO Auto-generated method stub
		this.constrain = constrain;
	}

	@Override
	public boolean isValid(IFrameSize framesize, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		String message = null;
		boolean valid = true;
		
		try
		{
			// if follow source values
			if(framesize.getSameAsSource())
			return valid;
			
			
			Integer width = (Integer) framesize.getWidth();
			Integer height = (Integer) framesize.getHeight();
			
			if(width<=0 || height<=0)
			{
				valid = false;
			}
			
			if(width>=3840 || height>=2040)
			{
				valid = false;
				message = "{com.flashvisions.server.rtmp.transcoder.validation.video.framesize.invalid.outofrange}";
			}
			
			if(height % 2 != 0 )
			{
				valid = false;
				message = "{com.flashvisions.server.rtmp.transcoder.validation.video.framesize.invalid.height}";
			}
				
			
			if(!valid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		    }
		}
		catch(Exception e)
		{
			valid = false;
		}
		
		return valid;
	}
}
