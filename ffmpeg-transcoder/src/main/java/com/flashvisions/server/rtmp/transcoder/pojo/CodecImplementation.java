package com.flashvisions.server.rtmp.transcoder.pojo;

import javax.validation.constraints.NotNull;

import com.flashvisions.server.rtmp.transcoder.interfaces.ICodecImplementation;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;

public class CodecImplementation implements ICodecImplementation {

	private static final String key = "-strict"; 
	
	@NotNull
	private Object value;
	
	public CodecImplementation(){
		
	}
	
	public CodecImplementation(Object value){
		this.value = value;
	}
	
	public CodecImplementation(CodecImplementation object){
		this.value = object.getValue();
	}
	
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return CodecImplementation.key;
	}

	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		// NO OP
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		this.value = value;
	}

	@Override
	public IParameter clone() {
		// TODO Auto-generated method stub
		return new CodecImplementation(this.getValue());
	}

}
