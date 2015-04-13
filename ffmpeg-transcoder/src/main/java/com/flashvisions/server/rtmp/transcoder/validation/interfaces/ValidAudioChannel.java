package com.flashvisions.server.rtmp.transcoder.validation.interfaces;

import java.lang.annotation.Documented;  
import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;  

import javax.validation.Constraint;
import javax.validation.Payload;

import com.flashvisions.server.rtmp.transcoder.validation.impl.AudioChannelValidator;

@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.PARAMETER,ElementType.ANNOTATION_TYPE})  
@Constraint(validatedBy=AudioChannelValidator.class)
public @interface ValidAudioChannel {
	String message() default "{com.flashvisions.server.rtmp.transcoder.validation.audio.channel.invalid.generic}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};  
}
