package com.flashvisions.server.rtmp.transcoder.context;

import java.util.ArrayList;

import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;


public class TranscodeRequest {

	private String workingDirectoryPath;
	private String templateFileName;
	private boolean cleanUpSegmentsOnExit; 
	private String readProtocol = "rtsp";
	private String readHost = "127.0.0.1";
	private int readPort = 8554;
	private ArrayList<IProperty> inputflags;
	private long readDelay = 4000;
	
	
	public void setWorkingDirectory(String workingDirectoryPath) {
		// TODO Auto-generated method stub
		this.workingDirectoryPath = workingDirectoryPath;
	}

	public  String getWorkingDirectory() {
		// TODO Auto-generated method stub
		return workingDirectoryPath;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public boolean isCleanUpSegmentsOnExit() {
		return cleanUpSegmentsOnExit;
	}

	public void setCleanUpSegmentsOnExit(boolean cleanUpSegmentsOnExit) {
		this.cleanUpSegmentsOnExit = cleanUpSegmentsOnExit;
	}

	public String getReadProtocol() {
		return readProtocol;
	}

	public void setReadProtocol(String readProtocol) {
		this.readProtocol = readProtocol;
	}

	public String getReadHost() {
		return readHost;
	}

	public void setReadHost(String readHost) {
		this.readHost = readHost;
	}

	public int getReadPort() {
		return readPort;
	}

	public void setReadPort(int readPort) {
		this.readPort = readPort;
	}

	public ArrayList<IProperty> getInputflags() {
		return inputflags;
	}

	public void setInputflags(ArrayList<IProperty> inputflags) {
		this.inputflags = inputflags;
	}

	public long getReadDelay() {
		return readDelay;
	}

	public void setReadDelay(long readDelay) {
		this.readDelay = readDelay;
	}

}
