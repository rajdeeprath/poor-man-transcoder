package com.flashvisions.server.rtmp.transcoder.data.factory;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeSettingsDao;

public class NullDAOFactory extends TranscoderDAOFactory {

	@Override
	public ITranscodeSettingsDao getTranscodeDao(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getTemplateBasePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTemplateBasePath(String baseDir) {
		// TODO Auto-generated method stub
		
	}
	

}
