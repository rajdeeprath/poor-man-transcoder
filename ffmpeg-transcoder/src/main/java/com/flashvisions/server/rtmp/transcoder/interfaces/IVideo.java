package com.flashvisions.server.rtmp.transcoder.interfaces;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.pojo.FrameRate;
import com.flashvisions.server.rtmp.transcoder.pojo.FrameSize;
import com.flashvisions.server.rtmp.transcoder.pojo.KeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.pojo.VideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.VideoProperty;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec.Implementation;

public interface IVideo extends IMutable {

	public ICodec getCodec();
	public void setCodec(ICodec codec);
	public FrameSize getFramesize();
	public void setFramesize(FrameSize framesize);
	public Implementation getCodecImplementation();
	public void setCodecImplementation(Implementation codecImplementation);
	public FrameRate getFramerate();
	public void setFramerate(FrameRate framerate);
	public VideoBitrate getBitrate();
	public void setBitrate(VideoBitrate bitrate);
	public KeyFrameInterval getKeyFrameInterval();
	public void setKeyFrameInterval(KeyFrameInterval keyFrameInterval);
	public ArrayList<Object> getOverlays();
	public void setOverlays(ArrayList<Object> overlays);
	public ArrayList<VideoProperty> getExtraParams();
	public void setExtraParams(ArrayList<VideoProperty> extraParams);
}
