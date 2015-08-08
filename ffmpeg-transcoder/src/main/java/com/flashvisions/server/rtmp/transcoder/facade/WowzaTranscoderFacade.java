package com.flashvisions.server.rtmp.transcoder.facade;

import com.flashvisions.server.rtmp.transcoder.context.TranscodeRequest;
import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderFacade;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;

public class WowzaTranscoderFacade implements ITranscoderFacade {

	@Override
	public void init() throws TranscoderException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFFmpegPath(String ffmpegPath) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFFmpegPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOperatingMediaServer(String serverName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getOperatingMediaServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setWorkingDirectory(String workingDirectoryPath) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getWorkingDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHomeDirectory(String homeDirectoryPath) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getHomeDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTemplateDirectory(String templateDirectoryPath) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTemplateDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doTranscode(ITranscoderResource input, TranscodeRequest request)
			throws TranscoderException {
		// TODO Auto-generated method stub

	}

	@Override
	public void abortTranscode() {
		// TODO Auto-generated method stub

	}

}
