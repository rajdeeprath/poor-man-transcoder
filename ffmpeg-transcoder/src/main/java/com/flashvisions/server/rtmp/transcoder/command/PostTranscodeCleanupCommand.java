package com.flashvisions.server.rtmp.transcoder.command;


import java.io.File;
import java.util.ArrayList;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.flashvisions.server.rtmp.transcoder.context.CleanUpContext;
import com.flashvisions.server.rtmp.transcoder.context.TranscodeRequest;
import com.flashvisions.server.rtmp.transcoder.helpers.TokenReplacer;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;

public class PostTranscodeCleanupCommand implements Command {

	
	@Override
	public boolean execute(Context context) throws Exception {
		
		CleanUpContext ctx = (CleanUpContext) context;
		
		ITranscoderResource input = ctx.getInput();
		ArrayList<ITranscoderResource> outputs = ctx.getOutputs();
		String workingDirectory = ctx.getWorkingDirectory();
		
		
		for(ITranscoderResource output : outputs)
		{
			switch(output.getContainer().getType())
			{
				case SSEGMENT:
					String outName = output.getMediaName();
					String outNameWithOutExt = FilenameUtils.removeExtension(outName);
					File sub = new File(workingDirectory + File.separator + outNameWithOutExt);
					if(sub.exists())
					FileUtils.deleteDirectory(sub);
				break;
				
				default:
				break;
			}
		}
		
		
		return false;
	}

}
