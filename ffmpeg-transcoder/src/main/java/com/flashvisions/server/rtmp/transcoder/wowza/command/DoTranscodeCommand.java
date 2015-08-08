package com.flashvisions.server.rtmp.transcoder.wowza.command;


import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.red5.server.api.IConnection;

import com.flashvisions.server.rtmp.transcoder.context.TranscodeRequest;
import com.flashvisions.server.rtmp.transcoder.context.TranscoderContext;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;

public class DoTranscodeCommand implements Command {

	ITranscoderResource input;
	TranscodeRequest request;
	
	public DoTranscodeCommand(ITranscoderResource input, TranscodeRequest request)
	{
		this.input = input;
		this.request = request;
	}

	
	@Override
	public boolean execute(Context context) throws Exception {
				
		return false;
	}

}
