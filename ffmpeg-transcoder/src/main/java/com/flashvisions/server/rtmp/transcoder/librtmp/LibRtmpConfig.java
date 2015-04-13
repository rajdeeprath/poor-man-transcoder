package com.flashvisions.server.rtmp.transcoder.librtmp;

import com.flashvisions.server.rtmp.transcoder.interfaces.ILibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;

public abstract class LibRtmpConfig implements ILibRtmpConfig {
	
	private int timeout;
	private String rtmpApplication;
	private String stream;
	private boolean live = true;
	private String appName;
	private String playPath;
	private String tcUrl;
	private String pageUrl;
	private String swfUrl;
	private String conn;
	private long buffer;
	
	public LibRtmpConfig(){
		
	}
	
	public LibRtmpConfig(ITranscoderResource source){
		prepareFrom(source);
	}
	
	public int getTimeout() {
		return timeout;
	}
	
	/*******************************************************************************
	timeout=num
	Timeout the session after num seconds without receiving any data from the server.
	The default is 120.
	********************************************************************************/
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
	
	/******************************************************************************************
	live=0|1
	Specify that the media is a live stream. No resuming or seeking in live streams is possible.
	*******************************************************************************************/
	public void setLive(boolean live) {
		this.live = live;
	}
	
	/*****************************************************************************************************************************
	app=name
	Name of application to connect to on the RTMP server. Overrides the app in the RTMP URL. 
	Sometimes the librtmp URL parser cannot determine the app name automatically, so it must be given explicitly using this option.
	******************************************************************************************************************************/
	public String getAppName() {
		return appName;
	}
	
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getPlayPath() {
		return playPath;
	}
	
	/****************************************************************************************************************************************************************************************
	playpath=path
	Overrides the playpath parsed from the RTMP URL. Sometimes the rtmpdump URL parser cannot determine the correct playpath automatically, so it must be given explicitly using this option.
	*****************************************************************************************************************************************************************************************/
	public void setPlayPath(String playPath) {
		this.playPath = playPath;
	}
	
	public String getTcUrl() {
		return tcUrl;
	}
	
	/*******************************************************************
	tcUrl=url
	URL of the target stream. Defaults to rtmp[t][e|s]://host[:port]/app.
	********************************************************************/
	public void setTcUrl(String tcUrl) {
		this.tcUrl = tcUrl;
	}
	
	public long getBuffer() {
		return buffer;
	}

	
	/********************************************************
	buffer=num
	Set buffer time to num milliseconds. The default is 30000.
	********************************************************/
	public void setBuffer(long buffer) {
		this.buffer = buffer;
	}
	
	public String getConn() {
		return conn;
	}
	
	
	/**************************************************************************************
	conn=type:data
	Append arbitrary AMF data to the Connect message. The type must be B for Boolean, N for number, S for string, O for object, or Z for null. For Booleans the data must be either 0 or 1 for FALSE or TRUE, respectively. Likewise for Objects the data must be 0 or 1 to end or begin an object, respectively. Data items in subobjects may be named, by prefixing the type with 'N' and specifying the name before the value, e.g. NB:myFlag:1. This option may be used multiple times to construct arbitrary AMF sequences. E.g.
	conn=B:1 conn=S:authMe conn=O:1 conn=NN:code:1.23 conn=NS:flag:ok conn=O:0
	**************************************************************************************/
	public void setConn(String conn) {
		this.conn = conn;
	}

	public String getSwfUrl() {
		return swfUrl;
	}

	/********************************************************************
	swfUrl=url
	URL of the SWF player for the media. By default no value will be sent.
	********************************************************************/
	public void setSwfUrl(String swfUrl) {
		this.swfUrl = swfUrl;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	/***********************************************************************************
	pageUrl=url
	URL of the web page in which the media was embedded. By default no value will be sent.
	************************************************************************************/
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public void prepareFrom(ITranscoderResource input) {
		// TODO Auto-generated method stub
		String source = input.getSourcePath();
		String stream = input.getMediaName();
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
		this.setBuffer(200);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String fullUrl = rtmpApplication + "/" + stream; 
		String SPACE = " ";
		String command = "\"";
		
		command += fullUrl;
		command += SPACE;
		
		command += "live=" + ((isLive())?1:0);
		command += SPACE;
		
		command += "timeout=" + getTimeout();
		command += SPACE;
		
		if(getBuffer()>0){
		command += "buffer=" + getBuffer();
		command += SPACE;
		}
		
		if(!getPlayPath().equals("")){
		command += "playpath=" + getPlayPath();
		command += SPACE;
		}
		
		if(!getPlayPath().equals("")){
		command += "playpath=" + getPlayPath();
		command += SPACE;
		}
		
		if(!getAppName().equals("")){
		command += "app=" + getAppName();
		command += SPACE;
		}
		
		if(!(getPageUrl() == null)){
		command += "pageUrl=" + getPageUrl();
		command += SPACE;	
		}
		
		if(!getTcUrl().equals("")){
		command += "tcUrl=" + getTcUrl();
		command += SPACE;	
		}
		
		if(!(getSwfUrl() == null)){
		command += "swfUrl=" + getSwfUrl();
		command += SPACE;	
		}
		
		if(!(getConn() == null)){
		command += "conn=" + getConn();
		command += SPACE;	
		}
		
		command = command.replaceAll("^\\s+|\\s+$", "");
		command += "\"";
		return command;
	}
}
