package com.flashvisions.server.rtmp.transcoder.pojo.io.enums;

public enum AudioSampleRates {
	SAMPLE_8000(8000),
	SAMPLE_11025(11025),
	SAMPLE_16000(16000),
	SAMPLE_22050(22050),
	SAMPLE_32000(32000),
	SAMPLE_37800(37800),
	SAMPLE_44056(44056),
	SAMPLE_44100(44100),
	SAMPLE_48000(48000);

    private final int value;

    private AudioSampleRates(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }	
}
