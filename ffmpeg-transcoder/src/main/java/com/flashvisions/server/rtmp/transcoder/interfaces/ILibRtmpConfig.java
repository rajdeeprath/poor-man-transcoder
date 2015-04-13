package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface ILibRtmpConfig {

	public int getTimeout();
	public void setTimeout(int timeout);
	public String getRtmpApplication();
	public void setRtmpApplication(String rtmpApplication);
	public String getStream();
	public void setStream(String stream);
	public boolean isLive();
	public void setLive(boolean live);
	public String getAppName();
	public void setAppName(String appName);
	public String getPlayPath();
	public void setPlayPath(String playPath);
	public String getTcUrl();
	public void setTcUrl(String tcUrl);
	public void parseRtmp(IMedia media);
}
