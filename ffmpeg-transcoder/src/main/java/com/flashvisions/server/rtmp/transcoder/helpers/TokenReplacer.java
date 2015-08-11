package com.flashvisions.server.rtmp.transcoder.helpers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TokenReplacer {
	
	/* Available tokens */
	
	public class TOKEN
	{
		public static final String SOURCE_STREAM_TOKEN = "${SourceStreamName}";
		public static final String SOURCE_STREAM_TOKEN_2 = "SourceStreamName";
		public static final String SOURCE_APP_TOKEN = "${SourceApplication}";
		public static final String SOURCE_APP_TOKEN_2 = "SourceApplication";
		public static final String HOME_DIRECTORY_TOKEN = "${homeDirectory}";
		public static final String HOME_DIRECTORY_TOKEN_2 = "HomeDirectory"	;
		public static final String CURRENT_DATE_TOKEN = "${currentDate}";
		public static final String WORKING_DIRECTORY_TOKEN = "${workingDirectory}";
		public static final String HLS_DIRECTORY_TOKEN = "${hlsDirectory}";
		public static final String TEMPLATE_DIRECTORY = "${templateDirectory}";
		public static final String OWN_SEGMENT_DIRECTORY = "${ownSegmentDirectory}";
		public static final String HLS_SAMPLE_PLAYBACK_TEMPLATE = "${hlsPlaybackTemplate}";
		public static final String FFMPEG_EXECUTABLE = "${ffmpegExecutable}";
		public static final String INPUT_SOURCE = "${inputSource}";
	}
	
	
	private HashMap<String, String> tokenMap;
	
	public TokenReplacer()
	{
		tokenMap = new HashMap<String, String>();
	}
	
	public void setTokenValue(String token, String value)
	{
		tokenMap.put(token, value);
	}
	
	public String getTokenValue(String token)
	{
		return tokenMap.get(token);
	}
	
	public String asPlaceholder(String placeHolder)
	{
		return "\\" + placeHolder;
	}
	
	@SuppressWarnings("rawtypes")
	public String processReplacement(String subject)
	{		
		Iterator<?> it = tokenMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        String token = (String) pair.getKey();
	        String value = (String) pair.getValue();
	        subject = subject.replace(token, value);
	        //subject = subject.replaceAll(token, value); // TO DO
	    }
	    
	    return subject;
	}
	
}
