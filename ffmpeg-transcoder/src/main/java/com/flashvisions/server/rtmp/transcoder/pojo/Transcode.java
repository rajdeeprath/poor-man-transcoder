package com.flashvisions.server.rtmp.transcoder.pojo;

import javax.validation.constraints.NotNull;

import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscode;
import com.flashvisions.server.rtmp.transcoder.pojo.base.MutableObject;

public class Transcode extends MutableObject implements ITranscode  {
	
	@NotNull
	private String label;
	
	@NotNull
	private String description;
	
	@NotNull
	private IEncodeCollection encodes;
	
	
	private Transcode(Builder builder){
		this.label = builder.label;
		this.description = builder.description;
		this.encodes = builder.encodes;
		this.setEnabled(builder.enabled);
	}
	
	@Override
	public String getLabel() 
	{
		return label;
	}
	
	public void setLabel(String label) 
	{
		this.label = label;
	}
	
	@Override
	public String getDescription() 
	{
		return description;
	}	
	
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	@Override
	public IEncodeCollection getEncodes() 
	{
		return encodes;
	}
	
	public void setEncodes(IEncodeCollection encodes) 
	{
		this.encodes = encodes;
	}
	
	public static class Builder
	{
		private boolean enabled;
		private String label;	
		private String description;
		private IEncodeCollection encodes;
		
		public static Builder newTranscode(){
			return new Builder();
		}
		
		public Builder asValid(boolean valid)
		{
			this.enabled = valid;
			return this;
		}
		
		public Builder withLabel(String label){
			this.label = label;
			return this;
		}
		
		public Builder withDescription(String description){
			this.description = description;
			return this;
		}
		
		public Builder usingEncodes(IEncodeCollection encodes){
			this.encodes = encodes;
			return this;
		}
		
		public ITranscode build()
		{
			return new Transcode(this);
		}
	}
}
