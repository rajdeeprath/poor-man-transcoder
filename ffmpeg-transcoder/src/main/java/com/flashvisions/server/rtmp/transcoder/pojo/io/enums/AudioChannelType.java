package com.flashvisions.server.rtmp.transcoder.pojo.io.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AudioChannelType {

	MONO(0), 
	STEREO(1);
	
	private static final Map<Integer,AudioChannelType> lookup = new HashMap<Integer,AudioChannelType>();

	static {
	    for(AudioChannelType w : EnumSet.allOf(AudioChannelType.class))
	         lookup.put(w.getCode(), w);
	}

	private int code;

	private AudioChannelType(int code) {
	    this.code = code;
	}

	public int getCode() { return code; }

	public static AudioChannelType get(int code) { 
	    return lookup.get(code); 
	}
	}
