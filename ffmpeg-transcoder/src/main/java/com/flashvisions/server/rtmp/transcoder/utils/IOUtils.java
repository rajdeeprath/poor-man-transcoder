package com.flashvisions.server.rtmp.transcoder.utils;

import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;
import com.flashvisions.server.rtmp.transcoder.pojo.MediaOutput;

public class IOUtils {

	
	public static IMediaOutput createOutputFromInput(IMediaInput in, IMediaOutput out){
		
		String insource = in.getSourcePath();
		String streamname = in.getStreamName();
		String inapp = insource.substring(0, insource.indexOf(streamname)-1);
		
		String outsource = out.getSourcePath();
		outsource = outsource.replace("SourceApplication", inapp);
		outsource = outsource.replace("SourceStreamName", streamname);
		
		return new MediaOutput(outsource);
	}
	
	
}
