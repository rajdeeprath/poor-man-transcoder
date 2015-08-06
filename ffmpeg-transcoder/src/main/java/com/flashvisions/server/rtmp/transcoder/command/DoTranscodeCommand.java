package com.flashvisions.server.rtmp.transcoder.command;

import java.io.File;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.flashvisions.server.rtmp.transcoder.context.TranscodeRequest;
import com.flashvisions.server.rtmp.transcoder.context.TranscoderContext;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pool.TranscodeSessionPool;

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
		// TODO Auto-generated method stub
		TranscoderContext ctx = (TranscoderContext) context;
		
		TranscodeSessionPool pool =  ctx.getPool();
		ISession session = pool.checkOut(input, request);
		
		if(this.request.getWorkingDirectory() != null)
		{
			File workingDir = new File(this.request.getWorkingDirectory());
			if(workingDir.exists()) session.setWorkingDirectoryPath(workingDir.getAbsolutePath());
		}
		
		if(session.isRunning())	session.stop();
		session.start();
		
		return false;
	}

}
