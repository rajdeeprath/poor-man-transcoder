package com.flashvisions.server.rtmp.transcoder.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameRate;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidVideoFrameRate;

public class VideoFrameRateValidator implements ConstraintValidator<ValidVideoFrameRate, IFrameRate> {

	ValidVideoFrameRate constrain;
	
	@Override
	public void initialize(ValidVideoFrameRate constrain) {
		// TODO Auto-generated method stub
		this.constrain = constrain;
	}

	@Override
	public boolean isValid(IFrameRate framerate, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		String message = null;
		boolean valid = true;
		
		try
		{
			Integer fps = (Integer) framerate.getValue();
			
			if(fps<=0)
			{
				valid = false;
			}
			
			if(fps>=60)
			{
				valid = false;
				message = "{com.flashvisions.server.rtmp.transcoder.validation.video.framerate.invalid.toohigh}";
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
