package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

public interface ITranscodeOutput {
	public IMediaOutput getMediaOutput();
	public void setMediaOutput(IMediaOutput output); 
	public ArrayList<IProperty> getOutputProperties();
	public void setOutputProperties(ArrayList<IProperty> extraProperties);
}
