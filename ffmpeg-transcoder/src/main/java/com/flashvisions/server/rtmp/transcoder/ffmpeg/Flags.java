package com.flashvisions.server.rtmp.transcoder.ffmpeg;

public class Flags {
	
	public static final String DASH = "-";
	
	public static final String INPUT = DASH + "i";
	
	public static final String DISABLE_AUDIO = DASH + "an";
	public static final String DISABLE_VIDEO = DASH + "vn";
	
	public static final String OVERWRITE = DASH + "y";
	public static final String OUTPUT = DASH + "f";
	
	public static final String VIDEO_BITRATE = DASH + "b:v";
	public static final String VIDEO_BUFFER = DASH + "bufsize";
	public static final String VIDEO_FRAMERATE = DASH + "r";
	public static final String VIDEO_FRAMESIZE = DASH + "s";
	public static final String VIDEO_GOP = DASH + "g";
	public static final String VIDEO_MIN_KFI = DASH + "keyint_min";
	public static final String VIDEO_MAXBITRATE = DASH + "maxrate";
	public static final String VIDEO_MINBITRATE = DASH + "minrate";
	public static final String VIDEO_CODEC = DASH + "codec:v";
	
	public static final String AUDIO_BITRATE = DASH + "b:a";
	public static final String AUDIO_CHANNEL = DASH + "ac";
	public static final String AUDIO_SAMPLERATE = DASH + "ar";
	public static final String AUDIO_CODEC = DASH + "codec:a";
	
	
}

