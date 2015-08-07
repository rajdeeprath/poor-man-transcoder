package com.flashvisions.server.rtmp.transcoder.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidVideoBitrate;

public class VideoBitrateValidator implements ConstraintValidator<ValidVideoBitrate, IVideoBitrate> {

	ValidVideoBitrate constrain;
	@Override
	public void initialize(ValidVideoBitrate constrain) {
		// TODO Auto-generated method stub
		this.constrain = constrain;
	}

	@Override
	public boolean isValid(IVideoBitrate videobitrate, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		String message = null;
		boolean valid = true;
		
		try
		{
			// if follow source values
			if(videobitrate.getSameAsSource())
			return valid;
			
			Integer min = (Integer) videobitrate.getMinimum().getValue();
			Integer max = (Integer) videobitrate.getMaximum().getValue();
			Integer avg = (Integer) videobitrate.getAverage().getValue();
			Integer buff = (Integer) videobitrate.getDeviceBuffer().getValue();
			
			
			if(min == 0 && max == 0 && avg == 0){
			message = "{com.flashvisions.server.rtmp.transcoder.validation.video.bitrate.invalid.all}";
			valid = false;
			}
			
			else if(max > 0 && buff <= 0){
			message = "{com.flashvisions.server.rtmp.transcoder.validation.video.bitrate.invalid.devicebuffer}";
			valid = false;
			}
			
			else if(min > (max)){
			message = "{com.flashvisions.server.rtmp.transcoder.validation.video.bitrate.invalid.min.max.error}";
			valid = false;
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
