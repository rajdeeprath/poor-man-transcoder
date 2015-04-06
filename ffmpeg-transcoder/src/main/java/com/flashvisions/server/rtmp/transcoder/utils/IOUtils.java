package com.flashvisions.server.rtmp.transcoder.utils;

import java.net.URI;
import java.net.URISyntaxException;

import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaInput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;
import com.flashvisions.server.rtmp.transcoder.pojo.io.FileOutput;
import com.flashvisions.server.rtmp.transcoder.pojo.io.StreamOutput;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.ContainerType;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Protocol;

public class IOUtils {
	
	public static boolean isRTMPCompatStream(IMediaInput input)
	{
		try
		{
			String protocol = input.getProtocol();
			switch(Protocol.valueOf(protocol.toUpperCase()))
			{		
				case RTMP:
				case RTMPE:
				case RTMPS:
				case RTMPT:
				return true;
				
				default:
				return false;
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public static ContainerType getContainer(String source) throws URISyntaxException
	{
		try
		{
			URI uri = new URI(source);
			String protocol = uri.getScheme();
			
			// match protocols
			switch(Protocol.valueOf(protocol.toUpperCase()))
			{		
				case RTMP:
				case RTMPE:
				case RTMPS:
				case RTMPT:
				return ContainerType.FLV;
					
				case RTP:
				return ContainerType.RTP;
					
				case RTSP:
				return ContainerType.RTSP;
				
				case HTTP:
				// possible hls or regular http file
				String fileType = source.substring(source.lastIndexOf(".")+1);
				switch(ContainerType.valueOf(fileType.toUpperCase()))
				{
					case M3U8:
					return ContainerType.SSEGMENT;
					
					default:
					return ContainerType.valueOf(fileType.toUpperCase());						
				}
				
				default:
				return ContainerType.FLV;
			}
		}
		catch(Exception e)
		{
			// match for files types from supported filetypes
			String fileType = source.substring(source.lastIndexOf(".")+1);
			switch(ContainerType.valueOf(fileType.toUpperCase()))
			{
				case M3U8:
				return ContainerType.SSEGMENT;
				
				default:
				return ContainerType.valueOf(fileType.toUpperCase());
			}
		}
	}
	
	public static IMediaOutput createOutputFromInput(IMediaInput in, IMediaOutput out){
		
		String insource = in.getSourcePath();
		String streamname = in.getStreamName();
		String inapp = insource.substring(0, insource.indexOf(streamname)-1);
		
		String outsource = out.getSourcePath();
		outsource = outsource.replace("SourceApplication", inapp);
		outsource = outsource.replace("SourceStreamName", streamname);
		
		return (out.isStreamingMedia())?new StreamOutput(outsource):new FileOutput(outsource);
	}
	
	
}
