package com.flashvisions.server.rtmp.transcoder.command.chain;

import org.apache.commons.chain.impl.ChainBase;

import com.flashvisions.server.rtmp.transcoder.command.BootstrapComplete;
import com.flashvisions.server.rtmp.transcoder.command.CheckFFmpegLibraries;
import com.flashvisions.server.rtmp.transcoder.command.InitializeEnvironment;
import com.flashvisions.server.rtmp.transcoder.command.InitializeVariables;

public class TranscoderBootStrap extends ChainBase {

	public TranscoderBootStrap()
	{
		super();
		
		addCommand(new InitializeEnvironment());
		addCommand(new CheckFFmpegLibraries());
		addCommand(new InitializeVariables());
		addCommand(new BootstrapComplete());
	}
}
