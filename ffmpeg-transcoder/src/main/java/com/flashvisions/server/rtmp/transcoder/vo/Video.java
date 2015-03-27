package com.flashvisions.server.rtmp.transcoder.vo;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.IVideo;
import com.flashvisions.server.rtmp.transcoder.pojo.FrameRate;
import com.flashvisions.server.rtmp.transcoder.pojo.FrameSize;
import com.flashvisions.server.rtmp.transcoder.pojo.KeyFrameInterval;
import com.flashvisions.server.rtmp.transcoder.pojo.VideoBitrate;
import com.flashvisions.server.rtmp.transcoder.pojo.VideoProperty;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec.Implementation;
import com.flashvisions.server.rtmp.transcoder.pojo.base.Mutable;

public class Video extends Mutable implements IVideo {
	
	private ICodec codec;
	private Implementation codecImplementation;
	private FrameSize framesize;
	private FrameRate framerate;
	private VideoBitrate bitrate;
	private KeyFrameInterval keyFrameInterval;
	private ArrayList<Object> overlays;
	private ArrayList<VideoProperty> extraParams;
	

	public ICodec getCodec() {
		return codec;
	}

	public void setCodec(ICodec codec) {
		this.codec = codec;
	}

	public FrameSize getFramesize() {
		return framesize;
	}

	public void setFramesize(FrameSize framesize) {
		this.framesize = framesize;
	}

	public Implementation getCodecImplementation() {
		return codecImplementation;
	}

	public void setCodecImplementation(Implementation codecImplementation) {
		this.codecImplementation = codecImplementation;
	}

	public FrameRate getFramerate() {
		return framerate;
	}

	public void setFramerate(FrameRate framerate) {
		this.framerate = framerate;
	}

	public VideoBitrate getBitrate() {
		return bitrate;
	}

	public void setBitrate(VideoBitrate bitrate) {
		this.bitrate = bitrate;
	}

	public KeyFrameInterval getKeyFrameInterval() {
		return keyFrameInterval;
	}

	public void setKeyFrameInterval(KeyFrameInterval keyFrameInterval) {
		this.keyFrameInterval = keyFrameInterval;
	}

	public ArrayList<Object> getOverlays() {
		return overlays;
	}

	public void setOverlays(ArrayList<Object> overlays) {
		this.overlays = overlays;
	}

	public ArrayList<VideoProperty> getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(ArrayList<VideoProperty> extraParams) {
		this.extraParams = extraParams;
	}
}
