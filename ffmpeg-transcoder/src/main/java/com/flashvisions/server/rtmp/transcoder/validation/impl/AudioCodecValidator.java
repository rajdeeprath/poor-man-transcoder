package com.flashvisions.server.rtmp.transcoder.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.AudioCodecs;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.CodecOptions;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidAudioCodec;

public class AudioCodecValidator implements ConstraintValidator<ValidAudioCodec, ICodec> {

	ValidAudioCodec constrain;
	
	@Override
	public void initialize(ValidAudioCodec constrain) {
		// TODO Auto-generated method stub
		this.constrain = constrain;
	}

	@Override
	public boolean isValid(ICodec codec, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		String message = null;
		boolean valid = true;
		
		try
		{
			String codecName = (String) codec.getValue();
			
			if(!isInEnum(codecName, CodecOptions.class) && !isInEnum(codecName, AudioCodecs.class))
			{
				valid = false;
				message = "{com.flashvisions.server.rtmp.transcoder.validation.audio.codec.invalid.generic}";
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

	public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
		  for (E e : enumClass.getEnumConstants()) {
		    if(e.name().equalsIgnoreCase(value)) { return true; }
		  }
		  return false;
		}
}
