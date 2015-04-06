package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMutable;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruProperty;

public class Codec extends PassThruProperty implements ICodec, IMutable {
	
	public static enum Type {
        PASSTHRU, DISABLE
    }
	
	public static enum Implementation {
		VERY, STRICT, NORMAL, UNOFFICIAL, EXPERIMENTAL
    }
	
	private String name;
	private Implementation implementation = null;
	private boolean enabled = true;
	
	public Codec(){
		
	}

	public Codec(String name){
		if(validateCodec(name))
		this.name = name;
	}
	
	public Codec(String name, String implementation){
		if(validateCodec(name)) this.name = name;
		validateImplementation(implementation); 
	}

	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public Implementation getImplementation() {
		return implementation;
	}

	public void setImplementation(Codec.Implementation implementation) {
		this.implementation = implementation;
	}
	
	@Override
	public boolean getEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		this.enabled = enabled;
	}
	
	protected boolean validateCodec(String codecName)
	{
		boolean valid = false;
		
		switch(Type.valueOf(codecName.toUpperCase()))
		{
			case DISABLE:
			this.setEnabled(false);
			break;
			case PASSTHRU:
			this.setSameAsSource(true);	
			break;
			default:
			valid = true;
			break;
		}
		
		return valid;
	}
	
	protected void validateImplementation(String implementation)
	{
		switch(Implementation.valueOf(implementation.toUpperCase()))
		{
			case VERY:
			setImplementation(Implementation.VERY);
			break;
			
			case EXPERIMENTAL:
			setImplementation(Implementation.EXPERIMENTAL);
			break;
			
			case STRICT:
			setImplementation(Implementation.STRICT);
			break;
			
			case UNOFFICIAL:
			setImplementation(Implementation.UNOFFICIAL);
			break;
			
			case NORMAL:
			default:
			setImplementation(Implementation.NORMAL);
			break;
		}
	}	

}
