package com.flashvisions.server.rtmp.transcoder.vo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscode;
import com.flashvisions.server.rtmp.transcoder.pojo.Flag;
import com.flashvisions.server.rtmp.transcoder.pojo.base.Mutable;

public class Transcode extends Mutable implements ITranscode, Cloneable {
 
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
	
	@Override
	protected Object clone() throws CloneNotSupportedException 
	{
		ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        ByteArrayOutputStream bos = null;
        ITranscode clone = null;
        
        try
        {
	        bos = new ByteArrayOutputStream(); 
	        oos = new ObjectOutputStream(bos); 
	        oos.writeObject(this);   
	        oos.flush();               
	        ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray()); 
	        ois = new ObjectInputStream(bin);
	        clone = (ITranscode) ois.readObject(); 
        }
        catch(Exception e)
        {
        	clone = null;
        }
        finally
        {
        	if(ois != null)
        	ois = null;
        	
        	if(oos != null)
        	oos = null;
        	
        	if(bos != null)
        	bos = null;
        }
        
        return clone;
	}
}
