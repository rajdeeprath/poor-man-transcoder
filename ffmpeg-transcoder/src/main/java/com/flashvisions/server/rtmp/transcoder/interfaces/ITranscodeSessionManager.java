package com.flashvisions.server.rtmp.transcoder.interfaces;

import org.red5.server.api.stream.IBroadcastStream;

import com.flashvisions.server.rtmp.transcoder.context.TranscodeRequest;
import com.flashvisions.server.rtmp.transcoder.exception.InvalidTranscoderResourceException;
import com.flashvisions.server.rtmp.transcoder.exception.MalformedTranscodeQueryException;
import com.flashvisions.server.rtmp.transcoder.exception.MediaIdentifyException;

public interface ITranscodeSessionManager {

	public abstract void doTranscode(IBroadcastStream stream,
			TranscodeRequest request)
			throws InvalidTranscoderResourceException,
			MalformedTranscodeQueryException, MediaIdentifyException;

	public abstract void stopTranscode(IBroadcastStream stream);

}