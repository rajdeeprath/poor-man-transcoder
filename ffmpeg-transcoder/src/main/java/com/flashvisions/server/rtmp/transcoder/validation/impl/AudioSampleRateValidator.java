package com.flashvisions.server.rtmp.transcoder.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioSampleRate;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.AudioSampleRates;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidAudioSampleRate;

public class AudioSampleRateValidator implements ConstraintValidator<ValidAudioSampleRate, IAudioSampleRate> {

	ValidAudioSampleRate constrain;
	
	@Override
	public void initialize(ValidAudioSampleRate constrain) {
		// TODO Auto-generated method stub
		this.constrain = constrain;
	}

	@Override
	public boolean isValid(IAudioSampleRate samplerate, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		String message = null;
		boolean valid = false;
		
		try
		{
			for (AudioSampleRates rate : AudioSampleRates.values()) {
				if(rate.getValue() == (Integer)samplerate.getValue()) {
					valid = true;
					break;
				}
			}
			
			if(!valid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message+" "+samplerate.getValue()).addConstraintViolation();
		    }
		}
		catch(Exception e)
		{
			valid = false;
		}
		
		return valid;
	}

}
