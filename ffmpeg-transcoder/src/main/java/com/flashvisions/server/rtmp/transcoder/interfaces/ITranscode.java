package com.flashvisions.server.rtmp.transcoder.interfaces;



public interface ITranscode extends IMutable {

	public String getLabel() ;
	public void setLabel(String label);
	public String getDescription();
	public void setDescription(String description);
	public IEncodeCollection getEncodes();
	public void setEncodes(IEncodeCollection encodes);
}
