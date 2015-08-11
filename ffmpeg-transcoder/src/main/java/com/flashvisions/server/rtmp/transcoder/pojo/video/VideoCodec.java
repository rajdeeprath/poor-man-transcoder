package com.flashvisions.server.rtmp.transcoder.pojo.video;


import com.flashvisions.server.rtmp.transcoder.ffmpeg.Flags;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec;


public class VideoCodec extends Codec {
	
	private static final String key = Flags.VIDEO_CODEC;
	
	
	public VideoCodec(){
		super();
	}
	
	public VideoCodec(ICodec codec) {
		super(codec);
	}

	public VideoCodec(Object name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return VideoCodec.key;
	}

	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		//NO OP 
	}
	
	@Override
	public IParameter clone() {
		// TODO Auto-generated method stub
		return new VideoCodec(this.getValue());
	}
	
}
