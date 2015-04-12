package com.flashvisions.server.rtmp.transcoder.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidCodecImplementation;

public class CodecImplementationValidator implements ConstraintValidator<ValidCodecImplementation, ICodec> {

	ValidCodecImplementation constrain;
	@Override
	public void initialize(ValidCodecImplementation constrain) {
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
			
			if(!valid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		    }
		}
		catch(Exception e)
		{
			codec.setEnabled(false);
			valid = false;
		}
		
		return valid;
	}

	public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
		  for (E e : enumClass.getEnumConstants()) {
		    if(e.name().equals(value)) { return true; }
		  }
		  return false;
		}
}
