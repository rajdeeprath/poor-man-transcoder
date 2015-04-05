package com.flashvisions.server.rtmp.transcoder.pojo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.flashvisions.server.rtmp.transcoder.interfaces.IEncodeCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfig;

public class Transcode extends Mutable implements ITranscodeConfig, Serializable  {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 8925017251384648786L;
	
	public String label;	
	public String description;
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
	
	public IEncodeCollection getEncodes() 
	{
		return encodes;
	}
	
	public void setEncodes(IEncodeCollection encodes) 
	{
		this.encodes = encodes;
	}	
	
	public ITranscodeConfig clone()
	{
		ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        ByteArrayOutputStream bos = null;
        Transcode clone = null;
        
        try
        {
	        bos = new ByteArrayOutputStream(); 
	        oos = new ObjectOutputStream(bos); 
	        oos.writeObject(this);   
	        oos.flush();               
	        ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray()); 
	        ois = new ObjectInputStream(bin);
	        clone = (Transcode) ois.readObject(); 
        }
        catch(Exception e)
        {
        	e.printStackTrace();
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
