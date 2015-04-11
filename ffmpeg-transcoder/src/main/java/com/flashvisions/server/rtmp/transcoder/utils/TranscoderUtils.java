package com.flashvisions.server.rtmp.transcoder.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.flashvisions.server.rtmp.transcoder.pojo.io.enums.Server;

public class TranscoderUtils {

	public static boolean isValidMediaServer(String server) {

	    for (Server c : Server.values()) {
	        if (c.name().equals(server.toUpperCase())) {
	            return true;
	        }
	    }

	    return false;
	}
	
	public static List<String> captureLibraries(String ffmpegOutput)
	{
		List<String> libraries = new ArrayList<String>();		    
	    Pattern p = Pattern.compile("--enable-[^\\s]*", Pattern.CASE_INSENSITIVE);
	    Matcher matcher = p.matcher(ffmpegOutput);
	    while(matcher.find()) {
	    libraries.add(matcher.group().replace("--enable-", ""));
	    }
	    
	    return libraries;
	}
	
	public static String captureVersion(String ffmpegOutput)
	{
		 Pattern p2 = Pattern.compile("version[\\s][^\\s]*", Pattern.CASE_INSENSITIVE);
		 Matcher matcher2 = p2.matcher(ffmpegOutput);
		 if(matcher2.find()){
			 return  matcher2.group().replace("version ", "");
		 }
		 return null;
	}
}
