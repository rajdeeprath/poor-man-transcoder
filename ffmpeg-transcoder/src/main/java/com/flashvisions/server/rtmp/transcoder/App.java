package com.flashvisions.server.rtmp.transcoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.exception.TranscoderException;
import com.flashvisions.server.rtmp.transcoder.facade.Red5TranscoderFacade;
import com.flashvisions.server.rtmp.transcoder.interfaces.ITranscoderFacade;
import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Server;


public class App {
	
	private static Logger logger = LoggerFactory.getLogger(App.class);


	public static void main(String[] args) {
		logger.info("Test runnning");
		
		ITranscoderFacade facade = new Red5TranscoderFacade();
		facade.setFFmpegPath("N:\\ffmpeg\\bin\\ffmpeg.exe");
		facade.setHomeDirectory("N:\\Red5_Pro_Server_Builds\\red5pro-server-4.5.4.b209-ffmpeg-thumbnailing");
		facade.setWorkingDirectory("N:\\Red5_Pro_Server_Builds\\red5pro-server-4.5.4.b209-ffmpeg-thumbnailing\\webapps\\root");
		facade.setTemplateDirectory("N:\\Red5_Pro_Server_Builds\\red5pro-server-4.5.4.b209-ffmpeg-thumbnailing\\transcoder\\templates");
		facade.setOperatingMediaServer(Server.RED5.name().toLowerCase());
		
		try 
		{
			facade.init();
		} 
		catch (TranscoderException e) 
		{
			e.printStackTrace();
		}
	}

}
