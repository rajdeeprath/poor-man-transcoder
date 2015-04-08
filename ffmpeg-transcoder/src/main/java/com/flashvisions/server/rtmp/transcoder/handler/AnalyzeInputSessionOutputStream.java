package com.flashvisions.server.rtmp.transcoder.handler;

import java.io.IOException;

import org.apache.commons.exec.LogOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flashvisions.server.rtmp.transcoder.interfaces.AnalyzeInputSessionDataCallback;


public class AnalyzeInputSessionOutputStream extends LogOutputStream {

	private static Logger logger = LoggerFactory.getLogger(AnalyzeInputSessionOutputStream.class);
	private AnalyzeInputSessionDataCallback callback;
	
	public AnalyzeInputSessionOutputStream(){
		
	}
	
	public AnalyzeInputSessionOutputStream(AnalyzeInputSessionDataCallback callback){
		this.callback = callback;
	}
	
	@Override
	protected void processLine(String line, int level) {
		logger.info("=>"+line);
		if(this.callback != null)
		this.callback.onAnalyzeInputProcessData(null, System.currentTimeMillis());
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		super.close();
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		super.flush();
	}

	@Override
	public int getMessageLevel() {
		// TODO Auto-generated method stub
		return super.getMessageLevel();
	}

	@Override
	protected void processBuffer() {
		// TODO Auto-generated method stub
		super.processBuffer();
	}

}
