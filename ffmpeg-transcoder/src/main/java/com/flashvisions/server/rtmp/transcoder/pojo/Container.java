package com.flashvisions.server.rtmp.transcoder.pojo;

import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;

public class Container implements IContainer {
	public static enum Type {
        FLV, MP4, SEGMENT, RTSP, RTMP, RTP, MPEGTS, M3U8
    }
	
	private String name;
	private Type scheme;
	
	public Container(String name){
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.validateContainer(this.name);
	}

	public Type getScheme() {
		return scheme;
	}
	
	protected void validateContainer(String containername)
	{
		switch(Type.valueOf(containername.toUpperCase()))
		{
			case FLV:
			break;
			
			case MP4:
			break;
			
			case MPEGTS:
			break;
			
			case RTMP:
			break;
			
			case RTP:
			break;
			
			case RTSP:
			break;
			
			case SEGMENT:
			break;
			
			default:
			break;		
		}
		
		scheme = Type.valueOf(containername.toUpperCase());
	}
}
