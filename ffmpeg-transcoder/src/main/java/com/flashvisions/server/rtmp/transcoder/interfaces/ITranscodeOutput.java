package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

public interface ITranscodeOutput {
	public ITranscoderResource getMediaOutput();
	public void setMediaOutput(ITranscoderResource output); 
	public ArrayList<IProperty> getOutputProperties();
	public void setOutputProperties(ArrayList<IProperty> extraProperties);
}
