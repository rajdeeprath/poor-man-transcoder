package com.flashvisions.server.rtmp.transcoder.facade;

import com.flashvisions.server.rtmp.transcoder.interfaces.ILibRtmpConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfig;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderFacade;
import com.flashvisions.server.rtmp.transcoder.system.Globals;

public final class TranscoderFacade implements ITranscoderFacade {

	private String ffmpegPath;
	private String homeDirectoryPath;
	private String workingDirectoryPath;
	private String serverName;
	

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFFmpegPath(String ffmpegPath) {
		// TODO Auto-generated method stub
		this.ffmpegPath = ffmpegPath;
		Globals.addEnv(Globals.Vars.FFMPEG_EXECUTABLE_PATH, this.ffmpegPath);
	}

	@Override
	public String getFFmpegPath() {
		// TODO Auto-generated method stub
		return ffmpegPath;
	}

	@Override
	public void setOperatingServer(String serverName) {
		// TODO Auto-generated method stub
		this.serverName = serverName;
		Globals.addEnv(Globals.Vars.OPERATING_SERVER, this.serverName);
	}

	@Override
	public String getOperatingServer() {
		// TODO Auto-generated method stub
		return serverName;
	}

	@Override
	public void setWorkingDirectory(String workingDirectoryPath) {
		// TODO Auto-generated method stub
		this.workingDirectoryPath = workingDirectoryPath;
		Globals.addEnv(Globals.Vars.WORKING_DIRECTORY, this.workingDirectoryPath);
	}

	@Override
	public String getWorkingDirectory() {
		// TODO Auto-generated method stub
		return workingDirectoryPath;
	}

	@Override
	public void setHomeDirectory(String homeDirectoryPath) {
		// TODO Auto-generated method stub
		this.homeDirectoryPath = homeDirectoryPath;
		Globals.addEnv(Globals.Vars.HOME_DIRECTORY, this.homeDirectoryPath);
	}

	@Override
	public String getHomeDirectory() {
		// TODO Auto-generated method stub
		return homeDirectoryPath;
	}

	@Override
	public Object doTranscode(IMediaInput input, String usingTemplate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object doTranscode(IMediaInput input, String usingTemplate,
			ILibRtmpConfig librtmpConfig) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object doTranscode(IMediaInput input, ITranscodeConfig transcode,
			ILibRtmpConfig librtmpConfig) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void abortTranscode(IMediaInput input, String usingTemplate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void abortTranscode(IMediaInput input, String usingTemplate,
			ILibRtmpConfig librtmpConfig) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void abortTranscode(IMediaInput input, ITranscodeConfig transcode,
			ILibRtmpConfig librtmpConfig) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void abortTranscode(long sessionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void abortTranscode(String sessionSignature) {
		// TODO Auto-generated method stub
		
	}
	
	

}
