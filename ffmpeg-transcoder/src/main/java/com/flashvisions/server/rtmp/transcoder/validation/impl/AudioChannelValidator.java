package com.flashvisions.server.rtmp.transcoder.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.flashvisions.server.rtmp.transcoder.interfaces.IAudioChannel;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.AudioChannelType;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidAudioChannel;

public class AudioChannelValidator implements ConstraintValidator<ValidAudioChannel, IAudioChannel> {

	ValidAudioChannel constrain;
	
	@Override
	public void initialize(ValidAudioChannel constrain) {
		// TODO Auto-generated method stub
		this.constrain = constrain;
	}

	@Override
	public boolean isValid(IAudioChannel channel, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		String message = null;
		boolean valid = false;
		
		try
		{
			for (AudioChannelType channelType : AudioChannelType.values()) {
				System.out.print(channelType.getCode());
			}
			
			if(!valid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message+" "+channel.getValue()).addConstraintViolation();
		    }
		}
		catch(Exception e)
		{
			valid = false;
		}
		
		return valid;
	}

}
