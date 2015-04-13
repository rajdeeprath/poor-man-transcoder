package com.flashvisions.server.rtmp.transcoder.utils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import com.flashvisions.server.rtmp.transcoder.exception.MediaIdentifyException;
import com.flashvisions.server.rtmp.transcoder.interfaces.IContainer;
import com.flashvisions.server.rtmp.transcoder.interfaces.IFileInput;
import com.flashvisions.server.rtmp.transcoder.interfaces.IMediaOutput;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderResource;
import com.flashvisions.server.rtmp.transcoder.pojo.Container;
import com.flashvisions.server.rtmp.transcoder.pojo.io.FileDestination;
import com.flashvisions.server.rtmp.transcoder.pojo.io.StreamDestination;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Format;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Protocol;

public class IOUtils {
	
	public static void IdentifyInput(ITranscoderResource input) throws MediaIdentifyException
	{
		try
		{
			String source = input.getSourcePath();
			URI uri = new URI(source);
			
			String protocol = uri.getScheme();
			input.setProtocol(protocol);
			
			String medianame = (uri.getPath().contains("/"))?uri.getPath().substring(uri.getPath().lastIndexOf("/")+1):uri.getPath();
			input.setMediaName(medianame);	
			
			
			if(input.getContainer() == null)
			input.setContainer(new Container(IOUtils.guessContainer(source)));
			
			
			if(input.isFile()){
			IFileInput in = (IFileInput) input;
			in.setFile(new File(source));
			}			
		}
		catch(Exception e)
		{
			throw new MediaIdentifyException("Unable to identify media " + e.getMessage());
		}
	}
	
	public static void IdentifyOutput(IMediaOutput output) throws MediaIdentifyException
	{
		try
		{
			String source = output.getSourcePath();
			URI uri = new URI(source);
			
			String protocol = uri.getScheme();
			output.setProtocol(protocol);
			
			String medianame = (uri.getPath().contains("/"))?uri.getPath().substring(uri.getPath().lastIndexOf("/")+1):uri.getPath();
			output.setMediaName(medianame);
			
			if(output.getContainer() == null)
			output.setContainer(new Container(IOUtils.guessContainer(source)));
		}
		catch(Exception e)
		{
			throw new MediaIdentifyException("Unable to identify media " + e.getMessage());
		}
	}
	
	public static boolean isRTMPCompatStream(ITranscoderResource input)
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

	public static Format guessContainer(String source) throws URISyntaxException
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
				return Format.FLV;
					
				case RTP:
				return Format.RTP;
					
				case RTSP:
				return Format.RTSP;
				
				case HTTP:
				// possible hls or regular http file
				String fileType = source.substring(source.lastIndexOf(".")+1);
				switch(Format.valueOf(fileType.toUpperCase()))
				{
					case M3U8:
					return Format.SSEGMENT;
					
					default:
					return Format.valueOf(fileType.toUpperCase());						
				}
				
				default:
				return Format.FLV;
			}
		}
		catch(Exception e)
		{
			// match for files types from supported filetypes
			String fileType = source.substring(source.lastIndexOf(".")+1);
			switch(Format.valueOf(fileType.toUpperCase()))
			{
				case M3U8:
				return Format.SSEGMENT;
				
				default:
				return Format.valueOf(fileType.toUpperCase());
			}
		}
	}
	
	public static IMediaOutput createOutputFromInput(ITranscoderResource in, IMediaOutput temp) throws URISyntaxException{
		
		IMediaOutput finalOutput = null;
		IContainer container = null;
		
		String insource = in.getSourcePath();
		String streamname = in.getMediaName();
		String inapp = insource.substring(0, insource.indexOf(streamname)-1);
		
		String outsource = temp.getSourcePath();
		outsource = outsource.replace("SourceApplication", inapp);
		outsource = outsource.replace("SourceStreamName", streamname);
		
		container = (temp.getContainer() == null)?new Container(guessContainer(outsource)):temp.getContainer();
		finalOutput = (temp.isStreamingMedia())?new StreamDestination(outsource, container):new FileDestination(outsource, container);
		finalOutput.setContainer(container);
		
		return finalOutput;
	}
	
	
}
