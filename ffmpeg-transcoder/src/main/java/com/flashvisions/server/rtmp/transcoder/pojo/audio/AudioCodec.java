package com.flashvisions.server.rtmp.transcoder.pojo.audio;



import com.flashvisions.server.rtmp.transcoder.ffmpeg.Flags;
import com.flashvisions.server.rtmp.transcoder.interfaces.ICodec;
import com.flashvisions.server.rtmp.transcoder.interfaces.IParameter;
import com.flashvisions.server.rtmp.transcoder.pojo.Codec;


public class AudioCodec extends Codec {

	private static final String key = Flags.AUDIO_CODEC;
	

	public AudioCodec(){
		super();
	}
	
	public AudioCodec(ICodec object) {
		super(object);
	}
	
	public AudioCodec(Object name) {
		super(name);
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return AudioCodec.key;
	}

	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		// NO OP
	}


	@Override
	public IParameter clone() {
		// TODO Auto-generated method stub
		return new AudioCodec(this.getValue());
	}
	
	
}
