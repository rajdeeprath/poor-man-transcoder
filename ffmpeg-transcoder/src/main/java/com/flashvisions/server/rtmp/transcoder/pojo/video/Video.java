package com.flashvisions.server.rtmp.transcoder.pojo.video;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IParam;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameRate;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFrameSize;
import com.flashvisions.server.rtmp.transcoder.interfaces.IKeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.interfaces.IOverlayCollection;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec.Implementation;
import com.flashvisions.server.rtmp.transcoder.pojo.base.Mutable;

public class Video extends Mutable implements IVideo  {

	private ICodec codec;
	private Implementation codecImplementation;
	private IFrameSize framesize;
	private IFrameRate framerate;
	private IVideoBitrate bitrate;
	private IKeyFrameInterval keyFrameInterval;
	private IOverlayCollection overlays;
	private ArrayList<IParam> extraParams;
	

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

	public Implementation getCodecImplementation() {
		return codecImplementation;
	}

	public void setCodecImplementation(Implementation codecImplementation) {
		this.codecImplementation = codecImplementation;
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

	public IOverlayCollection getOverlays() {
		return overlays;
	}

	public void setOverlays(IOverlayCollection overlays) {
		this.overlays = overlays;
	}

	public ArrayList<IParam> getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(ArrayList<IParam> extraParams) {
		this.extraParams = extraParams;
	}
}
