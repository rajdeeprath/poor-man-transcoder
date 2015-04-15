package com.flashvisions.server.rtmp.transcoder.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.CodecOptions;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.VideoCodecs;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidVideoCodec;

public class VideoCodecValidator implements ConstraintValidator<ValidVideoCodec, ICodec> {

	ValidVideoCodec constrain;
	@Override
	public void initialize(ValidVideoCodec constrain) {
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
			
			if(!isInEnum(codecName, CodecOptions.class) && !isInEnum(codecName, VideoCodecs.class))
			{
				valid = false;
				message = "{com.flashvisions.server.rtmp.transcoder.validation.video.codec.invalid.generic}";
			}
			
			if(isEnum(codecName, CodecOptions.COPY))
			codec.setSameAsSource(true);
			
			if(isEnum(codecName, CodecOptions.DISABLE))
			codec.setEnabled(false);
			
			
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

	public boolean isEnum(String element, Enum<?> e)
	{
		return element.toLowerCase().equals(e.name().toLowerCase());
	}
}
