package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;


public interface IVideo extends IMutable {

	public ICodec getCodec();
	public void setCodec(ICodec codec);
	public ICodecImplementation getImplementation();
	public void setImplementation(ICodecImplementation implementation);
	public IFrameSize getFramesize();
	public void setFramesize(IFrameSize framesize);
	public IFrameRate getFramerate();
	public void setFramerate(IFrameRate framerate);
	public IVideoBitrate getBitrate();
	public void setBitrate(IVideoBitrate bitrate);
	public IKeyFrameInterval getKeyFrameInterval();
	public void setKeyFrameInterval(IKeyFrameInterval keyFrameInterval);
	public ArrayList<IParameter> getExtraParams();
	public void setExtraParams(ArrayList<IParameter> extraParams);
	public ArrayList<IProperty> getExtraProperties();
	public void setExtraProperties(ArrayList<IProperty> extraProperties);
}
