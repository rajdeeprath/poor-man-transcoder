package com.flashvisions.server.rtmp.transcoder.generic.command;

import java.io.File;
import java.io.IOException;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

import com.flashvisions.server.rtmp.transcoder.context.TranscodeRequest;
import com.flashvisions.server.rtmp.transcoder.context.TranscoderContext;
import com.flashvisions.server.rtmp.transcoder.interfaces.ISession;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pool.TranscodeSessionPool;
import com.flashvisions.server.rtmp.transcoder.utils.IOUtils;

@SuppressWarnings("unused")
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
		File workingDir = null;
		
		File templateFile = new File(ctx.getTemplateDirectory() + File.separator + this.request.getTemplateFileName());
		if(!templateFile.exists()) throw new IOException("Template not found");
		
		if(this.request.getWorkingDirectory() != null) {
		workingDir = new File(this.request.getWorkingDirectory());
		if(!workingDir.exists()) throw new IOException("Working directory not found");
		}
		
		IOUtils.IdentifyInput(input);
		String stream = input.getMediaName();
		
		TranscodeSessionPool pool =  ctx.getPool();
		ISession session = pool.checkOut(input, request);
		session.setWorkingDirectoryPath(workingDir.getAbsolutePath());
		
		session.start();
		
		return true;
	}

}
