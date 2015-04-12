package com.flashvisions.server.rtmp.transcoder.interfaces;

import com.flashvisions.server.rtmp.transcoder.pojo.video.Gop;
import com.flashvisions.server.rtmp.transcoder.pojo.video.MinKeyframeInterval;

public interface IKeyFrameInterval extends IPassThru {
	public MinKeyframeInterval getMinimunInterval();
	public void setMinimunInterval(MinKeyframeInterval minimunInterval);
	public Gop getGop();
	public void setGop(Gop gop); 
}
