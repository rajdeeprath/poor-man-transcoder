package com.flashvisions.server.rtmp.transcoder.testclient;

import java.util.ArrayList;
import java.util.Arrays;

import com.flashvisions.server.rtmp.transcoder.context.TranscodeRequest;
import com.flashvisions.server.rtmp.transcoder.decorator.RTMPTranscoderResource;
import com.flashvisions.server.rtmp.transcoder.facade.GenericTranscoderFacade;
import com.flashvisions.server.rtmp.transcoder.interfaces.IProperty;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderFacade;
import com.flashvisions.server.rtmp.transcoder.pojo.Property;
import com.flashvisions.server.rtmp.transcoder.pojo.io.StreamMedia;

public class MainLinux  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try 
		{
			/* Boot strap */
			ITranscoderFacade facade = GenericTranscoderFacade.getInstance();
			facade.setFFmpegPath("/home/rajdeeprath/bin/ffmpeg");
			facade.setHomeDirectory("/home/rajdeeprath/red5-server/");
			facade.setWorkingDirectory("/home/rajdeeprath/red5-server/webapps/");
			facade.setTemplateDirectory("/home/rajdeeprath/red5-server/transcoder/templates/");
			facade.setOperatingMediaServer("red5");
			facade.init();
			
			
			/* Transcode request Object - these params override the global params set in bootsrap */
			TranscodeRequest request = new TranscodeRequest();
			request.setWorkingDirectory("/home/rajdeeprath/red5-server/webapps/live/streams/hls/");
			request.setTemplateFileName("hls-template.xml");
			request.setCleanUpSegmentsOnExit(true);
			
			
			/* fire request */
			ArrayList<IProperty> inputflags = new ArrayList<IProperty>(Arrays.asList(new Property("-re")));
			facade.doTranscode(new RTMPTranscoderResource(new StreamMedia("rtmp://localhost/live/test"),inputflags), request);
			
			/*
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			                // your code here
			            	ITranscoderFacade facade = GenericTranscoderFacade.getInstance();
			            	facade.abortTranscode();
			            }
			        }, 
			        5000 
			);
			*/
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}

