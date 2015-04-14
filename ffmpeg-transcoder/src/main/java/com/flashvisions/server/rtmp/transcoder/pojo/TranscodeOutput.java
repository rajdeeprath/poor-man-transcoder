package com.flashvisions.server.rtmp.transcoder.pojo;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;

public class TranscodeOutput implements ITranscodeOutput{
	
	private ITranscoderResource output;
	public ArrayList<IProperty> extraProperties;


	@Override
	public ITranscoderResource getMediaOutput() {
		// TODO Auto-generated method stub
		return output;
	}

	@Override
	public void setMediaOutput(ITranscoderResource output) {
		// TODO Auto-generated method stub
		this.output = output;
	}

	@Override
	public ArrayList<IProperty> getOutputProperties() {
		// TODO Auto-generated method stub
		return extraProperties;
	}

	@Override
	public void setOutputProperties(ArrayList<IProperty> extraProperties) {
		// TODO Auto-generated method stub
		this.extraProperties = extraProperties;
	}

}
