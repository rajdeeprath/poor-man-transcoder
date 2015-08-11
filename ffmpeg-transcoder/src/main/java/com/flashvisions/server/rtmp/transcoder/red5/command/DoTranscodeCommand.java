package com.flashvisions.server.rtmp.transcoder.red5.command;

import java.io.File;
import java.io.IOException;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;

import com.flashvisions.server.rtmp.transcoder.Constants;
import com.flashvisions.server.rtmp.transcoder.context.TranscodeRequest;
import com.flashvisions.server.rtmp.transcoder.context.TranscoderContext;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pool.TranscodeSessionPool;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;

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
		
		IOUtils.IdentifyInput(input);
		String stream = input.getMediaName();
		
		TranscodeSessionPool pool =  ctx.getPool();
		ISession session = pool.checkOut(input, request);
		
		IConnection connnection = Red5.getConnectionLocal();
		connnection.setAttribute(Constants.TRANSCODER_SESSION_ATTR, pool.getSignature(session));
				
		if(this.request.getWorkingDirectory() != null) {
		File workingDir = new File(this.request.getWorkingDirectory());
		if(!workingDir.exists()) throw new IOException("Working directory not found");
		session.setWorkingDirectoryPath(workingDir.getAbsolutePath());
		}
		
		session.start();
		
		return true;
	}

}
