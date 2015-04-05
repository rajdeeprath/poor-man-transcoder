package com.flashvisions.server.rtmp.transcoder.librtmp;

import com.flashvisions.server.rtmp.transcoder.interfaces.ILibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;

public abstract class LibRtmpConfig implements ILibRtmpConfig {
	
	private int timeout;
	private String rtmpApplication;
	private String stream;
	private boolean live = true;
	private String appName;
	private String playPath;
	private String tcUrl;
	private long buffer;
	
	
	public int getTimeout() {
		return timeout;
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	public String getRtmpApplication() {
		return rtmpApplication;
	}
	
	public void setRtmpApplication(String rtmpApplication) {
		this.rtmpApplication = rtmpApplication;
	}
	
	public String getStream() {
		return stream;
	}
	
	public void setStream(String stream) {
		this.stream = stream;
	}
	
	public boolean isLive() {
		return live;
	}
	
	public void setLive(boolean live) {
		this.live = live;
	}
	
	public String getAppName() {
		return appName;
	}
	
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getPlayPath() {
		return playPath;
	}
	
	public void setPlayPath(String playPath) {
		this.playPath = playPath;
	}
	
	public String getTcUrl() {
		return tcUrl;
	}
	
	public void setTcUrl(String tcUrl) {
		this.tcUrl = tcUrl;
	}
	
	public long getBuffer() {
		return buffer;
	}

	public void setBuffer(long buffer) {
		this.buffer = buffer;
	}

	@Override
	public void parseRtmp(IMediaInput input) {
		// TODO Auto-generated method stub
		String source = input.getSourcePath();
		String stream = input.getStreamName();
		String rtmpApplication = source.substring(0, source.indexOf(stream)-1);
		String appname = rtmpApplication.substring(rtmpApplication.lastIndexOf("/")+1);
		String playpath = source.substring(rtmpApplication.length(), source.indexOf(stream)-1);
		String tcUrl = rtmpApplication + "/" + stream;
		
		this.setRtmpApplication(rtmpApplication);
		this.setStream(stream);
		this.setAppName(appname);
		this.setPlayPath(playpath);
		this.setTcUrl(tcUrl);
		this.setLive(true);
		this.setBuffer(50);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String fullUrl = rtmpApplication + "/" + stream; 
		String SPACE = " ";
		return "\"" + fullUrl + SPACE + "live=1" + SPACE + "buffer=" + getBuffer() + SPACE + "timeout=" + getTimeout() + SPACE + "app=" + getAppName() + SPACE + "playpath=" + getPlayPath() + SPACE + "tcUrl=" + getTcUrl() + "\"";
	}

	

	
}
