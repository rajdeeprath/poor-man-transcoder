package com.flashvisions.server.rtmp.transcoder.context;


public class TranscodeRequest {

	private String workingDirectoryPath;
	private String templateFileName;
	private boolean cleanUpSegmentsOnExit; 
	
	
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

}
