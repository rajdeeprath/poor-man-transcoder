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

public class Main  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try 
		{
			/* Boot strap */
			ITranscoderFacade facade = GenericTranscoderFacade.getInstance();
			facade.setFFmpegPath("C:\\ffmpeg\\bin\\ffmpeg.exe");
			facade.setHomeDirectory("C:\\red5-server-1.0.5\\");
			facade.setWorkingDirectory("C:\\red5-server-1.0.5\\webapps\\");
			facade.setTemplateDirectory("C:\\red5-server-1.0.5\\transcoder\\templates\\");
			facade.setOperatingMediaServer("red5");
			facade.init();
			
			
			/* Transcode request Object overrides global working directory.*/
			TranscodeRequest request = new TranscodeRequest();
			request.setWorkingDirectory("C:\\red5-server-1.0.5\\webapps\\live\\streams\\");
			request.setTemplateFileName("record-mp4-template.xml");
			request.setCleanUpSegmentsOnExit(true);
			
			
			/* fire request */
			// ArrayList<IProperty> inputflags = new ArrayList<IProperty>(Arrays.asList(new Property("-probesize"), new Property("32"), new Property("-analyzeduration"), new Property("1000000"))); // for low latency
			ArrayList<IProperty> inputflags = new ArrayList<IProperty>();
			facade.doTranscode(new RTMPTranscoderResource(new StreamMedia("rtmp://localhost/live/test"),inputflags), request);
			
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
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

}

