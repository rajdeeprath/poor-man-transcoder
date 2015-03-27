package com.flashvisions.server.rtmp.transcoder.vo;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscode;
import com.flashvisions.server.rtmp.transcoder.pojo.Flag;

public class Transcode implements ITranscode {
 
	public String label;	
	public String description;
	public ArrayList<Flag> inputflags;
	public IEncodeCollection encodes;
	
	
	public String getLabel() 
	{
		return label;
	}
	
	public void setLabel(String label) 
	{
		this.label = label;
	}
	
	public String getDescription() 
	{
		return description;
	}
	
	
	public void setDescription(String description) 
	{
		this.description = description;
	}
	
	public ArrayList<Flag> getInputflags() 
	{
		return inputflags;
	}
	
	public void setInputflags(ArrayList<Flag> inputflags) 
	{
		this.inputflags = inputflags;
	}
	
	public IEncodeCollection getEncodes() 
	{
		return encodes;
	}
	
	public void setEncodes(IEncodeCollection encodes) 
	{
		this.encodes = encodes;
	}	
	
}
