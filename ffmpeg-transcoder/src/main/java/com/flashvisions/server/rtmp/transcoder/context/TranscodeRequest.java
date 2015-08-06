package com.flashvisions.server.rtmp.transcoder.context;


public class TranscodeRequest {

	private String workingDirectoryPath;
	private String templateFileName;
	private String templateDirectoryPath;
	
	
	public void setWorkingDirectory(String workingDirectoryPath) {
		// TODO Auto-generated method stub
		this.workingDirectoryPath = workingDirectoryPath;
	}

	public  String getWorkingDirectory() {
		// TODO Auto-generated method stub
		return workingDirectoryPath;
	}
	
	public void setTemplateDirectory(String templateDirectoryPath) {
		// TODO Auto-generated method stub
		this.templateDirectoryPath = templateDirectoryPath;
	}

	public String getTemplateDirectory() {
		// TODO Auto-generated method stub
		return templateDirectoryPath;
	}

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

}
