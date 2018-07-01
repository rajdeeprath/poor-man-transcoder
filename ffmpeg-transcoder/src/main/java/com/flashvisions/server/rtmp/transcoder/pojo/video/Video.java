package com.flashvisions.server.rtmp.transcoder.pojo.video;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import com.flashvisions.server.rtmp.transcoder.interfaces.ICodecImplementation;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameSize;
import com.flashvisions.server.rtmp.transcoder.interfaces.IKeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.base.MutableObject;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidCodecImplementation;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidVideoCodec;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidVideoFrameRate;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidVideoFrameSize;
import com.flashvisions.server.rtmp.transcoder.validation.interfaces.ValidVideoKeyframeInterval;

public class Video extends MutableObject implements IVideo  {

	@NotNull
	@ValidVideoCodec
	private ICodec codec;
	
	@NotNull
	@ValidCodecImplementation
	private ICodecImplementation codecImplementation;
	
	@NotNull
	@ValidVideoFrameSize
	private IFrameSize framesize;
	
	@NotNull
	@ValidVideoFrameRate
	private IFrameRate framerate;
	
	@NotNull
	@ValidVideoBitrate
	private IVideoBitrate bitrate;
	
	@NotNull
	@ValidVideoKeyframeInterval
	private IKeyFrameInterval keyFrameInterval;
	
	@NotNull
	private ArrayList<IParameter> extraParams;
	
	@NotNull
	private ArrayList<IProperty> extraProperties;
	
	

	@Override
	public ICodec getCodec() {
		return codec;
	}

	@Override
	public void setCodec(ICodec codec) {
		this.codec = codec;
	}

	@Override
	public IFrameSize getFramesize() {
		return framesize;
	}

	@Override
	public void setFramesize(IFrameSize framesize) {
		this.framesize = framesize;
	}

	@Override
	public IFrameRate getFramerate() {
		return framerate;
	}

	@Override
	public void setFramerate(IFrameRate framerate) {
		this.framerate = framerate;
	}

	@Override
	public IVideoBitrate getBitrate() {
		return bitrate;
	}

	@Override
	public void setBitrate(IVideoBitrate bitrate) {
		this.bitrate = bitrate;
	}

	@Override
	public IKeyFrameInterval getKeyFrameInterval() {
		return keyFrameInterval;
	}

	@Override
	public void setKeyFrameInterval(IKeyFrameInterval keyFrameInterval) {
		this.keyFrameInterval = keyFrameInterval;
	}

	@Override
	public ArrayList<IParameter> getExtraParams() {
		return extraParams;
	}

	@Override
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

	@Override
	public ICodecImplementation getImplementation() {
		// TODO Auto-generated method stub
		return this.codecImplementation;
	}

	@Override
	public void setImplementation(ICodecImplementation implementation) {
		// TODO Auto-generated method stub
		this.codecImplementation = implementation;
	}
	
}
