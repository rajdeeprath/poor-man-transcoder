package com.flashvisions.server.rtmp.transcoder.pojo.video;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameSize;
import com.flashvisions.server.rtmp.transcoder.interfaces.IKeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.base.MutableObject;

public class Video extends MutableObject implements IVideo  {

	private ICodec codec;
	private IFrameSize framesize;
	private IFrameRate framerate;
	private IVideoBitrate bitrate;
	private IKeyFrameInterval keyFrameInterval;
	private ArrayList<IParameter> extraParams;
	private ArrayList<IProperty> extraProperties;
	
	

	public ICodec getCodec() {
		return codec;
	}

	public void setCodec(ICodec codec) {
		this.codec = codec;
	}

	public IFrameSize getFramesize() {
		return framesize;
	}

	public void setFramesize(IFrameSize framesize) {
		this.framesize = framesize;
	}

	public IFrameRate getFramerate() {
		return framerate;
	}

	public void setFramerate(IFrameRate framerate) {
		this.framerate = framerate;
	}

	public IVideoBitrate getBitrate() {
		return bitrate;
	}

	public void setBitrate(IVideoBitrate bitrate) {
		this.bitrate = bitrate;
	}

	public IKeyFrameInterval getKeyFrameInterval() {
		return keyFrameInterval;
	}

	public void setKeyFrameInterval(IKeyFrameInterval keyFrameInterval) {
		this.keyFrameInterval = keyFrameInterval;
	}

	public ArrayList<IParameter> getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(ArrayList<IParameter> extraParams) {
		this.extraParams = extraParams;
	}

	@Override
	public ArrayList<IProperty> getExtraProperties() {
		// TODO Auto-generated method stub
		return extraProperties;
	}

	@Override
	public void setExtraProperties(ArrayList<IProperty> extraProperties) {
		// TODO Auto-generated method stub
		this.extraProperties = extraProperties;
	}

	
}
