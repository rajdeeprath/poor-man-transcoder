package com.flashvisions.server.rtmp.transcoder.validation.interfaces;

import java.lang.annotation.Documented;  
import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;  

import javax.validation.Constraint;
import javax.validation.Payload;

import com.flashvisions.server.rtmp.transcoder.validation.impl.VideoKeyframeIntervalValidator;

@Documented  
@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.PARAMETER,ElementType.ANNOTATION_TYPE})  
@Constraint(validatedBy=VideoKeyframeIntervalValidator.class)
public @interface ValidVideoKeyframeInterval {
	String message() default "{com.flashvisions.server.rtmp.transcoder.validation.video.keyframeinterval.invalid.generic}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};  
}
