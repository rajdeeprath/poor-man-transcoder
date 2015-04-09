package com.flashvisions.server.rtmp.transcoder.interfaces;



public interface ITranscode extends IMutable {
	public String getLabel() ;
	public String getDescription();
	public IEncodeCollection getEncodes();
}
