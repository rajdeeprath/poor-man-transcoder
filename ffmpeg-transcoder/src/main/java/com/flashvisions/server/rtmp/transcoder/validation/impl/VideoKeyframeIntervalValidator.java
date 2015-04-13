package com.flashvisions.server.rtmp.transcoder.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.flashvisions.server.rtmp.transcoder.interfaces.IKeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.pojo.video.Gop;
import com.flashvisions.server.rtmp.transcoder.pojo.video.MinKeyframeInterval;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidVideoKeyframeInterval;

public class VideoKeyframeIntervalValidator implements ConstraintValidator<ValidVideoKeyframeInterval, IKeyFrameInterval> {

	ValidVideoKeyframeInterval constrain;
	
	@Override
	public void initialize(ValidVideoKeyframeInterval constrain) {
		// TODO Auto-generated method stub
		this.constrain = constrain;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean isValid(IKeyFrameInterval kfi, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		String message = null;
		boolean valid = true;
		
		try
		{
			Gop gop = kfi.getGop();
			MinKeyframeInterval minkfi = kfi.getMinimunInterval();
				
			
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
