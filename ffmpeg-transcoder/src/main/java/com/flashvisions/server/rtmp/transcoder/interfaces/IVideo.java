package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.pojo.Codec.Implementation;

public interface IVideo extends IMutable {

	public ICodec getCodec();
	public void setCodec(ICodec codec);
	public IFrameSize getFramesize();
	public void setFramesize(IFrameSize framesize);
	public Implementation getCodecImplementation();
	public void setCodecImplementation(Implementation codecImplementation);
	public IFrameRate getFramerate();
	public void setFramerate(IFrameRate framerate);
	public IVideoBitrate getBitrate();
	public void setBitrate(IVideoBitrate bitrate);
	public IKeyFrameInterval getKeyFrameInterval();
	public void setKeyFrameInterval(IKeyFrameInterval keyFrameInterval);
	public IOverlayCollection getOverlays();
	public void setOverlays(IOverlayCollection overlays);
	public ArrayList<IArbitaryProperty> getExtraParams();
	public void setExtraParams(ArrayList<IArbitaryProperty> extraParams);
}
