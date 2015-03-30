package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.pojo.Flag;


public interface ITranscode extends IMutable {

	public String getLabel() ;
	public void setLabel(String label);
	public String getDescription();
	public void setDescription(String description);
	public ArrayList<Flag> getInputflags();
	public void setInputflags(ArrayList<Flag> inputflags);
	public IEncodeCollection getEncodes();
	public void setEncodes(IEncodeCollection encodes);
	public ITranscode clone(); 
}
