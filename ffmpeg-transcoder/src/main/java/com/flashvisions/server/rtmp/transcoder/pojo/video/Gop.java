package com.flashvisions.server.rtmp.transcoder.pojo.video;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.pojo.Parameter;

public class Gop extends Parameter implements IParameter {

	private static final String key = "-g"; 
	
	@NotNull
    @Range(min = 0, max = 120)
	private Object value;
	
	public Gop(){
		
	}
	
	public Gop(Object value){
		this.value = value;	
	}
	
	public Gop(Gop object){
		this.value = (int) object.getValue();
	}
	
	public Object getValue()
	{
		return this.value;
	}
	
	public String getKey()
	{
		return Gop.key;
	}

	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		// NO OP
	}

	@Override
	public void setValue(Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IParameter clone() {
		// TODO Auto-generated method stub
		return new Gop(this.value);
	}
}
