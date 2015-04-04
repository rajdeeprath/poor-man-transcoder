package com.flashvisions.server.rtmp.transcoder.system;

import java.util.HashMap;
import java.util.Map;

public final class Globals {

	public static class Vars{
		public static final String FFMPEG_EXECUTABLE_PATH = "ffmpegExcutable";
		public static final String WORKING_DIRECTORY = "workingDirectory";
		public static final String TEMPLATE_DIRECTORY = "templateDirectory";
		
		/*
		public static final String HLS_OUTPUT_DIRECTORY = "hlsOutDirectory";
		public static final String FFMPEG_READ_TIMEOUT = "transcoderReadTimeout";
		public static final String FFMPEG_START_DELAY = "transcoderStartDelay";
		*/
	}
	
	private static Map<String, String> envMap;
	
	private static void init()
	{
		envMap = new HashMap<String,String>();
	}
	
	public static void addEnv(String var, String value)
	{
		if(envMap == null) init();
		envMap.put(var, value);
	}
	
	public static void removeEnv(String var)
	{
		if(envMap == null) init();
		if(containsEnv(var)) envMap.remove(var);
	}
	
	public static boolean containsEnv(String var)
	{
		if(envMap == null) init();
		return envMap.containsKey(var);
	}
	
	public static String getEnv(String var)
	{
		if(envMap == null) init();
		if(containsEnv(var)) return envMap.get(var);
		return null;
	}
	
	public static Map<String,String> getEnvs()
	{
		if(envMap == null) init();
		return envMap;
	}
	
}
