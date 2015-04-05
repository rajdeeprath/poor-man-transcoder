package com.flashvisions.server.rtmp.transcoder.interfaces;

public interface ITranscoderFacade {

	public void init();
	
	public void setFFmpegPath(String ffmpegPath);
	public String getFFmpegPath();
	
	public void setOperatingServer(String serverName);
	public String getOperatingServer();
	
	public void setWorkingDirectory(String workingDirectoryPath);
	public String getWorkingDirectory();
	
	public void setHomeDirectory(String homeDirectoryPath);
	public String getHomeDirectory();
	
	public Object doTranscode(IMediaInput input, String usingTemplate);
	public Object doTranscode(IMediaInput input, String usingTemplate, ILibRtmpConfig librtmpConfig);
	public Object doTranscode(IMediaInput input, ITranscodeConfig transcode, ILibRtmpConfig librtmpConfig);
		
	public void abortTranscode(IMediaInput input, String usingTemplate);
	public void abortTranscode(IMediaInput input, String usingTemplate, ILibRtmpConfig librtmpConfig);
	public void abortTranscode(IMediaInput input, ITranscodeConfig transcode, ILibRtmpConfig librtmpConfig);
	public void abortTranscode(long sessionId);
	public void abortTranscode(String sessionSignature);
}
