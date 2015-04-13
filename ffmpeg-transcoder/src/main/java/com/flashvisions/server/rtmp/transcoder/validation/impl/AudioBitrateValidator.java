package com.flashvisions.server.rtmp.transcoder.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioBitrate;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidAudioBitrate;

public class AudioBitrateValidator implements ConstraintValidator<ValidAudioBitrate, IAudioBitrate> {

	ValidAudioBitrate constrain;
	
	@Override
	public void initialize(ValidAudioBitrate constrain) {
		// TODO Auto-generated method stub
		this.constrain = constrain;
	}

	@Override
	public boolean isValid(IAudioBitrate bitrate, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		String message = null;
		boolean valid = true;
		
		try
		{
			
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
