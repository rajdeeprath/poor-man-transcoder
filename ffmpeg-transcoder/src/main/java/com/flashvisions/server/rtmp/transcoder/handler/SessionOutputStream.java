package com.flashvisions.server.rtmp.transcoder.handler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.exec.LogOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SessionOutputStream extends LogOutputStream {

	private static Logger logger = LoggerFactory.getLogger(SessionOutputStream.class);
	
	private static final int QUEUE_SIZE = 10;
	private final Queue<String> lines = new LinkedList<String>();
	private long lastOutputTime = 0;
	
	
	@Override
	protected void processLine(String line, int level) {
		
		if(lines.size()>QUEUE_SIZE){
			lines.remove();
		}	
		
		lines.add(line);
		lastOutputTime = System.currentTimeMillis();		
		logger.info(level+" : "+line);
	}
	
	/* Estimates if process is running by checking if there was output in last 3 seconds */
	public boolean isRunning()
	{
		long diff = System.currentTimeMillis() - lastOutputTime;
        long diffSeconds = diff / 1000 % 60;
        return (diffSeconds>3)?false:true;
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
