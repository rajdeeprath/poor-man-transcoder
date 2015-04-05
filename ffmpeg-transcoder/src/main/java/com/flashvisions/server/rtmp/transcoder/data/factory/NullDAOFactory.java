package com.flashvisions.server.rtmp.transcoder.data.factory;

import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscodeConfigDao;

public class NullDAOFactory extends AbstractDAOFactory {

	@Override
	public ITranscodeConfigDao getTranscodeDao(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/***** Important!! Not to be used with prototype pattern ******/
	@Override
	public ITranscodeConfigDao getTranscodeDao(String filename,
			boolean lazyLoad) {
		// TODO Auto-generated method stub
		return null;
	}
	

}