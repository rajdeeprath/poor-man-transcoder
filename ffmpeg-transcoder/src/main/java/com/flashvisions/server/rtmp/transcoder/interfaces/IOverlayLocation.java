package com.flashvisions.server.rtmp.transcoder.interfaces;

import com.flashvisions.server.rtmp.transcoder.pojo.Overlay.Location.ALIGNMENT;

public interface IOverlayLocation {

	public int getX();
	public void setX(int x);
	public void setY(int y);
	public int getWidth();
	public void setWidth(int width);
	public int getHeight();
	public void setHeight(int height);
	public ALIGNMENT getAlign();
	public void setAlign(ALIGNMENT align);
	public void setAlign(String alignment);
}
