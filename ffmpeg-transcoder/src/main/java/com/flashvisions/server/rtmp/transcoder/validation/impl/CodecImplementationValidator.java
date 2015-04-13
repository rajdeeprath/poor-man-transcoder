package com.flashvisions.server.rtmp.transcoder.validation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.flashvisions.server.rtmp.transcoder.interfaces.ICodecImplementation;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.CodecImplementations;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidCodecImplementation;

public class CodecImplementationValidator implements ConstraintValidator<ValidCodecImplementation, ICodecImplementation> {

	ValidCodecImplementation constrain;
	@Override
	public void initialize(ValidCodecImplementation constrain) {
		// TODO Auto-generated method stub
		this.constrain = constrain;
	}

	@Override
	public boolean isValid(ICodecImplementation implementation, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		String message = null;
		boolean valid = true;
		
		try
		{
			String implementationName = (String) implementation.getValue();
			
			if(!isInEnum(implementationName, CodecImplementations.class))
			{
				valid = false;
				message = "{com.flashvisions.server.rtmp.transcoder.validation.codec.implementation.invalid.generic}";
			}
			
			if(!valid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message + " " + implementationName).addConstraintViolation();
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
