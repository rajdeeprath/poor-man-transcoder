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
		String channelType = (String) channel.getValue();
		
		try
		{
			if(isInEnum(channelType, AudioChannelType.class))
			{
				valid = true;
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

	public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
		  for (E e : enumClass.getEnumConstants()) {
		    if(e.name().equalsIgnoreCase(value)) { return true; }
		  }
		  return false;
	}
}
