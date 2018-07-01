package com.flashvisions.server.rtmp.transcoder.pojo.video;

import com.flashvisions.server.rtmp.transcoder.interfaces.IKeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.pojo.base.PassThruObject;

public class KeyFrameInterval extends PassThruObject implements IKeyFrameInterval  {

	private Gop gop;
	private MinKeyframeInterval minimunInterval;
	
	
	public KeyFrameInterval(){
		
	}
	
	public KeyFrameInterval(IKeyFrameInterval object){
		this.setSameAsSource(object.getSameAsSource());
		this.gop = object.getGop();
		this.minimunInterval = object.getMinimunInterval();
	}
	
	public KeyFrameInterval(boolean sameAsSource){
		this.setSameAsSource(sameAsSource);
		this.gop = new Gop(0);
		this.minimunInterval = new MinKeyframeInterval(0);
	}

	public KeyFrameInterval(Gop gop, MinKeyframeInterval minimunInterval){
		this.gop = gop;
		this.minimunInterval = minimunInterval;
	}
	
	@Override
	public MinKeyframeInterval getMinimunInterval() 
	{
		return minimunInterval;
	}
	
	@Override
	public void setMinimunInterval(MinKeyframeInterval minimunInterval) 
	{
		this.minimunInterval = minimunInterval;
	}

	@Override
	public Gop getGop() 
	{
		return gop;
	}

	@Override
	public void setGop(Gop gop) 
	{
		this.gop = gop;
	}
}
